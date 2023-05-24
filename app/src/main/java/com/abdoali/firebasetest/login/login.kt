@file:OptIn(ExperimentalMaterial3Api::class)

package com.abdoali.firebasetest.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abdoali.firebasetest.R
import com.abdoali.firebasetest.dataLayer.LoginSata
import com.abdoali.firebasetest.mainScreen.popupToMain
import com.abdoali.firebasetest.temp.ApplyChangeDialog
import com.abdoali.firebasetest.temp.ErrorDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(
    navController: NavController
) {

    val tabTitle = listOf(stringResource(R.string.sign_in) , stringResource(R.string.sign_up))
    val tabIcon = listOf(

        painterResource(id = R.drawable.baseline_login_24) ,
        painterResource(id = R.drawable.baseline_person_add_24)
    )
    var tabInt by rememberSaveable { mutableStateOf(0) }

    val scrollableState = rememberScrollState()

    var landing by remember {
        mutableStateOf(false)
    }
    Scaffold(

    ) { padding ->


        Column(

            verticalArrangement = Arrangement.SpaceBetween ,
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollableState)
                .padding(paddingValues = padding)

        ) {


            Image(
                painter = painterResource(id = R.drawable.logo) ,
                contentDescription = "logo" ,
                contentScale = ContentScale.Crop
            )

            when (tabInt) {
                0 -> SignIn(navController = navController) { landing = it }
                1 -> Sign_Up(navController = navController) { landing = it }
            }

            NavigationBar(

            ) {
                tabTitle.forEachIndexed { index , title ->
                    NavigationBarItem(selected = tabInt == index ,
                        onClick = { tabInt = index } ,
                        label = { Text(text = title) } ,
                        icon = { Image(painter = tabIcon[index] , contentDescription = title) } ,
                        alwaysShowLabel = false)
                }
            }
            AnimatedVisibility (landing) {
                ApplyChangeDialog(isLoading = { landing = it })
            }


        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Sign_Up(
    loginVm: LoginVm = hiltViewModel() , navController: NavController , loading: (Boolean) -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    var email by rememberSaveable {
        mutableStateOf("")
    }
    var userName by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var showDig by rememberSaveable { mutableStateOf(false) }
    var errorMassage by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val (userNameFocus , passwordFocus , emailFocus) = remember {
        FocusRequester.createRefs()
    }


    Column(
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally ,

        ) {
        OutlinedTextField(value = email ,
            onValueChange = { email = it } ,
            singleLine = true ,
            label = { Text(text = stringResource(R.string.email)) } ,
            modifier = Modifier.focusRequester(emailFocus) ,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next) ,
            keyboardActions = KeyboardActions(onNext = { userNameFocus.requestFocus() })
        )

        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = userName ,
            onValueChange = { userName = it } ,
            singleLine = true ,
            label = { Text(text = stringResource(R.string.userName)) } ,
            modifier = Modifier.focusRequester(userNameFocus) ,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next) ,
            keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() })
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(value = password ,
            onValueChange = { password = it } ,
            singleLine = true ,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password ,
                imeAction = ImeAction.Done
            ) ,
            label = { Text(text = stringResource(id = R.string.password)) } ,
            trailingIcon = {
                IconButton(onClick = { passwordHidden = ! passwordHidden }) {
                    val icon = if (passwordHidden) Icons.Default.Lock else Icons.Filled.LockOpen
                    Icon(icon , contentDescription = null)
                }
            } ,
            modifier = Modifier.focusRequester(passwordFocus) ,
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = {

            coroutineScope.launch {
                loginVm.singUp(email , password , userName).collect {
                    when (it) {
                        is LoginSata.Error -> {
                            loading(false)
                            showDig = true
                            errorMassage = it.result
                        }

                        LoginSata.Loading -> {
                            loading(true)
                        }

                        LoginSata.Succeed -> {
                            loading(false)
                            navController.navToInfo()
                        }
                    }


                }
            }

        } , shape = MaterialTheme.shapes.medium) {
            Text(text = stringResource(id = R.string.sign_in))
        }
    }


    if (showDig) {
        ErrorDialog(errorMassge = errorMassage , showDialog = { showDig = it })
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignIn(
    navController: NavController , loginVm: LoginVm = hiltViewModel() , loading: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var showDigRest by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var showDig by rememberSaveable { mutableStateOf(false) }
    var errorMassage by remember {
        mutableStateOf("Something went wrong")
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val (emailFocus , passwordFocus) = remember {
        FocusRequester.createRefs()
    }

    Column(
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally ,

        ) {
        OutlinedTextField(value = email ,
            onValueChange = { email = it } ,
            singleLine = true ,
            label = { Text(text = stringResource(id = R.string.email)) } ,
            modifier = Modifier.focusRequester(emailFocus) ,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next) ,
            keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() })
        )



        Spacer(Modifier.height(8.dp))

        OutlinedTextField(value = password ,
            onValueChange = { password = it } ,
            singleLine = true ,
            modifier = Modifier.focusRequester(passwordFocus) ,
            visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None ,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password ,
                imeAction = ImeAction.Done
            ) ,
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }) ,
            label = { Text(text = stringResource(R.string.password)) } ,
            trailingIcon = {
                IconButton(onClick = { passwordHidden = ! passwordHidden }) {
                    val icon = if (passwordHidden) Icons.Default.Lock else Icons.Filled.LockOpen
                    Icon(icon , contentDescription = null)
                }
            })

        Spacer(Modifier.height(8.dp))

        Button(onClick = {
//                save = ! save
            coroutineScope.launch {
                loginVm.singIn(email , password).collect {
                    when (it) {
                        is LoginSata.Error -> {
                            showDig = true
                            errorMassage = it.result
                            loading(false)
                        }

                        LoginSata.Loading -> {
                            loading(true)
                        }

                        LoginSata.Succeed -> {
                            loading(false)
                            navController.popupToMain()
                        }
                    }
                }
            }
        } , shape = MaterialTheme.shapes.medium) {
            Text(text = stringResource(id = R.string.sign_in))
        }
        Text(text = stringResource(R.string.forgetPassword) ,
            modifier = Modifier.clickable { showDigRest = true })
    }

    if (showDigRest) {
        RestPassWord(showDialog = { showDigRest = it })
    }








    if (showDig) {
        ErrorDialog(errorMassage , showDialog = { showDig = it })

    }

}

@Composable
fun RestPassWord(
    vm: LoginVm = hiltViewModel() , showDialog: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

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


            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.rest_password))
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email ,
                    onValueChange = { email = it } ,
                    singleLine = true ,
                    label = {
                        Text(text = error)

                    } ,
                    isError = isErrors ,

                    )
                Button(onClick = {
                    coroutineScope.launch {
                        vm.resatPass(email).collect {
                            when (it) {
                                is LoginSata.Error -> {
                                    isErrors = true
                                    error = it.result
                                }

                                LoginSata.Loading -> {

                                }

                                LoginSata.Succeed -> {
                                    showDialog(false)
                                }


                            }
                        }
                    }

                } , shape = MaterialTheme.shapes.medium) {
                    Text(text = stringResource(R.string.rest))
                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Flo() {
    RestPassWord(showDialog = {})
}


