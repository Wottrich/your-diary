package wottrich.github.io.yourdiary.extensions

import android.content.Intent
import android.widget.EditText
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.model.OrderType

infix fun EditText.setText (text: String?) {
    this.setText(text)
}

infix fun Intent.orderType (orderType: OrderType) {
    this.putExtra("orderType", orderType)
}

infix fun Intent.orderId (orderId: Long) {
    this.putExtra("orderId", orderId)
}

infix fun Intent.userId (userId: Long?) {
    this.putExtra("userId", userId)
}