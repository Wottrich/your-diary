package wottrich.github.io.yourdiary.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun hideKeyboard (activity: Activity, viewGroup: View) {
        try {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

            if (viewGroup.applicationWindowToken != null) {
                imm.hideSoftInputFromWindow(viewGroup.applicationWindowToken, 0)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showKeyboard (activity: Activity, viewGroup: ViewGroup) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.toggleSoftInputFromWindow(
            viewGroup.applicationWindowToken,
            InputMethodManager.SHOW_FORCED,
            0
        )
    }

}