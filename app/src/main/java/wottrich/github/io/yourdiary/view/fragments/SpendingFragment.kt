package wottrich.github.io.yourdiary.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_spending.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.SpendingAdapter
import wottrich.github.io.yourdiary.enumerators.RegisterType
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.view.activity.MainActivity
import wottrich.github.io.yourdiary.view.activity.register.RegisterActivity

@SuppressLint("StaticFieldLeak")
open class SpendingFragment : BaseFragment(R.layout.fragment_spending), View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    private val boxSpendingList: List<Spending> get() = boxList()

    private val spendingAdapter: SpendingAdapter by lazy {
        SpendingAdapter(
            boxSpendingList.asReversed(),
            requireActivity(),
            onClickSpending = this::onClickSpending,
            onLongClickSpending = this::onLongClickSpending
        )
    }

    companion object : SpendingFragment() {
        @JvmStatic
        fun newInstance() = SpendingFragment()
    }

    override fun initValues() {
        baseView.rvSpending.adapter = spendingAdapter
        baseView.rvSpending.setHasFixedSize(true)
        baseView.toolbar.inflateMenu(R.menu.add_option)
        baseView.toolbar.setOnMenuItemClickListener(this)
    }

    open fun reload() {
        spendingAdapter.updateList()
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        return when (menuItem?.itemId) {
            R.id.itAdd -> {
                val intent = Intent(activity, RegisterActivity::class.java).apply {
                    this registerType  RegisterType.NEW
                    this isSpending true
                }
                activity?.startActivityForResult(intent, MainActivity.UPDATE_SPENDING_LIST)
                true
            }
            else -> false
        }
    }

    private fun onClickSpending (spending: Spending?, position: Int?) {
        if (spending != null) {
            val intent = Intent(activity, RegisterActivity::class.java).apply {
                this spendingId spending.id
                this registerType RegisterType.EDIT
                this isSpending true
            }
            activity?.startActivityForResult(intent, MainActivity.UPDATE_SPENDING_LIST)
        } else {
            Toast.makeText(activity, "Error to get spending id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLongClickSpending (spending: Spending?, position: Int?) {

    }

    override fun onClick(v: View?) {}
}
