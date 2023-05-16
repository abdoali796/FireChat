package com.abdoali.firebasetest.temp

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.abdoali.firebasetest.R

@Composable
fun ErrorDialog(
    errorMassge:String,
    showDialog: (Boolean)->Unit
) {
    AlertDialog(onDismissRequest = { showDialog(false) } ,
        title = { Text(text = stringResource(R.string.authFailed)) } ,
        text = { Text(text = errorMassge , color = MaterialTheme.colors.error) } ,
        buttons = {
            Button(onClick = { showDialog(false)}) {
                Text(text = "ok")
            }
        })
}

@Preview
@Composable
fun error() {
    ErrorDialog(errorMassge = "aaaaaaaaaaa" , showDialog ={} )
}