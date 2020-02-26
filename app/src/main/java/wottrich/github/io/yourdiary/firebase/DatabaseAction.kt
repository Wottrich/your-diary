//package wottrich.github.io.yourdiary.firebase
//
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.ValueEventListener
//import wottrich.github.io.yourdiary.model.User
//
///**
// * @author Wottrich
// * @author lucas.wottrich@operacao.rcadigital.com.br
// * @since 2019-09-01
// *
// * Copyright © 2019 your-diary. All rights reserved.
// *
// */
//
//class UserAction {
//
//    fun toSave (user: User, success: (Boolean) -> Unit) {
//
//        val userRef = Child.users().push().child(user.uid)
//        userRef.setValue(user)
//
//        userRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snap: DataSnapshot) {
//                userRef.removeEventListener(this)
//                success(true)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                userRef.removeEventListener(this)
//                success(false)
//            }
//        })
//
//    }
//
//    fun customersSync (userId: String) {
//
//
//
//    }
//
//}