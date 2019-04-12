package wottrich.github.io.yourdiary.generics

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

@SuppressLint("ValidFragment")
abstract class BaseFragment constructor(private var layout: Int) : Fragment() {

    protected lateinit var baseView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        baseView = inflater.inflate(layout, container, false)
        this.initValues()
        return baseView
    }

    protected abstract fun initValues()

}