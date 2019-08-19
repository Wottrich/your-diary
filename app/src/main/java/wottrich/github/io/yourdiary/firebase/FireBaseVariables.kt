package wottrich.github.io.yourdiary.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

val gDatabase = FirebaseDatabase.getInstance()

val gReference = gDatabase.reference

val gAuth: FirebaseAuth = FirebaseAuth.getInstance()