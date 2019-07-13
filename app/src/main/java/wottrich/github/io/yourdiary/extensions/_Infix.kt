package wottrich.github.io.yourdiary.extensions

import android.content.Intent
import android.widget.EditText
import wottrich.github.io.yourdiary.enumerators.RegisterType
import wottrich.github.io.yourdiary.model.Spending

infix fun Intent.registerType (registerType: RegisterType) {
    this.putExtra("registerType", registerType)
}

infix fun Intent.orderId (orderId: Long) {
    this.putExtra("orderId", orderId)
}

infix fun Intent.userId (userId: Long?) {
    this.putExtra("userId", userId)
}

infix fun Intent.spendingId (spendingId: Long?) {
    this.putExtra("spendingId", spendingId)
}

infix fun Intent.isSpending (isSpending: Boolean) {
    this.putExtra("isSpending", isSpending)
}
