package com.abdoali.firebasetest.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.abdoali.firebasetest.UserItem
import com.abdoali.firebasetest.chat.navToChats
import com.abdoali.firebasetest.temp.Loading

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchUi(
    vm: SearchVm = hiltViewModel(),
    navController: NavController
) {
    val searchText by vm.searchText.collectAsState()
    val userList by vm.userList.collectAsState()
    val isSearch by vm.isSearching.collectAsState()
Scaffold() {padding->


    Column(Modifier.fillMaxSize().padding(paddingValues = padding)) {
        TextField(
            value = searchText ,
            onValueChange = vm::onSearchTextChange ,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search by job ,email user name or nike name")},
            trailingIcon={ Icon(Icons.Filled.Search, contentDescription = null)}
        )
        Spacer(modifier = Modifier.height(19.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ){
            if (userList!=null){
            items(userList!!){user->
                if (user != null) {
                 UserItem(user = user) {
//                     vm.addFriend(user.uid!!)
                     navController.navToChats(uid = user.uid!!)
                 }
                }}
            }else{
                item{
                    Loading (isLoading = {it})
                }
            }
        }
    }}
}


