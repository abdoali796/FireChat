package com.abdoali.firebasetest.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.firebasetest.dataLayer.RepositoryChat
import com.abdoali.firebasetest.dataLayer.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVm @Inject constructor(
    private val repositoryChat: RepositoryChat
) : ViewModel() {

 private val _searchText= MutableStateFlow("")
 val searchText=_searchText.asStateFlow()

 private val _isSearching= MutableStateFlow(false)
 val isSearching=_isSearching.asStateFlow()

private  val _list= MutableStateFlow<MutableList<User?>?>(mutableListOf())
 private val list: StateFlow<MutableList<User?>?>
     get() = _list


 init {
viewModelScope.launch {
_list.update {  repositoryChat.getList()}
}
 }
//    fun addFriend(uid:String){
//        viewModelScope.launch {
//            repositoryChat.addFriend(uid)
//        }
//
//    }

    fun onSearchTextChange(text:String){
        _searchText.value=text

    }

    private val _userList= list
    val userList=searchText
        .combine(_userList){text , user->
            if (text.isBlank()){
                user
            }else{
                user?.filter {
                    it!!.query(text)
                }
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(59999),
            _userList.value
        )

}


fun User.query(query:String):Boolean{
    val matching= listOf(
        userName,nikeName,job,email
    )
    return matching.any {
        it!!.contains(query,ignoreCase = true)
    }







}