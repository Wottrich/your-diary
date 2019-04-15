package wottrich.github.io.yourdiary.box

import android.content.Context
import io.objectbox.BoxStore
import wottrich.github.io.yourdiary.model.MyObjectBox

class ObjectBox {

    companion object {
        lateinit var boxStore: BoxStore

        fun init(ctx: Context) {
            boxStore = MyObjectBox.builder().androidContext(ctx.applicationContext).build()
        }
    }

}