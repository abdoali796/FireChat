package com.abdoali.firebasetest.login

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.firebasetest.dataLayer.LoginSata
import com.abdoali.firebasetest.dataLayer.RepositoryChat
import com.abdoali.firebasetest.dataLayer.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
 private  val _user= MutableStateFlow<User?>(User())
    val user:StateFlow<User?>
        get() = _user
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

    fun getUserInfo(){
        viewModelScope.launch {
            _user.update { login.getProfile() }
        }
    }

    fun Updateporfile(user: User): Flow<LoginSata> = flow {
        emit(LoginSata.Loading)
        emit(login.updateProfile(user))
    }





}


const val TAGVM = "TAGVM"