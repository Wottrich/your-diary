package wottrich.github.io.yourdiary.view.fragments.spending

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
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.view.activity.main.MainActivity
import wottrich.github.io.yourdiary.view.activity.register.RegisterActivity

@SuppressLint("StaticFieldLeak", "ValidFragment")
open class SpendingFragment() : BaseFragment(R.layout.fragment_spending), View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    lateinit var user: User

    val viewModel: SpendingFragmentViewModel by lazy {
        SpendingFragmentViewModel()
    }

    constructor(user: User) : this () {
        this.user =  user
    }

    private val spendingAdapter: SpendingAdapter by lazy {
        SpendingAdapter(
            viewModel.boxSpendingList.asReversed(),
            requireActivity(),
            onClickSpending = this::onClickSpending,
            onLongClickSpending = this::onLongClickSpending
        )
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) = SpendingFragment(user)
    }

    override fun initValues() {
        baseView.rvSpending.adapter = spendingAdapter
        baseView.rvSpending.setHasFixedSize(true)
        baseView.toolbar.inflateMenu(R.menu.add_option)
        baseView.toolbar.menu.getItem(1).isVisible = false
        baseView.toolbar.setOnMenuItemClickListener(this)
    }

    open fun reload() {
        spendingAdapter.updateList()
    }

    private fun onClickSpending (spending: Spending?, position: Int) {
        if (!viewModel.onLongClickEnable) {
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
        } else if (spending != null) {
            controlSelectedList(spending, position)
        } else {
            Toast.makeText(activity, "Error to get order id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLongClickSpending (spending: Spending?, position: Int) {
        spending ?: return
        controlSelectedList(spending, position)
    }

    private fun controlSelectedList (spending: Spending, position: Int) {
        if (viewModel.selectedSpending.isEmpty()) {
            spending.isSelected = true
            viewModel.selectedSpending.add(spending)
            menuSelectedItem()
        } else {
            val hasInList = viewModel.selectedSpending.find { it.id == spending.id } != null
            if (hasInList) {
                spending.isSelected = false
                viewModel.selectedSpending.remove(spending)
                menuSelectedItem()
            } else {
                spending.isSelected = true
                viewModel.selectedSpending.add(spending)
            }
        }

        spendingAdapter.notifyItemChanged(position)
    }

    private fun menuSelectedItem () {
        if (viewModel.selectedSpending.isNotEmpty()) {
            baseView.toolbar.menu.getItem(0).isVisible = false
            baseView.toolbar.menu.getItem(1).isVisible = true
        } else {
            baseView.toolbar.menu.getItem(0).isVisible = true
            baseView.toolbar.menu.getItem(1).isVisible = false
        }
    }

    fun cleanSelectedItems () {
        if (viewModel.onLongClickEnable) {
            viewModel.selectedSpending.clear()
            spendingAdapter.updateList()
        }
        menuSelectedItem()
    }

    override fun onClick(v: View?) {}

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
            R.id.itDelete -> {
                viewModel.deleteSelectedItems {
                    viewModel.selectedSpending.clear()
                    spendingAdapter.updateList()
                    menuSelectedItem()
                }
                true
            }
            else -> false
        }
    }
}
