package wottrich.github.io.yourdiary.extensions

import android.widget.EditText

infix fun EditText.setText (text: String?) {
    this.setText(text)
}

fun EditText.getString() : String {
    return this.text.toString()
}