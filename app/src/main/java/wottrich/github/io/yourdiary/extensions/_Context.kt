package wottrich.github.io.yourdiary.extensions

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent

fun Context.intentLockActivity (message: String = "") : Intent {
    val key = getSystemService(Activity.KEYGUARD_SERVICE) as KeyguardManager
    return key.createConfirmDeviceCredentialIntent("Security Lock", message)
}

infix fun Context.startMyActivity (clazz : Class<*>) {
    this.startActivity(Intent(this, clazz))
}