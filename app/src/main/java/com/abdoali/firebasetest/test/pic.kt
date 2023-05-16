package com.abdoali.firebasetest.test

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import coil.compose.rememberAsyncImagePainter

@Composable
fun Test() {
    var result = remember {
        mutableStateOf<Uri?>(null)
    }
//    val rememberImagePainter= remember

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()){
        result.value = it
    }
    Column {
        Button(onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
            Icon(Icons.Default.Face, contentDescription = null)

        }

        result.value?.let { Text(text = it.toString()) }
        Image(rememberAsyncImagePainter(result.value?.let { it }), contentDescription = null)
//        Image(rememberAsyncImagePainter(File(result.value)), contentDescription = null)
//        Log.i(TAG,result.value.toString())

    }
}