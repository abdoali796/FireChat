package com.abdoali.firebasetest

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.abdoali.firebasetest.dataLayer.LoginSata
import com.abdoali.firebasetest.dataLayer.User
import com.abdoali.firebasetest.login.LoginVm
import com.abdoali.firebasetest.login.TAGVM
import com.abdoali.firebasetest.mainScreen.popupToMain
import com.abdoali.firebasetest.temp.LoadingAnimationDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class , ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun Info(
    navController: NavController ,
    vm: LoginVm = hiltViewModel()
) {
    val user by vm.user.collectAsState()
    var email by remember {
        mutableStateOf("")
    }
    var userName by remember {
        mutableStateOf("")
    }
    var info by remember {
        mutableStateOf("")
    }
    val (nikaNameF , jobF , ageF , infoF) = remember {
        FocusRequester.createRefs()
    }

    var nikaName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf<String>("0") }
    var job by remember { mutableStateOf("") }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var errorMassage by remember {
        mutableStateOf("")
    }
    var isLoading by remember {
        mutableStateOf(false)
    }
    val keybord = LocalSoftwareKeyboardController.current
    val scrollableState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var uri by remember {
        mutableStateOf<Uri?>(user?.picture?.toUri())
    }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
            uri = it
        }
    LaunchedEffect(key1 = true , key2 = user) {

        vm.getUserInfo()
            if (user == User()) {
                isLoading = true
            } else {
Log.i(TAGVM,"kkkklllllllllllllllllllll"+user?.email.toString())
                isLoading = false
                uri = user?.picture?.toUri()
                userName = user?.userName.toString()
                email = user?.email.toString()
                info = user?.info.toString()
                nikaName = user?.nikeName.toString()
                age = user?.age.toString()
                job = user?.job.toString()
            }
        }


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.update_profile))}
            , navigationIcon = {
                IconButton(onClick = { navController.popupToMain() }) {
                    Icon(Icons.Default.Home , contentDescription =null )
                }
                }
            )

        }

    ) { padding ->


        if (isLoading) {
            LoadingAnimationDialog(isLoading = { isLoading = it })
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,

            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollableState)
                .padding(paddingValues = padding)

        ) {

            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                Row() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally


                    ) {
                        AsyncImage(model = uri ,
                            contentDescription = null ,
                            contentScale = ContentScale.Crop ,
                            modifier = Modifier
                                .size(120.dp)
                                .border(BorderStroke(1.dp , Color.Black) , CircleShape)
                                .clip(CircleShape)
                                .clickable {
                                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                } ,
                            placeholder = painterResource(R.drawable.baseline_login_24))
                        IconButton(onClick = {
                            coroutineScope.launch {
                                vm.updatePic(uri).collect {
                                    when (it) {
                                        is LoginSata.Error -> {
                                            isLoading = false
                                            showDialog = true
                                            errorMassage = it.result
                                        }

                                        LoginSata.Loading -> {
                                            isLoading = true
                                        }

                                        LoginSata.Succeed -> {
                                            isLoading = false

                                        }
                                    }
                                }
                            }
                        } , Modifier.size(24.dp)) {
                            Icon(Icons.Default.Image , contentDescription = null)
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(start = 12.dp)
                    ) {
                        Text(text = userName , style = MaterialTheme.typography.headlineMedium)
                        Text(text = email , style = MaterialTheme.typography.headlineSmall)
                    }
                }
            }



            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(value = nikaName ,
                onValueChange = { nikaName = it } ,
                singleLine = true ,
                label = { Text(text = stringResource(R.string.nikeName)) } ,
                modifier = Modifier.focusRequester(nikaNameF) ,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next) ,
                keyboardActions = KeyboardActions(onNext = { jobF.requestFocus() })
            )


            OutlinedTextField(value = job ,
                onValueChange = { job = it } ,
                singleLine = true ,
                label = { Text(text = stringResource(R.string.job)) } ,
                modifier = Modifier.focusRequester(jobF) ,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next) ,
                keyboardActions = KeyboardActions(onNext = { ageF.requestFocus() })
            )

            OutlinedTextField(value = age ,
                onValueChange = { age = it } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number ,
                    imeAction = ImeAction.Next
                ) ,
                singleLine = true ,
                label = { Text(text = stringResource(R.string.age)) } ,
                modifier = Modifier.focusRequester(ageF) ,
                keyboardActions = KeyboardActions(onNext = { infoF.requestFocus() })
            )
            OutlinedTextField(value = info ,
                onValueChange = { info = it } ,
                singleLine = true ,
                label = { Text(text = stringResource(R.string.info)) } ,
                modifier = Modifier.focusRequester(infoF) ,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done) ,
                keyboardActions = KeyboardActions(onDone = { keybord?.hide() })
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {
                coroutineScope.launch {
                    val tem = User(
                        uid = "" ,
                        email = email ,
                        userName = userName ,
                        info = info ,
                        picture = uri.toString() ,
                        job = job ,
                        age = age.toInt() ,
                        nikeName = nikaName

                    )
                    vm.Updateporfile(

                        tem
                    ).collect {
                        when (it) {
                            is LoginSata.Error -> {
                                isLoading = false
                                showDialog = true
                                errorMassage = it.result
                            }

                            LoginSata.Loading -> {
                                isLoading = true
                            }

                            LoginSata.Succeed -> {

                                isLoading = false
                            }
                        }
                    }

                }
            }) {
                Text(text = stringResource(R.string.update))
            }


        }
    }
}

//@Composable
//fun InfoCord() {
//    Card(
//        Modifier
//            .fillMaxWidth()
//            .padding(16.dp) ,
//    ) {
//        Row() {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally
//
//
//            ) {
//                AsyncImage(
//                    model = "oii" ,
//                    contentDescription = null ,
//                    contentScale = ContentScale.Crop ,
//                    modifier = Modifier
//                        .padding(top = (- 5).dp)
//                        .size(120.dp)
//                        .border(BorderStroke(1.dp , Color.Black) , CircleShape)
//                        .clip(CircleShape)
//                )
//                Button(onClick = { /*TODO*/ }) {
//
//                }
//            }
//            Column(
//                verticalArrangement = Arrangement.Center ,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(130.dp)
//                    .padding(start = 12.dp)
//            ) {
//                Text(text = "aaaaaaaa" , style = MaterialTheme.typography.displayLarge)
//                Text(text = "dsdfcscx" , style = MaterialTheme.typography.labelSmall)
//            }
//        }
//    }
//}
//
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewInfo() {
//    InfoCord()
//}

