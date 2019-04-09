package wottrich.github.io.yourdiary.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import wottrich.github.io.yourdiary.MainActivity

import wottrich.github.io.yourdiary.R

open class SpendingFragment : Fragment() {

    companion object : SpendingFragment() {
        @JvmStatic
        fun newInstance() = SpendingFragment()
    }

    open fun reload() {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_spending, container, false)
    }
}
