package com.abdoali.firebasetest.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.firebasetest.dataLayer.Friends
import com.abdoali.firebasetest.dataLayer.RepositoryChat
import com.abdoali.firebasetest.login.TAGVM
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenVM @Inject constructor(
    private val repositoryChat: RepositoryChat,
    private val database: FirebaseDatabase,
    private val currencyUser:FirebaseUser?
) : ViewModel() {

//    val : StateFlow<MutableList<Friends?>?>
//        get() = repositoryChat.friendsList
    private var _friends = MutableStateFlow<MutableList<Friends?>>(mutableListOf())
  val listStateFlow: StateFlow<MutableList<Friends?>>
        get() = _friends

    init {
        viewModelScope.launch {
//            repositoryChat.getFriends()
            repositoryChat.getList()

                Log.i(TAGVM , "chaxxxxxxxxxn${listStateFlow.value}")
            getFriends()
        }

    }

 private  suspend  fun getFriends() {
     try {
         repeat(1200) {
             _friends.update { repositoryChat.getFriends() !! }
         delay(2000)
             Log.i(TAGVM , "delaydelaydelaydelay")
         }
         } catch (e:Exception){
             Log.i(TAGVM , e.message.toString())
         }

    }
//    private  suspend fun friendsLister(add:Boolean){
//
//        val listener=object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (!add)      {
//                    database.reference.child(Constrain.friend).child(currencyUser!!.uid)
//                        .removeEventListener(this)
//                }
//                val friendsList = mutableListOf<Friends?>()
//                snapshot.children.forEach {
//                    val friends = it.getValue<Friends>()
//
//                    friendsList.add(friends)
//                    Log.i(TAGVM , "friends${friendsList}")
//                    _friends.update { friendsList }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        }
//        if (add){
//            database.reference.child(Constrain.friend).child(currencyUser !!.uid).addValueEventListener(listener)
//
//        }
//        if (!add){
//            database.reference.child(Constrain.friend).child(currencyUser!!.uid).removeEventListener(listener)
//            database.reference.removeEventListener(listener)
//            database.reference.removeEventListener(listener)
//Log.i(TAGVM,"jjjj${database.app.removeLifecycleEventListener { firebaseAppName, options ->options.projectId  }}")
//            Log.i(TAGVM,currencyUser.email.toString())
//        }
//    }

    fun clearChat() = repositoryChat.clearChat()
    fun singOut() {
        viewModelScope.launch {

            repositoryChat.singOut()
        }


    }


//    fun getUserList() {
//        viewModelScope.launch {
//
//            repositoryChat.getList()
//            repositoryChat.getFriends()
//            Log.i(TAGVM ,"Faa ${repositoryChat.friendsList.value}")
//        }
//    }


}