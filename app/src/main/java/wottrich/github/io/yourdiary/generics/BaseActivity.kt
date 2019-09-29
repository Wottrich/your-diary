package wottrich.github.io.yourdiary.generics

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.view.dialog.LoadingDialog

abstract class BaseActivity(
        private var layout: Int
) : AppCompatActivity() {

    private var loader: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)

        this.onRecoverIntent(intent)
        this.initValues()

    }

    protected abstract fun initValues()
    open fun onRecoverIntent (intent: Intent) {}

    fun showLoader () {
        if (loader != null) {
            loader = LoadingDialog()
            loader!!.show(this.supportFragmentManager, "LoadingDialog")
        }
    }

    fun hideLoader () {
        if (loader != null && loader!!.dialog != null) {
            loader!!.dismiss()
            loader = null
        }
    }

    fun showAlertDialog(icon: Drawable? = null, title: String, message: String, cancelable: Boolean = true, onClick: (() -> Unit)? = null){
        AlertDialog.Builder(this)
            .setCancelable(cancelable)
            .setIcon(icon)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { _, _ ->
                onClick?.invoke()
            }.show()
    }

    fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setCancelable(true)
            .setIcon(R.drawable.ic_error)
            .setTitle("Ops!")
            .setMessage(message)
            .setPositiveButton("Ok", null)
            .show()
    }

}