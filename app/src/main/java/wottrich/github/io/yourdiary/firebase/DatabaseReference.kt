package wottrich.github.io.yourdiary.firebase

import com.google.firebase.database.DatabaseReference

object Child {

    fun users () : DatabaseReference {
        return gReference.child("users")
    }

    fun user (userId: String) : DatabaseReference {
        return users().child(userId)
    }



}

object Reference {

}