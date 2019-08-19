package wottrich.github.io.yourdiary.extensions

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

fun Context.intentLockActivity (message: String = "") : Intent {
    val key = getSystemService(Activity.KEYGUARD_SERVICE) as KeyguardManager
    return key.createConfirmDeviceCredentialIntent("Security Lock", message)
}

fun Context.startMyActivity (clazz : KClass<*>) {
    this.startActivity(Intent(this, clazz.java))
}

fun Activity.startMyActivity (clazz : KClass<*>, result: Int) {
    this.startActivityForResult(Intent(this, clazz.java), result)
}

fun Context.startMyActivity (clazz : KClass<*>, prepareIntent: (Intent) -> Intent) {
    val intent = prepareIntent.invoke(Intent())
    intent.setClass(this, clazz.java)
    this.startActivity(intent)
}

