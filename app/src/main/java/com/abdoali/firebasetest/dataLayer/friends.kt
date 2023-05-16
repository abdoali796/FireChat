package com.abdoali.firebasetest.dataLayer

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Friends(
    var uid: String? = "" ,
    var userName: String? = "user name" ,
    var nikeName: String? = "nike name" ,
    var picture: String? = "https://firebasestorage.googleapis.com/v0/b/test-for-firebase-297cb.appspot.com/o/de%20Pic%2F_76cb768a-dd88-41eb-8e13-ee45f98374af.jpg?alt=media&token=d78999c0-57f8-4759-b597-a209b9e8b622" ,
    var job: String? = "job" ,
    var age: Int? = 0 ,
    var email: String? = "" ,
    var info: String? = "" ,
    var lastMass:String?="",
    var readLastMass:Boolean= true

    ){
    fun readMassage():Friends{
        return Friends(
            uid, userName, nikeName, picture, job, age, email, info, lastMass, true
        )
    }
}
