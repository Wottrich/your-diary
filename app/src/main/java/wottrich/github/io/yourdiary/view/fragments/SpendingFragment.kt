package wottrich.github.io.yourdiary.view.fragments

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.fragment_spending.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.SpendingAdapter
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.view.dialog.spending.SpendingDialog
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Spending

@SuppressLint("StaticFieldLeak")
open class SpendingFragment : BaseFragment(R.layout.fragment_spending), View.OnClickListener {

    private val boxSpendingList: List<Spending> by lazy {
        boxList<Spending>()
    }

    private val spendingAdapter: SpendingAdapter by lazy {
        SpendingAdapter(boxSpendingList.asReversed(), requireActivity())
    }

    companion object : SpendingFragment() {
        @JvmStatic
        fun newInstance() = SpendingFragment()
    }

    override fun initValues() {
        baseView.rvSpending.adapter = spendingAdapter
        baseView.rvSpending.setHasFixedSize(true)
        baseView.ivAdd.setOnClickListener(this)
        verifyEmptyList()
    }

    open fun reload() {
        //spendingAdapter?.updateList()
        //SpendingDialog(this::onSpending).show(activity?.supportFragmentManager, "SpendingDialog")
        verifyEmptyList()
    }

    fun verifyEmptyList() {
        baseView.tvEmptyList.visibility = if (boxSpendingList.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivAdd -> {
                SpendingDialog {
                    spendingAdapter.updateList()
                }.show(activity?.supportFragmentManager, "SpendingDialog")
            }
        }

    }
}
