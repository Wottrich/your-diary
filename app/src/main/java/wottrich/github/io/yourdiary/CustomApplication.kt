package wottrich.github.io.yourdiary

import android.app.Application
import wottrich.github.io.yourdiary.box.ObjectBox

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)
    }

}