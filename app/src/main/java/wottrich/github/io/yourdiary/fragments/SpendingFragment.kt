package wottrich.github.io.yourdiary.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import wottrich.github.io.yourdiary.MainActivity

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.dialog.SpendingDialog
import wottrich.github.io.yourdiary.model.Spending

open class SpendingFragment : Fragment() {

    companion object : SpendingFragment() {
        @JvmStatic
        fun newInstance() = SpendingFragment()
    }

    open fun reload() {
        SpendingDialog(this::onSpending).show(activity?.supportFragmentManager, "SpendingDialog")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
        reload()
        return inflater.inflate(R.layout.fragment_spending, container, false)
    }

    private fun onSpending (spending: Spending) {
        Toast.makeText(activity ?: return, spending.description, Toast.LENGTH_SHORT).show()
    }
}
