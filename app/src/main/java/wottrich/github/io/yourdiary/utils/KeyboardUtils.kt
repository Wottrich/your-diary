package wottrich.github.io.yourdiary.utils

import android.app.Activity
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

object KeyboardUtils {

    fun hideKeyboard (activity: Activity) {
        try {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = activity.currentFocus

            if (view != null) {
                imm.hideSoftInputFromWindow(view.windowToken, 0)
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