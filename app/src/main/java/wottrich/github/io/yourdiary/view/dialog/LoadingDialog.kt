package wottrich.github.io.yourdiary.view.dialog

import android.view.animation.AnticipateInterpolator
import android.view.animation.OvershootInterpolator
import kotlinx.android.synthetic.main.dialog_loading.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.generics.BaseDialog

class LoadingDialog : BaseDialog(R.layout.dialog_loading, animation = false) {

    override fun initValues() {}

    override fun onStart() {
        super.onStart()
        baseView.progressBar.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(250)
            .setInterpolator(OvershootInterpolator(0.8f))
            .start()
    }

    override fun onDestroyView() {
        if (dialog != null) {
            dialog.setDismissMessage(null)
        }
        super.onDestroyView()
    }

    override fun dismiss() {
        baseView.progressBar.post {
            baseView.progressBar.animate()
                .scaleX(0f)
                .scaleY(0f)
                .setDuration(150)
                .setInterpolator(AnticipateInterpolator(0.4f))
                .withEndAction(this::dismissPattern)
                .start()
        }
    }

    private fun dismissPattern() {

        try {
            super.dismissAllowingStateLoss()
        } catch (exception: Exception) {
            //add Crashlytics
        }

    }

}