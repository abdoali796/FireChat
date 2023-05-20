package com.abdoali.firebasetest.dataLayer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import com.abdoali.firebasetest.login.TAGVM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.Calendar
import javax.inject.Inject

interface RepositoryChat {


    suspend fun singIn(email: String , password: String): LoginSata
    suspend fun singUp(email: String , password: String , userName: String): LoginSata
    suspend fun reSetPassWord(email: String): LoginSata
    suspend fun updateProfile(user: User): LoginSata
    suspend fun getProfile(): User?
    suspend fun updatePic(uri: Uri?): LoginSata
    suspend fun getList(): MutableList<User?>

    //    suspend fun addFriend(uid: String): LoginSata
    suspend fun getFriends(): MutableList<Friends?>
    suspend fun getChatUser(uid: String): User?
    suspend fun getChatMassage(uid: String): MutableList<Mass?>
    suspend fun sandMassage(massage: String , uid: String)
    suspend fun readLastMassage(uid: String)
//    fun clearChat()
//    val massage: StateFlow<MutableList<Mass?>>
//    val userList: StateFlow<MutableList<User?>?>
//    val friendsList: StateFlow<MutableList<Friends?>>
    fun singOut()
}

class RepositoryChatImp @Inject constructor(
    private val firebaseAut: FirebaseAuth ,
    private val database: FirebaseDatabase ,
    private val storage: FirebaseStorage ,
    private val context: Context

) : RepositoryChat {
//    private var _massage = MutableStateFlow<MutableList<Mass?>>(mutableListOf())
//    override val massage: StateFlow<MutableList<Mass?>>
////        get() = _massage
//    private var _userList = MutableStateFlow<MutableList<User?>>(mutableListOf())
//    override val userList: StateFlow<MutableList<User?>>
//        get() = _userList
//
//    private var _friends = MutableStateFlow<MutableList<Friends?>>(mutableListOf())
//    override val friendsList: StateFlow<MutableList<Friends?>>
//        get() = _friends


    override suspend fun singIn(email: String , password: String): LoginSata {
        return try {
            firebaseAut.signInWithEmailAndPassword(email.trim() , password.trim()).await()
            LoginSata.Succeed

        } catch (e: Exception) {

            LoginSata.Error(e.message.toString())
        }

    }

    override suspend fun singUp(email: String , password: String , userName: String): LoginSata {
        return try {
            firebaseAut.createUserWithEmailAndPassword(email.trim() , password.trim()).await()

            firebaseAut.currentUser?.updateProfile(userProfileChangeRequest {
                displayName = userName

            })?.await()

            database.reference.child(Constrain.user).child(firebaseAut.currentUser !!.uid)
                .setValue(
                    User(
                        uid = getCurrentUser() !!.uid ,
                        userName = getCurrentUser() !!.displayName ,
//                        email = getCurrentUser() !!.email
                    )
                )

            LoginSata.Succeed
        } catch (e: Exception) {
            LoginSata.Error(e.message.toString())
        }

    }

    override suspend fun reSetPassWord(email: String): LoginSata {
        return try {
            firebaseAut.sendPasswordResetEmail(email).await()
            LoginSata.Succeed
        } catch (e: Exception) {
            LoginSata.Error(e.message.toString())
        }

    }

    override suspend fun updateProfile(user: User): LoginSata {
        return try {

            database.reference.child(Constrain.user).child(getCurrentUser() !!.uid).setValue(
                User(
                    uid = getCurrentUser() !!.uid ,
                    email = getCurrentUser() !!.email ,
                    userName = getCurrentUser() !!.displayName ,
                    picture = user.picture ,
                    info = user.info ,
                    job = user.job ,
                    nikeName = user.nikeName ,
                    age = user.age ,

                    )
            ).await()
            LoginSata.Succeed
        } catch (e: Exception) {
            LoginSata.Error(e.message.toString())
        }


    }

    override suspend fun getProfile(): User? {
        val snapshot =
            database.reference.child(Constrain.user).child(getCurrentUser() !!.uid).get().await()

        return snapshot.getValue(User::class.java)
    }

    override suspend fun updatePic(uri: Uri?): LoginSata {
        val coms = compressImage(uri !!)
        return try {
            val storeReferences =
                storage.reference.child(Constrain.user).child(getCurrentUser() !!.uid)
                    .putFile(coms !!.normalizeScheme() !!).await()

            val databaseReference =
                database.reference.child(Constrain.user).child(getCurrentUser() !!.uid)
            val user = getCurrentUserProfile()
            databaseReference.setValue(
                User(
                    uid = user !!.uid ,
                    userName = user.userName ,
                    email = user.email ,
                    picture = storeReferences.storage.downloadUrl.await().toString() ,
                    info = user.info ,
                    job = user.job ,
                    nikeName = user.nikeName ,
                    age = user.age ,

                    )
            ).await()


            LoginSata.Succeed
        } catch (e: Exception) {
            LoginSata.Error(e.message.toString())
        }
    }

    override suspend fun getList(): MutableList<User?> {
        val userList = mutableListOf<User?>()
database.reference.child(Constrain.user).get().await().children.forEach {
    val user = it.getValue(User::class.java)
    userList.add(user)
}
//        _userList.update { userList }
        return userList
//        database.reference.child(Constrain.user).addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                snapshot.children.forEach {
//
//                }
//
//                _userList.update { userList }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })

    }

//    override suspend fun addFriend(uid: String): LoginSata {
//        return try {
//
//            val friend = getChatUser(uid) !!.tofriend("" , true)
//            database.reference.child(Constrain.friend).child(getCurrentUser() !!.uid).child(uid)
//                .setValue(friend).await()
//            LoginSata.Succeed
//        } catch (e: Exception) {
//            LoginSata.Error(e.message.toString())
//        }
//    }

    override suspend fun getFriends(): MutableList<Friends?> {
        Log.i(TAGVM , "Userrrrrrrrrrr${getCurrentUser()?.email}")
        val friends = mutableListOf<Friends?>()


        database.reference.child(Constrain.friend).child(getCurrentUser() !!.uid).get()
            .await().children.forEach {
                val friend = it.getValue<Friends>()
                friends.add(friend)
            }


        return friends
    }


    override suspend fun getChatUser(uid: String): User? {
        return database.reference.child(Constrain.user).child(uid).get().await()
            .getValue(User::class.java)
    }

    private suspend fun getFriend(uid: String): Friends? {
        return database.reference.child(Constrain.friend).child(getCurrentUser() !!.uid).child(uid)
            .get().await().getValue(Friends::class.java)
    }

    override suspend fun getChatMassage(uid: String):MutableList<Mass?> {
        val chat= mutableListOf<Mass?>()
        makeRoom(uid).child(Constrain.chat).get().await().children.forEach {
            val massage=it.getValue(Mass::class.java)
            chat.add(massage)
        }
        chat.reverse()
return chat
//        makeRoom(uid).child(Constrain.chat).addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val chat = mutableListOf<Mass?>()
//                snapshot.children.forEach {
//                    val mass = it.getValue(Mass::class.java)
//                    chat.add(mass)
//                    _massage.update { chat }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })

    }


    override suspend fun sandMassage(massage: String , uid: String) {
        val room = makeRoom(uid)
        val key = room.push().key
        val timeMillis = Calendar.getInstance().timeInMillis

        room.child(Constrain.chat).child(key !!)
            .setValue(Mass(getCurrentUser() !!.uid , timeMillis , massage)).await()
//        room.child(Constrain.lastMass).setValue(massage)
        upDataLastMass(uid = uid , massage = massage)
    }

    override suspend fun readLastMassage(uid: String) {
        try {

            database.reference.child(Constrain.friend).child(getCurrentUser() !!.uid).child(uid)
                .setValue(getFriend(uid) !!.readMassage()).await()
        } catch (E: Exception) {
            Log.i(TAGVM , E.message.toString())
        }


    }

    private suspend fun upDataLastMass(uid: String , massage: String) {
        val friend = getChatUser(uid) !!.tofriend(massage , true)
        database.reference.child(Constrain.friend).child(getCurrentUser() !!.uid).child(uid)
            .setValue(friend).await()
        database.reference.child(Constrain.friend).child(uid).child(getCurrentUser() !!.uid)
            .setValue(getCurrentUserProfile() !!.tofriend(massage , false)).await()
    }

//    override fun clearChat() {
//        _massage.value = mutableListOf()
//    }


    override fun singOut() {
        firebaseAut.signOut()
    }

    private fun getCurrentUser() = firebaseAut.currentUser


    private suspend fun getCurrentUserProfile(): User? {
        return database.reference.child(Constrain.user).child(getCurrentUser() !!.uid).get().await()
            .getValue(User::class.java)
    }

    private suspend fun makeRoom(uid: String): DatabaseReference {
        val roomId =
            if (uid >= getCurrentUser() !!.uid) uid + getCurrentUserProfile() !!.uid else getCurrentUser() !!.uid + uid
        return database.reference.child(Constrain.room).child(roomId)
    }


    private fun compressImage(uri: Uri): Uri? {
        try {

            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(
                    context.contentResolver , uri
                )
            } else {
                val source = ImageDecoder.createSource(context.contentResolver , uri)
                ImageDecoder.decodeBitmap(source)
            }
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG , 0 , bytes)
            val path: String = MediaStore.Images.Media.insertImage(
                context.contentResolver , bitmap , "Title" , null
            )
            return Uri.parse(path)
        } catch (e: Exception) {
            return uri
        }

    }

}