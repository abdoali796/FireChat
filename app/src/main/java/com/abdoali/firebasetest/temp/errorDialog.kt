package com.abdoali.firebasetest.temp

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.abdoali.firebasetest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(
    errorMassge:String,
    showDialog: (Boolean)->Unit
) {
    AlertDialog(onDismissRequest = { showDialog(false) } ,
        confirmButton = {
            Button(onClick = { showDialog(false)}) {
                Text(text = "ok")
            }
        } ,
        title = { Text(text = stringResource(R.string.authFailed)) } ,
        text = { Text(text = errorMassge , color = MaterialTheme.colorScheme.error) })

}

@Preview
@Composable
fun error() {
    ErrorDialog(errorMassge = "aaaaaaaaaaa" , showDialog ={} )
}