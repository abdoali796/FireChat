package com.abdoali.firebasetest.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.abdoali.firebasetest.R

@Composable
fun RestPassWord(
    showDialog: (Boolean) -> Unit
) {


    var email by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("email")
    }
    var isErrors by remember {
        mutableStateOf(false)
    }
//    if (showDialog)
    Dialog(onDismissRequest = { showDialog(false) }) {
        Surface(
            color = MaterialTheme.colorScheme.onPrimary , shape = MaterialTheme.shapes.small

        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally ,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.rest_password) ,
                    style = MaterialTheme.typography.titleLarge ,
                    fontFamily = FontFamily.Monospace
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email ,
                    onValueChange = { email = it } ,
                    singleLine = true ,
                    label = {
                        Text(text = error)

                    } ,
                    isError = isErrors ,
                    modifier = Modifier.fillMaxWidth(0.8f)

                )

                Button(onClick = {} , shape = MaterialTheme.shapes.medium) {
                    Text(text = stringResource(R.string.rest))
                }

            }
        }
    }
}

@Preview
@Composable
fun rest() {
    RestPassWord {}
}