package com.abdoali.firebasetest.mainScreen


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abdoali.firebasetest.FriendItem
import com.abdoali.firebasetest.R
import com.abdoali.firebasetest.chat.navToChats
import com.abdoali.firebasetest.dataLayer.Friends
import com.abdoali.firebasetest.login.INFO
import com.abdoali.firebasetest.login.singOut
import com.abdoali.firebasetest.search.navToSearch
import com.abdoali.firebasetest.test.isScrollingUp

@Composable
fun MainScreen(
    navController: NavController , vm: MainScreenVM = hiltViewModel()
) {

    val ursers by vm.listStateFlow.collectAsState()


    MainScreenImp(ursers = ursers ,
        singOutAction = {
        vm.singOut()
        navController.singOut()
    } ,
        infoAction = { navController.navigate(INFO) } ,

        clickAction = {


            navController.navToChats(it.uid !!)
        } , getUserAction = {

        }, searchAction ={
            navController.navToSearch()

        } )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenImp(

    ursers: MutableList<Friends?>? ,
    singOutAction: () -> Unit ,
    infoAction: () -> Unit ,
    clickAction: (Friends) -> Unit ,
    getUserAction: () -> Unit ,
    searchAction: () -> Unit ,

    ) {

    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) ,
        floatingActionButton = {
            androidx.compose.material3.ExtendedFloatingActionButton(

                text = { Text(text = stringResource(R.string.sand_massage)) } ,
                onClick = searchAction ,
                expanded = listState.isScrollingUp() ,
                icon = { Icon(Icons.Filled.Add , contentDescription = null) } ,

                )
        } ,
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            } , scrollBehavior = scrollBehavior , navigationIcon = {
                IconButton(
                    onClick = singOutAction
                ) {
                    Icon(imageVector = Icons.Filled.ExitToApp , contentDescription = null)
                }
            } , actions = {
                IconButton(onClick = infoAction) {
                    Icon(Icons.Filled.AccountCircle , contentDescription = null)
                }
            })


        } ,

        floatingActionButtonPosition = FabPosition.End) {


        LazyColumn(
            state = listState , contentPadding = it
        ) {
            if (ursers != null) {
                items(ursers) { firand ->

                    if (firand != null) {
                        FriendItem(firand , clickAction = { clickAction(firand) })
                    }
                }
            }
        }

    }

    LaunchedEffect(key1 = ursers) {
        getUserAction()

    }

}


//@Preview(uiMode = UI_MODE_NIGHT_YES , wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
//@Composable
//fun ScreenPre() {
//    val user = Friends(
//        nikeName = "abdoAli" ,
//        job = "man" ,
//        age = 29 ,
//        email = "abdoali@gaama" ,
//        info = "infoinfoinfoinfoinfoinfoinfoinfoinfo"
//    )
//    MainScreenImp(
//
//        ursers = mutableListOf(
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user ,
//            user
//        ) , {} , {} , {} , {}) {}
//}

