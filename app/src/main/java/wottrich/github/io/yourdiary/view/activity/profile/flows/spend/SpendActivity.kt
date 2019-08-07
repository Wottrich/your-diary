package wottrich.github.io.yourdiary.view.activity.profile.flows.spend

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_spend.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.SpendingAdapter
import wottrich.github.io.yourdiary.enumerators.RegisterType
import wottrich.github.io.yourdiary.extensions.isSpending
import wottrich.github.io.yourdiary.extensions.registerType
import wottrich.github.io.yourdiary.extensions.spendingId
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.view.activity.profile.register.RegisterActivity

class SpendActivity : BaseActivity(R.layout.activity_spend), Toolbar.OnMenuItemClickListener {

    private val updateListCode = 200

    val viewModel: SpendActivityViewModel by lazy {
        SpendActivityViewModel()
    }

    private val spendingAdapter: SpendingAdapter by lazy {
        SpendingAdapter(
            viewModel.boxSpendingList.asReversed(),
            this,
            onClickSpending = this::onClickSpending,
            onLongClickSpending = this::onLongClickSpending
        )
    }

    override fun initValues() {
        rvSpending.adapter = spendingAdapter
        rvSpending.setHasFixedSize(true)
        toolbar.inflateMenu(R.menu.add_option)
        toolbar.menu.getItem(1).isVisible = false
        toolbar.setOnMenuItemClickListener(this)
        toolbar.setNavigationOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
            //overridePendingTransition()
        }
        emptyList()
    }

    private fun emptyList () {
        if (viewModel.boxSpendingList.isEmpty() && Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            lotEmptyList.playAnimation()
            lotEmptyList.visibility = View.VISIBLE
            tvEmptyList.visibility = View.VISIBLE
        } else {
            lotEmptyList.cancelAnimation()
            lotEmptyList.visibility = View.GONE
            tvEmptyList.visibility = View.GONE
        }
    }

    private fun onLongClickSpending (spending: Spending?, position: Int) {
        spending ?: return
        controlSelectedList(spending, position)
    }

    private fun onClickSpending (spending: Spending?, position: Int) {
        if (!viewModel.onLongClickEnable) {
            if (spending != null) {
                val intent = Intent(this, RegisterActivity::class.java).apply {
                    this spendingId spending.id
                    this registerType RegisterType.EDIT
                    this isSpending true
                }
                startActivityForResult(intent, updateListCode)
            } else {
                Toast.makeText(this, "Error to get spending id", Toast.LENGTH_SHORT).show()
            }
        } else if (spending != null) {
            controlSelectedList(spending, position)
        } else {
            Toast.makeText(this, "Error to get order id", Toast.LENGTH_SHORT).show()
        }
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
            toolbar.menu.getItem(0).isVisible = false
            toolbar.menu.getItem(1).isVisible = true
        } else {
            toolbar.menu.getItem(0).isVisible = true
            toolbar.menu.getItem(1).isVisible = false
        }
    }

    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        return when (menuItem?.itemId) {
            R.id.itAdd -> {
                val intent = Intent(this, RegisterActivity::class.java).apply {
                    this registerType  RegisterType.NEW
                    this isSpending true
                }
                startActivityForResult(intent, updateListCode)
                true
            }
            R.id.itDelete -> {
                viewModel.deleteSelectedItems {
                    viewModel.selectedSpending.clear()
                    spendingAdapter.updateList()
                    menuSelectedItem()
                    emptyList()
                }
                true
            }
            else -> false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when {
            resultCode == Activity.RESULT_OK
                    && requestCode == updateListCode -> {
                spendingAdapter.updateList()
                emptyList()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        if (viewModel.boxSpendingList.isEmpty()) {
            lotEmptyList.playAnimation()
        }
        super.onStart()
    }

    override fun onStop() {
        lotEmptyList.cancelAnimation()
        super.onStop()
    }

    override fun onBackPressed() {
        if (viewModel.selectedSpending.isNotEmpty()) {
            if (viewModel.onLongClickEnable) {
                viewModel.selectedSpending.clear()
                spendingAdapter.updateList()
            }
            menuSelectedItem()
            emptyList()
        } else {
            setResult(Activity.RESULT_OK)
            super.onBackPressed()
        }
    }

}
