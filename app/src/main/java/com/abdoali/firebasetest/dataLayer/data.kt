package com.abdoali.firebasetest.dataLayer

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var uid: String? = "" ,
    var userName: String? = "user name" ,
    var nikeName: String? = "nike name" ,
    var picture: String? = "https://firebasestorage.googleapis.com/v0/b/test-for-firebase-297cb.appspot.com/o/de%20Pic%2F_76cb768a-dd88-41eb-8e13-ee45f98374af.jpg?alt=media&token=d78999c0-57f8-4759-b597-a209b9e8b622" ,
    var job: String? = "job" ,
    var age: Int? = 0 ,
    var email: String? = "" ,
    var info: String? = "" ,
    var deviceToken:String?=""

    ) {
    fun tofriend(mass: String , read: Boolean): Friends {
        return Friends(
            uid = this.uid ,
            userName = this.userName ,
            nikeName = this.nikeName ,
            picture = this.picture ,
            job = this.job ,
            age = this.age ,
            email = this.email ,
            info = this.info,
            lastMass = mass,
            readLastMass = read


        )
    }

    @Exclude
    fun toMap(): Map<String , Any?> {
        return mapOf(
            "uid" to uid ,
            "userName" to userName ,
            "picture" to picture ,
            "email" to email ,
            "info" to info ,

            )


    }

}