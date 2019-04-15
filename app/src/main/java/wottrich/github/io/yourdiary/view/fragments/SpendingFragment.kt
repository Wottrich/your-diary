package wottrich.github.io.yourdiary.view.fragments

import android.annotation.SuppressLint
import android.widget.Toast
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import kotlinx.android.synthetic.main.fragment_spending.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.SpendingAdapter
import wottrich.github.io.yourdiary.box.ObjectBox
import wottrich.github.io.yourdiary.dialog.SpendingDialog
import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Spending
import java.util.*

@SuppressLint("StaticFieldLeak")
open class SpendingFragment : BaseFragment(R.layout.fragment_spending) {

    private var spendingAdapter: SpendingAdapter? = null

    companion object : SpendingFragment() {
        @JvmStatic
        fun newInstance() = SpendingFragment()
    }

    override fun initValues() {
        if (spendingAdapter == null) {
            spendingAdapter = SpendingAdapter(boxList(),activity ?: return)
        }

        baseView.rvSpending.adapter = spendingAdapter
        baseView.rvSpending.setHasFixedSize(true)
        baseView.ivAdd.setOnClickListener { SpendingDialog(this::onSpending).show(activity?.supportFragmentManager, "SpendingDialog") }
    }

    open fun reload() {
        //SpendingDialog(this::onSpending).show(activity?.supportFragmentManager, "SpendingDialog")
    }

    private fun onSpending () {
        spendingAdapter?.updateList()
    }
}
