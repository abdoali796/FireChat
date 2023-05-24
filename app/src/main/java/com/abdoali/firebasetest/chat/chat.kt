package com.abdoali.firebasetest.chat

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.abdoali.firebasetest.R
import com.abdoali.firebasetest.test.timeString
import com.abdoali.firebasetest.ui.theme.ShapesMassageEnd
import com.abdoali.firebasetest.ui.theme.ShapesMassageStart
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chats(
    vm: vm = hiltViewModel()
) {


    val user by vm.userChat.collectAsState()
    val massageList by vm.chat.collectAsState()
    var massage by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val listSata = rememberLazyListState()
    LaunchedEffect(key1 = massageList) {
        scope.launch {
            if (massageList.size > 1) listSata.animateScrollToItem(index = 0)
            user?.uid?.let { vm.readMassage(it) }

        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) ,
        topBar = {
            if (user != null) {


                TopAppBar(

                    title = {
                        Column() {
                            Text(
                                text = user !!.nikeName !! ,
                                style = MaterialTheme.typography.titleLarge
                            )
                            user !!.job?.let {
                                Text(
                                    text = it ,
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }

                    } , actions = {
                        AsyncImage(
                            model = user?.picture ,
                            contentDescription = user?.nikeName ,
                            contentScale = ContentScale.FillBounds ,
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                        )
                    } ,
                    scrollBehavior = scrollBehavior
                )
            }
        } , bottomBar = {


        }) { padd ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = padd)
        ) {
            val (buttom , massge) = createRefs()

            Column(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(massge , constrainBlock = {

                        start.linkTo(parent.start)
                        bottom.linkTo(buttom.top)
                    })
            ) {
                LazyColumn(
                    state = listSata ,
                    contentPadding = padd ,
                    reverseLayout = true ,
                    verticalArrangement = Arrangement.Bottom ,
                    modifier = Modifier.animateContentSize()
                ) {
//                    item {
//                        Spacer(modifier = Modifier.height(30.dp))
//                    }
                    items(items = massageList , key = { massge ->
                        massge !!.time
                    }) { md ->
                        MassageFide(
                            owner = md?.uid != user?.uid ,
                            time = md !!.time ,
                            massageText = md.text ,
                            modifier = Modifier.animateContentSize(animationSpec = tween())
                        )


                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(buttom , constrainBlock = {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)

                    })
            ) {

                MassageEdit(massage = { massage = it } , tec = massage , action = {
                    if (massage != "") {
                        vm.sandMassage(massage)
                        massage = ""
                    }
                }


                )
            }

        }


    }

}

@Composable
fun MassageFide(
    time: Long , massageText: String , owner: Boolean , modifier: Modifier = Modifier
) {
    val colorOwner =
        if (owner) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.onPrimaryContainer
    val alignment = if (owner) Alignment.Start else Alignment.End
    val textAlign = if (owner) TextAlign.Start else TextAlign.End
    val shapes = if (owner) ShapesMassageStart else ShapesMassageEnd
    Column(
        modifier.fillMaxWidth() , horizontalAlignment = alignment
    ) {

        Card(

            shape = shapes ,

            modifier = modifier
                .padding(top = 3.dp , start = 10.dp , end = 10.dp)

                .clip(
                    shape = shapes
                )
                .border(
                    width = 0.4.dp , color = Color.Black , shape = shapes
                )
                .background(colorOwner)

        ) {
            Column(
                horizontalAlignment = alignment , modifier = modifier.padding(
                    start = 12.dp , end = 12.dp , top = 3.dp , bottom = 2.dp
                )
            ) {
                Text(
                    text = timeString(time) ,
                    style = MaterialTheme.typography.labelSmall ,
                    textAlign = textAlign
                )
                Text(text = massageText , style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MassageEdit(
    tec: String , massage: (String) -> Unit , action: () -> Unit
) {
    val text = remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(value = tec ,
            onValueChange = { massage(it);text.value = it } ,
            placeholder = { Text(text = stringResource(R.string.massage)) } ,
            shape = ShapesMassageStart ,
            modifier = Modifier
                .weight(0.7f)
                .padding(4.dp , bottom = 8.dp)
        )
        Button(
            onClick = action ,
            enabled = text.value != "" ,
            shape = MaterialTheme.shapes.medium ,
            modifier = Modifier
                .weight(0.2f)
                .padding(4.dp , bottom = 8.dp)
                .height(TextFieldDefaults.MinHeight)

//                .clip(CircleShape)
//                .clipToBounds()
        ) {
            Icon(
                Icons.Filled.Send ,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Vc() {
    MassageEdit(tec = "f" , { } , {})
}

@Preview(showBackground = true , heightDp = 680)
@Composable
fun Sa() {
    Chats()
}

@Preview
@Composable
fun Cr() {
    MassageFide(445 , "kfkgg" , owner = false)
}

