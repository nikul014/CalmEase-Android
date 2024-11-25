package com.example.calmease

import android.Manifest
import android.content.pm.PackageManager
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import io.agora.rtc.video.VideoEncoderConfiguration

class JoinSessionActivity : AppCompatActivity() {

    private var mRtcEngine: RtcEngine? = null
    private var sessionId: String? = null
    private var token: String? = null
    private var role: String? = null
    private var userId: Int = 0

    companion object {
        const val PERMISSION_REQUEST_CODE = 1
    }

    private val appId = "e947c59bbe8c4287954cb154e63be817"
    private var isAudioMuted = false
    private var isVideoMuted = false

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            super.onJoinChannelSuccess(channel, uid, elapsed)
            Log.d("Agora", "Joined channel: $channel")
        }

        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            Log.d("Agora", "User joined with uid: $uid")
            runOnUiThread {
                setupRemoteVideo(uid)
            }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            super.onUserOffline(uid, reason)
            Log.d("Agora", "User offline with uid: $uid")
        }

        override fun onError(err: Int) {
            super.onError(err)
            Log.d("Agora", "Error with error code: $err")
        }
    }

    private fun setupRemoteVideo(uid: Int) {
        val container = findViewById<FrameLayout>(R.id.remote_video_view_container)
        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        surfaceView.setZOrderMediaOverlay(true)
        container.addView(surfaceView)
        mRtcEngine!!.setupRemoteVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, uid))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_session)

        sessionId = intent.getStringExtra("SESSION_ID")
        token = intent.getStringExtra("TOKEN")
        userId = intent.getIntExtra("USER_ID", 0)
        role = intent.getStringExtra("ROLE")

        // Request permissions if necessary
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO),
                PERMISSION_REQUEST_CODE
            )
        } else {
            initializeAndJoinChannel()
        }

        // Set button actions
        setupButtons()
    }

    private fun setupButtons() {
        if (role == "publisher") {
            // Audio toggle button
            val audioButton = findViewById<Button>(R.id.btn_audio_toggle)
            audioButton.setOnClickListener {

                if (isAudioMuted) {
                    mRtcEngine?.enableAudio()
                    audioButton.text = "Mute Audio"
                } else {
                    mRtcEngine?.disableAudio()
                    audioButton.text = "Unmute Audio"
                }
                isAudioMuted = !isAudioMuted

            }

            // Camera toggle button
            val cameraButton = findViewById<Button>(R.id.btn_camera_toggle)
            cameraButton.setOnClickListener {
                if (isVideoMuted) {
                    mRtcEngine?.disableVideo()
                    cameraButton.text = "Mute Camera"
                } else {
                    mRtcEngine?.enableVideo()
                    cameraButton.text = "Unmute Camera"
                }

                // Toggle the video state
                isVideoMuted = !isVideoMuted
            }

            // End call button
            val endCallButton = findViewById<Button>(R.id.btn_end_call)
            endCallButton.setOnClickListener {
                endCall()
            }

            // Hide the remote video view for the publisher
            val remoteView = findViewById<FrameLayout>(R.id.remote_video_view_container)
            remoteView.visibility = View.GONE
        } else {
            // Leave session button for the subscriber
            val leaveButton = findViewById<Button>(R.id.btn_leave_session)
            leaveButton.setOnClickListener {
//                leaveSession()
            }

            // Fullscreen remote view for the subscriber
            val container = findViewById<FrameLayout>(R.id.remote_video_view_container)
            val layoutParams = container.layoutParams
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            container.layoutParams = layoutParams

            // Hide the local video view for the subscriber
            val localView = findViewById<FrameLayout>(R.id.local_video_view_container)
            localView.visibility = View.GONE
        }
    }

    private fun initializeAndJoinChannel() {
        try {
            mRtcEngine = RtcEngine.create(baseContext, appId, mRtcEventHandler)
        } catch (e: Exception) {
            throw RuntimeException("Check the error.")
        }

        mRtcEngine?.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)

        if (role == "publisher") {
            mRtcEngine?.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
        } else {
            mRtcEngine?.setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
            mRtcEngine?.disableVideo()
            mRtcEngine?.disableAudio()
        }

        if (role == "publisher") {
            mRtcEngine?.enableVideo()
        }

        val container = findViewById<FrameLayout>(R.id.local_video_view_container)
        val surfaceView = RtcEngine.CreateRendererView(baseContext)
        container.addView(surfaceView)
        mRtcEngine?.setupLocalVideo(VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_FIT, 0))

        mRtcEngine?.joinChannel(token, sessionId, "", userId)
    }

    private fun endCall() {
        mRtcEngine?.leaveChannel()
        RtcEngine.destroy()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRtcEngine?.let {
            it.leaveChannel()
            RtcEngine.destroy()
        }
    }

    // Handle the result of permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                initializeAndJoinChannel()
            } else {
                Toast.makeText(this, "Permissions not granted, exiting app", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }
}
