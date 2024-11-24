package com.example.calmease.ui.screen.sessions

import android.view.SurfaceView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.calmease.network.fetchToken
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import io.agora.rtc.video.VideoEncoderConfiguration

@Composable
fun ViewerScreen(sessionId: String) {
    val context = LocalContext.current
    val agoraClient = remember { RtcEngine.create(context, "e947c59bbe8c4287954cb154e63be817", object : IRtcEngineEventHandler() {}) }
    var remoteViewId by remember { mutableStateOf(0) }

    LaunchedEffect(sessionId) {
        val token = fetchToken(sessionId) // Fetch token from API
        agoraClient.apply {
            enableVideo()
            setVideoEncoderConfiguration(
                VideoEncoderConfiguration(
                    VideoEncoderConfiguration.VideoDimensions(640, 480),
                    VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                    VideoEncoderConfiguration.STANDARD_BITRATE,
                    VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE
                )
            )
            joinChannel(token, sessionId, "", 0)
        }

//        agoraClient  .setEventHandler(object : IRtcEngineEventHandler() {
//            override fun onUserJoined(uid: Int, elapsed: Int) {
//                remoteViewId = uid
//            }
//        })
    }

    DisposableEffect(Unit) {
        onDispose {
            agoraClient.leaveChannel()
            RtcEngine.destroy()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (remoteViewId != 0) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {
                    SurfaceView(context).apply {
                        agoraClient.setupRemoteVideo(VideoCanvas(this, VideoCanvas.RENDER_MODE_HIDDEN, remoteViewId))
                    }
                }
            )
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Waiting for Publisher...", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
