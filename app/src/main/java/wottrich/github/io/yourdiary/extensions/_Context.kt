package wottrich.github.io.yourdiary.extensions

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent

fun Context.intentLockActivity (message: String = "") : Intent {
    val key = getSystemService(Activity.KEYGUARD_SERVICE) as KeyguardManager
    return key.createConfirmDeviceCredentialIntent("Security Lock", message)
}

fun Context.startMyActivity (clazz : Class<*>) {
    this.startActivity(Intent(this, clazz))
}

fun Context.startMyActivity (clazz : Class<*>, prepareIntent: (Intent) -> Intent) {
    val intent = prepareIntent.invoke(Intent())
    intent.setClass(this, clazz)
    this.startActivity(Intent(this, clazz))
}