package wottrich.github.io.yourdiary.view.fragments

import android.annotation.SuppressLint
import kotlinx.android.synthetic.main.fragment_spending.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.SpendingAdapter
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.view.dialog.SpendingDialog
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Spending

@SuppressLint("StaticFieldLeak")
open class SpendingFragment : BaseFragment(R.layout.fragment_spending) {

    private var spendingAdapter: SpendingAdapter? = null

    companion object : SpendingFragment() {
        @JvmStatic
        fun newInstance() = SpendingFragment()
    }

    override fun initValues() {
        if (spendingAdapter == null) {
            spendingAdapter = SpendingAdapter(boxList<Spending>().asReversed(), activity ?: return)
        }

        baseView.rvSpending.adapter = spendingAdapter
        baseView.rvSpending.setHasFixedSize(true)
        baseView.ivAdd.setOnClickListener {
            SpendingDialog {
                spendingAdapter?.notifyDataSetChanged()
            }.show(activity?.supportFragmentManager, "SpendingDialog")
        }
    }

    open fun reload() {
        //SpendingDialog(this::onSpending).show(activity?.supportFragmentManager, "SpendingDialog")
    }

    private fun onSpending() {
        spendingAdapter?.notifyDataSetChanged()
    }
}
