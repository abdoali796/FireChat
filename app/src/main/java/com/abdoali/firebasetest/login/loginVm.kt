package com.abdoali.firebasetest.login

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.abdoali.firebasetest.dataLayer.LoginSata
import com.abdoali.firebasetest.dataLayer.RepositoryChat
import com.abdoali.firebasetest.dataLayer.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class LoginVm @Inject constructor(
    private val login: RepositoryChat
) : ViewModel() {

//    private val _ListUser = MutableStateFlow<MutableList<User?>?>(null)
//    val listStateFlow: StateFlow<MutableList<Friends?>?>
//        get() = login.friendsList
//init {
//    viewModelScope.launch {
//    login.getFriends()
//login.getList()
//
//}}
    fun singIn(email: String , password: String): Flow<LoginSata> = flow()
    {
        emit(LoginSata.Loading)
        emit(login.singIn(email , password))

    }

    fun singUp(email: String , password: String , userName: String): Flow<LoginSata> = flow() {
        emit(LoginSata.Loading)
        emit(login.singUp(email , password , userName))
    }

    fun resatPass(email: String): Flow<LoginSata> = flow {
        emit(LoginSata.Loading)
        emit(login.reSetPassWord(email))
    }

    fun updatePic(uri: Uri?): Flow<LoginSata> = flow {
        emit(LoginSata.Loading)
        emit(login.updatePic(uri = uri))
    }

    fun getUserInfo(): Flow<User> = flow {
        login.getProfile()?.let { emit(it) }
    }

    fun Updateporfile(user: User): Flow<LoginSata> = flow {
        emit(LoginSata.Loading)
        emit(login.updateProfile(user))
    }

//    fun getUserList() {
//        viewModelScope.launch {
//
//login.getList()
//            login.getFriends()
//Log.i(TAGVM,"Faa ${login.friendsList.value}")
//        }
//    }





}


const val TAGVM = "TAGVM"