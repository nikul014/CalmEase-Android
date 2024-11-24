//package com.example.calmease.ui.screen.sessions
//
//import android.app.Activity
//import android.content.pm.PackageManager
//import android.util.Log
//import android.view.SurfaceView
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.Warning
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.viewinterop.AndroidView
//import com.example.calmease.network.fetchToken
//import androidx.compose.ui.*
//import androidx.compose.ui.graphics.Color
//import androidx.core.app.ActivityCompat.requestPermissions
//import androidx.core.content.ContextCompat
//import io.agora.rtc.IRtcEngineEventHandler
//import io.agora.rtc.RtcEngine
//import io.agora.rtc.video.VideoCanvas
//import io.agora.rtc.video.VideoEncoderConfiguration
//
//@Composable
//fun PublisherScreen(sessionId: String) {
//    val context = LocalContext.current
//    val permissions = arrayOf(
//        android.Manifest.permission.INTERNET,
//        android.Manifest.permission.RECORD_AUDIO,
//        android.Manifest.permission.CAMERA
//    )
//
//    // State to track permissions status
//    var allPermissionsGranted by remember { mutableStateOf(false) }
//
//    // Permission launcher
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions(),
//        onResult = { permissionsGranted ->
//            allPermissionsGranted = permissionsGranted.values.all { it }
//            if (!allPermissionsGranted) {
//                Toast.makeText(context, "Permissions Denied!", Toast.LENGTH_SHORT).show()
//            }
//        }
//    )
//
//    // Request permissions on launch
//    LaunchedEffect(Unit) {
//        launcher.launch(permissions)
//    }
//
//    // Render content only if permissions are granted
//    if (allPermissionsGranted) {
//        PublisherView(sessionId)
//    }
//}
//
//@Composable
//fun PublisherView(sessionId: String) {
//    val context = LocalContext.current
//
//    // Manage AgoraClient Lifecycle
//    val agoraClient by remember {
//        mutableStateOf(
//            RtcEngine.create(context, "e947c59bbe8c4287954cb154e63be817", object : IRtcEngineEventHandler() {})
//        )
//    }
//    var localViewId by remember { mutableStateOf(0) }
//
//    // Effect to configure and join channel
//    LaunchedEffect(sessionId) {
//        val token = fetchToken(sessionId) // Replace with your API fetch logic
//        agoraClient.apply {
//            enableVideo()
//            setVideoEncoderConfiguration(
//                VideoEncoderConfiguration(
//                    VideoEncoderConfiguration.VideoDimensions(640, 480),
//                    VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
//                    VideoEncoderConfiguration.STANDARD_BITRATE,
//                    VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE
//                )
//            )
//            joinChannel(token, sessionId, "", 12345)
//        }
//    }
//
//    // Ensure proper cleanup
//    DisposableEffect(Unit) {
//        onDispose {
//            agoraClient.leaveChannel()
//            RtcEngine.destroy()
//        }
//    }
//
//    // UI for Video Streaming
//    Box(modifier = Modifier.fillMaxSize()) {
//        AndroidView(
//            modifier = Modifier.fillMaxSize(),
//            factory = {
//                SurfaceView(context).apply {
//                    agoraClient.setupLocalVideo(VideoCanvas(this, VideoCanvas.RENDER_MODE_HIDDEN, 0))
//                    localViewId = 0
//                }
//            }
//        )
//    }
//}
//@Composable
//fun UIRequirePermissions(
//    permissions: Array<String>,
//    onPermissionGranted: @Composable () -> Unit,
//    onPermissionDenied: @Composable (requester: () -> Unit) -> Unit
//) {
//    Log.d("TAG", "UIRequirePermissions: ")
//    val context = LocalContext.current
//
//    var grantState by remember {
//        mutableStateOf(permissions.all {
//            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
//        })
//    }
//
//    if (grantState) onPermissionGranted()
//    else {
//        val launcher = rememberLauncherForActivityResult(
//            contract = ActivityResultContracts.RequestMultiplePermissions(),
//            onResult = {
//                grantState = !it.containsValue(false)
//            }
//        )
//        onPermissionDenied {
//            Log.d("TAG", "launcher.launch")
//            launcher.launch(permissions)
//        }
//    }
//}
//
//@Composable
//private fun AlertScreen(requester: () -> Unit) {
//    val context = LocalContext.current
//
//    Log.d("TAG", "AlertScreen: ")
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(color = Color.Red),
//        contentAlignment = Alignment.Center
//    ) {
//        Button(onClick = {
//            requestPermissions(
//                context as Activity,
//                permissions,
//                22
//            )
//            requester()
//        }) {
//            Icon(Icons.Rounded.Warning, "Permission Required")
//            Text(text = "Permission Required")
//        }
//    }
//}