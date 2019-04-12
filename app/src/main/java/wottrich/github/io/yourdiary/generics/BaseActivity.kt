package wottrich.github.io.yourdiary.generics

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity(
        private var layout: Int
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        this.initValues()
    }

    protected abstract fun initValues()

}