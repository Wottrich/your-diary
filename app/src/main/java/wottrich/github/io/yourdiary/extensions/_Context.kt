package wottrich.github.io.yourdiary.extensions

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.ColorRes
import kotlin.reflect.KClass

fun Context.intentLockActivity (message: String = "") : Intent {
    val key = getSystemService(Activity.KEYGUARD_SERVICE) as KeyguardManager
    return key.createConfirmDeviceCredentialIntent("Security Lock", message)
}

fun Context.startMyActivity (clazz : KClass<*>) {
    this.startActivity(Intent(this, clazz.java))
}

fun Activity.startMyActivity (clazz : KClass<*>, result: Int, prepareIntent: ((Intent) -> Intent)? = null) {
    val intent = prepareIntent?.invoke(Intent()) ?: Intent()
    intent.setClass(this, clazz.java)
    this.startActivityForResult(intent, result)
}

fun Context.startMyActivity (clazz : KClass<*>, prepareIntent: (Intent) -> Intent) {
    val intent = prepareIntent.invoke(Intent())
    intent.setClass(this, clazz.java)
    this.startActivity(intent)
}

fun Context.getResourseColor (@ColorRes color: Int) : Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.resources.getColor(color, this.theme)
    } else {
        this.resources.getColor(color)
    }
}

