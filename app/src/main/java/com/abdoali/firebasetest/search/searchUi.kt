package com.abdoali.firebasetest.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abdoali.firebasetest.R
import com.abdoali.firebasetest.UserCard
import com.abdoali.firebasetest.UserItem
import com.abdoali.firebasetest.chat.navToChats


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchUi(
    vm: SearchVm = hiltViewModel(),
    navController: NavController
) {
    val searchText by vm.searchText.collectAsState()
    val userListFilter by vm.userListFilter.collectAsState()
    val isSearch by vm.isSearching.collectAsState()
    val userList by vm.list.collectAsState()


Box(modifier = Modifier
    .semantics {
        isContainer = true
    }
    .zIndex(1f)
    .fillMaxSize()
) {
    Spacer(modifier = Modifier.background(MaterialTheme.colorScheme.background).fillMaxSize())
    LazyColumn(
        contentPadding = PaddingValues(top = 70.dp)
    ){
        if (userList != null) {
            items(userList !!) { user ->
                if (user != null) {
                    UserItem(user = user) {
//                     vm.addFriend(user.uid!!)
                        navController.navToChats(uid = user.uid !!)
                    }
                }
            }
        }
    }
    SearchBar(
        modifier = Modifier.align(Alignment.TopCenter) ,
        query = searchText ,
        onQueryChange = vm::onSearchTextChange ,
        active = isSearch ,
        onSearch = { vm.onSearch(false) } ,
        onActiveChange = vm::onSearch ,
        placeholder = { Text(text = stringResource(R.string.search))},
        trailingIcon={ Icon(Icons.Filled.Search, contentDescription = null)}
        ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
            //   .weight(1f)
        ) {
            if (userListFilter != null) {
                items(userListFilter !!) { user ->
                    if (user != null) {
                        UserCard(user = user) {
//                     vm.addFriend(user.uid!!)
                            navController.navToChats(uid = user.uid !!)
                        }
                    }
                }
            }
        }
    }
}}
//    Box(Modifier.fillMaxSize().padding(paddingValues = padding)) {
//        TextField(
//            value = searchText ,
//            onValueChange = vm::onSearchTextChange ,
//            modifier = Modifier.fillMaxWidth(),
//            placeholder = { Text(text = "Search by job ,email user name or nike name")},
//            trailingIcon={ Icon(Icons.Filled.Search, contentDescription = null)}
//        )
//        Spacer(modifier = Modifier.height(19.dp))
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//             //   .weight(1f)
//        ){
//            if (userList!=null){
//            items(userList!!){user->
//                if (user != null) {
//                 UserItem(user = user) {
////                     vm.addFriend(user.uid!!)
//                     navController.navToChats(uid = user.uid!!)
//                 }
//                }}
//            }else{
//                item{
//                    Loading (isLoading = {it})
//                }
//            }
//        }
//    }
//
//    }
//
//}}
