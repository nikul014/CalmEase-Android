package com.example.calmease.ui.screen.sessions

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.view.TextureView
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.*

private const val PERMISSION_REQ_ID = 22

// Required permissions for audio and video
private val permissions = arrayOf(
    Manifest.permission.RECORD_AUDIO,
    Manifest.permission.CAMERA,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

class VideoActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channelName = intent.getStringExtra("ChannelName")
        val userRole = intent.getStringExtra("UserRole")

        setContent {
            Scaffold {
                UIRequirePermissions(
                    permissions = permissions,
                    onPermissionGranted = {
                        if (channelName != null && userRole != null) {
                            CallScreen(channelName = channelName, userRole = userRole)
                        }
                    },
                    onPermissionDenied = {
                        AlertScreen(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun CallScreen(channelName: String, userRole: String) {
    val context = LocalContext.current

    val localSurfaceView: TextureView? by remember { mutableStateOf(RtcEngine.CreateTextureView(context)) }

    var remoteUserMap by remember { mutableStateOf(mapOf<Int, TextureView?>()) }

    val mEngine = remember {
        initEngine(context, object : IRtcEngineEventHandler() {
            override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                Log.d("VideoActivity", "onJoinChannelSuccess: channel:$channel, uid:$uid")
            }

            override fun onUserJoined(uid: Int, elapsed: Int) {
                Log.d("VideoActivity", "onUserJoined: uid:$uid")
                remoteUserMap = remoteUserMap.toMutableMap().apply {
                    put(uid, null)
                }
            }

            override fun onUserOffline(uid: Int, reason: Int) {
                Log.d("VideoActivity", "onUserOffline: uid:$uid")
                remoteUserMap = remoteUserMap.toMutableMap().apply {
                    remove(uid)
                }
            }
        }, channelName, userRole)
    }

    if (userRole == "Broadcaster") {
        mEngine.setupLocalVideo(VideoCanvas(localSurfaceView, Constants.RENDER_MODE_FIT, 0))
    }

    Box(Modifier.fillMaxSize()) {
        localSurfaceView?.let { local ->
            AndroidView(factory = { local }, modifier = Modifier.fillMaxSize())
        }
        RemoteView(remoteListInfo = remoteUserMap, mEngine = mEngine)
        UserControls(mEngine = mEngine)
    }
}

@Composable
private fun RemoteView(remoteListInfo: Map<Int, TextureView?>, mEngine: RtcEngine) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
            .horizontalScroll(state = rememberScrollState())
    ) {
        remoteListInfo.forEach { entry ->
            val remoteTextureView =
                RtcEngine.CreateTextureView(context).takeIf { entry.value == null } ?: entry.value
            AndroidView(
                factory = { remoteTextureView!! },
                modifier = Modifier.size(180.dp, 240.dp)
            )
            mEngine.setupRemoteVideo(VideoCanvas(remoteTextureView, Constants.RENDER_MODE_HIDDEN, entry.key))
        }
    }
}

var APP_ID = "e947c59bbe8c4287954cb154e63be817"
var token = "007eJxTYKhN2q1QF7p1noNeluy5M9XLnn+0fKmVxcsnEbc3+pBfSIMCQ6qliXmyqWVSUqpFsomRhbmlqUlykqGpSaqZcVKqhaG5A6dDugAfA8N8H2tGRgZGBhYGRoYAcYd0JjDJDCZZwCQjgxErg6GRsYkpABr3H0k="

fun initEngine(
    current: Context,
    eventHandler: IRtcEngineEventHandler,
    channelName: String,
    userRole: String
): RtcEngine =
    RtcEngine.create(current, APP_ID, eventHandler).apply {
        enableVideo()
        setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING)
        setClientRole(if (userRole == "Broadcaster") Constants.CLIENT_ROLE_BROADCASTER else Constants.CLIENT_ROLE_AUDIENCE)
        joinChannel(token, channelName, null, 0)
    }

@Composable
private fun UserControls(mEngine: RtcEngine) {
    var muted by remember { mutableStateOf(false) }
    var videoDisabled by remember { mutableStateOf(false) }
    val activity = LocalContext.current as? Activity

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 50.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        ControlButton(
            icon = if (muted) Icons.Rounded.Place else Icons.Rounded.Place,
            onClick = {
                muted = !muted
                mEngine.muteLocalAudioStream(muted)
            },
            buttonColor = if (muted) Color.Blue else Color.White,
            iconColor = if (muted) Color.White else Color.Blue,
            size = 50.dp
        )

        ControlButton(
            icon = Icons.Rounded.Call,
            onClick = {
                mEngine.leaveChannel()
                activity?.finish()
            },
            buttonColor = Color.Red,
            iconColor = Color.White,
            size = 70.dp
        )

        ControlButton(
            icon = if (videoDisabled) Icons.Rounded.PlayArrow else Icons.Rounded.PlayArrow,
            onClick = {
                videoDisabled = !videoDisabled
                mEngine.muteLocalVideoStream(videoDisabled)
            },
            buttonColor = if (videoDisabled) Color.Blue else Color.White,
            iconColor = if (videoDisabled) Color.White else Color.Blue,
            size = 50.dp
        )
    }
}

@Composable
private fun ControlButton(
    icon: ImageVector,
    onClick: () -> Unit,
    buttonColor: Color,
    iconColor: Color,
    size: Dp
) {
    OutlinedButton(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier.size(size),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = buttonColor)
    ) {
        Icon(icon, contentDescription = "Control", tint = iconColor)
    }
}
@Composable
private fun AlertScreen(requester: () -> Unit) {
    val context = LocalContext.current

    Log.d("TAG", "AlertScreen: ")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            requestPermissions(
                context as Activity,
                permissions,
                22
            )
            requester()
        }) {
            Icon(Icons.Rounded.Warning, "Permission Required")
            Text(text = "Permission Required")
        }
    }
}

@Composable
private fun UIRequirePermissions(
    permissions: Array<String>,
    onPermissionGranted: @Composable () -> Unit,
    onPermissionDenied: @Composable (requester: () -> Unit) -> Unit
) {
    val context = LocalContext.current

    var grantState by remember {
        mutableStateOf(permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        })
    }

    if (grantState) onPermissionGranted()
    else {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = {
                grantState = it.values.all { granted -> granted }
            }
        )
        onPermissionDenied {
            launcher.launch(permissions)
        }
    }
}
