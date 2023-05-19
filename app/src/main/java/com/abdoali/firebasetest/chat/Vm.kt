package com.abdoali.firebasetest.chat

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.firebasetest.dataLayer.Mass
import com.abdoali.firebasetest.dataLayer.RepositoryChat
import com.abdoali.firebasetest.dataLayer.User
import com.abdoali.firebasetest.login.TAGVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class vm @Inject constructor(
    stateHandle: SavedStateHandle ,
    private val repositoryChat: RepositoryChat
) : ViewModel() {


    var userChat = MutableStateFlow<User?>(null)

    private val _chat= MutableStateFlow<MutableList<Mass?>>(mutableListOf())
    val chat: StateFlow<MutableList<Mass?>>
        get() =_chat


    init {

        val userChatUid = getUserChat(stateHandle)
        userChatUid.let {
            if (it != null) {
                getUserAndChat(it)

            }
        }


    }

    private fun getUserAndChat(uid: String) {
        viewModelScope.launch {

            userChat.emit(repositoryChat.getChatUser(uid))
repeat(Int.MAX_VALUE) {
    _chat.update { repositoryChat.getChatMassage(uid) }
    Log.i(TAGVM ,"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu")
    delay(1000)
}
        }
    }

//    suspend fun updateChat(){
//        _chat.update { repositoryChat.getChatMassage(userChat.value?.uid.toString()) }
//
//    }

    fun sandMassage(massage: String) {
        viewModelScope.launch {
            repositoryChat.sandMassage(massage , userChat.value?.uid.toString())
        }
    }

  suspend  fun readMassage(uid: String) {

            repositoryChat.readLastMassage(uid)

    }
}
