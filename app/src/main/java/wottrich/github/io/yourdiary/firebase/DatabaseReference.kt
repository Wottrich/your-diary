//package wottrich.github.io.yourdiary.firebase
//
//import android.app.Activity
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.ValueEventListener
//import wottrich.github.io.yourdiary.model.User
//
//object Child {
//
//    /**
//     * Return user list
//     */
//    fun users () : DatabaseReference {
//        return gReference.child("users")
//    }
//
//    /**
//     * Return specific user
//     * @param userId is the uuid to get specific user
//     */
//    fun user (userId: String) : DatabaseReference {
//        return users().child(userId)
//    }
//
//    /**
//     * Return customers list
//     * @param userId is the uuid to get specific user
//     */
//    fun customers (userId: String) : DatabaseReference {
//        return user(userId).child("customers")
//    }
//
//    /**
//     * Return specific customer
//     * @param userId is the uuid to get specific user
//     * @param customerId is the ID to get specific customer
//     */
//    fun customer (userId: String, customerId: String) : DatabaseReference {
//        return customers(userId).child(customerId)
//    }
//
//    /**
//     * Return orders list
//     * @param userId is the uuid to get specific user
//     * @param customerId is the ID to get specific customer
//     */
//    fun orders (userId: String, customerId: String) : DatabaseReference {
//        return customer(userId, customerId).child("orders")
//    }
//
//    /**
//     * Return specific order
//     * @param userId is the uuid to get specific user
//     * @param customerId is the ID to get specific customer
//     * @param orderId is the ID to get specific order
//     */
//    fun order (userId: String, customerId: String, orderId: String) : DatabaseReference {
//        return orders(userId, customerId).child(orderId)
//    }
//
//    /**
//     * Return spending list
//     * @param userId is the uuid to get specific user
//     */
//    fun spending (userId: String) : DatabaseReference {
//        return user(userId).child("spending")
//    }
//
//    /**
//     * Return specific spend
//     * @param userId is the uuid to get specific user
//     * @param spendId is the ID to get specific spend
//     */
//    fun spend (userId: String, spendId: String) : DatabaseReference {
//        return spending(userId).child(spendId)
//    }
//}
//
//fun ValueEventListener.listenerUserChange (userId: String) {
//    Child.user(userId).addValueEventListener(this)
//}
//
//fun getUserAuth(userId: String, result:(User?, String) -> Unit) {
//    val ref = Child.user(userId)
//
//    ref.addValueEventListener(object : ValueEventListener {
//        override fun onCancelled(error: DatabaseError) {
//            ref.removeEventListener(this)
//            result(null, error.message)
//        }
//
//        override fun onDataChange(snap: DataSnapshot) {
//            ref.removeEventListener(this)
//            //TODO mapear o user e enviar de volta
//        }
//
//    })
//}
//
//fun ValueEventListener.listenerCustomersChange (userId: String) {
//    Child.customers(userId).addValueEventListener(this)
//}
//
//fun ValueEventListener.listernerOrdersChange (userId: String, customerId: String) {
//    Child.orders(userId, customerId).addValueEventListener(this)
//}