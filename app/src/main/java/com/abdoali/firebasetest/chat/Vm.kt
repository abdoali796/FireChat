package com.abdoali.firebasetest.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.firebasetest.dataLayer.Mass
import com.abdoali.firebasetest.dataLayer.RepositoryChat
import com.abdoali.firebasetest.dataLayer.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class vm @Inject constructor(
    stateHandle: SavedStateHandle ,
    private val repositoryChat: RepositoryChat
) : ViewModel() {


    var userChat = MutableStateFlow<User?>(null)

    val chat: StateFlow<MutableList<Mass?>>
        get() = repositoryChat.massage


    init {

        val userChatUid = getUserChat(stateHandle)
        userChatUid.let {
            if (it != null) {
                getUser(it)

            }
        }


    }

    private fun getUser(uid: String) {
        viewModelScope.launch {
            repositoryChat.getChatMassage(uid)
            userChat.emit(repositoryChat.getChatUser(uid))

        }
    }


    fun sandMassage(massage: String) {
        viewModelScope.launch {
            repositoryChat.sandMassage(massage , userChat.value?.uid.toString())
        }
    }

  suspend  fun readMassage(uid: String) {

            repositoryChat.readLastMassage(uid)

    }
}
