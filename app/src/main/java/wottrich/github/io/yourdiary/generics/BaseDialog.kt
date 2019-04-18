package wottrich.github.io.yourdiary.generics

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import wottrich.github.io.yourdiary.extensions.AnimationMin
import wottrich.github.io.yourdiary.extensions.toTranslateBottomToTop
import wottrich.github.io.yourdiary.extensions.toTranslateTopToBottom

abstract class BaseDialog(private val layoutView: Int) : DialogFragment() {

    protected lateinit var baseView: View
    protected var parent: ViewGroup? = null

    private fun initAnimation () {
        parent?.visibility = View.VISIBLE
        val animation = toTranslateBottomToTop()
        animation.duration = 200
        animation.repeatCount = 0
        animation.interpolator = AccelerateDecelerateInterpolator()
        parent?.startAnimation(animation)
    }

    private fun finishAnimation () {
        val animation = toTranslateTopToBottom()
        animation.setAnimationListener(object : AnimationMin() {
            override fun onAnimationEnd(animation: Animation?) {
                dismiss()
            }
        })
        animation.duration = 200
        animation.repeatCount = 0
        animation.interpolator = AccelerateDecelerateInterpolator()
        parent?.startAnimation(animation)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).also { dialog ->
            dialog.window?.attributes.also { params ->
                params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                params?.dimAmount = 0f
            }
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onStart() {
        super.onStart()
        dialog.also {
            it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.window?.attributes.also { params ->
                params?.width = ViewGroup.LayoutParams.MATCH_PARENT
                params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                params?.dimAmount = 0f
            }
            it.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        }
        if (parent != null)
            initAnimation()
    }

    fun dismissAnimation () {
        finishAnimation()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        baseView = inflater.inflate(layoutView, container, false)
        this.initValues()
        return baseView
    }

    protected abstract fun initValues ()

}
