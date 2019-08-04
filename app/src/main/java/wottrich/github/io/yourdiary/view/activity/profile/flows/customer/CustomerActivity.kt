package wottrich.github.io.yourdiary.view.activity.profile.flows.customer

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_customer.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.OrderAdapter
import wottrich.github.io.yourdiary.enumerators.CustomerType
import wottrich.github.io.yourdiary.enumerators.RegisterType
import wottrich.github.io.yourdiary.extensions.orderId
import wottrich.github.io.yourdiary.extensions.registerType
import wottrich.github.io.yourdiary.extensions.userId
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.utils.KeyboardUtils
import wottrich.github.io.yourdiary.view.activity.profile.register.RegisterActivity
import wottrich.github.io.yourdiary.view.dialog.ShowCustomersDialog
import wottrich.github.io.yourdiary.view.dialog.customer.CustomerDialog

class CustomerActivity : BaseActivity(R.layout.activity_customer), View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    val updateListCode = 100

    val viewModel: CustomerActivityViewModel by lazy {
        CustomerActivityViewModel()
    }

    private lateinit var _toolbar: Toolbar

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter(viewModel.orders, this, this::onClickOrder, this::onLongClickOrder)
    }

    override fun initValues() {
        _toolbar = toolbar
        rvOrders.adapter = orderAdapter
        configMenu()
        loadCustomer()
    }

    private fun configMenu() {
        _toolbar.inflateMenu(R.menu.customer_options)
        _toolbar.menu.getItem(1).isVisible = false
        _toolbar.setOnMenuItemClickListener(this)
        _toolbar.setOnClickListener(this)
        _toolbar.setNavigationOnClickListener { finish() }
    }

    private fun loadCustomer() {
        if (viewModel.clientCount > 0) {
            _toolbar.title = viewModel.client?.name
            _toolbar.subtitle = "Novo pedido..."
            showMenu(true)
            orderAdapter.updateList()
        } else {
            _toolbar.title = "Novo Cliente"
            _toolbar.subtitle = null
            orderAdapter.updateList()
            showMenu(false)
        }
        emptyList()
    }

    private fun showMenu(show: Boolean) {
        _toolbar.menu.getItem(0).isVisible = show
        //_toolbar.menu.getItem(1).isVisible = show
    }

    private fun emptyList() {
        if (viewModel.orders.isEmpty() && Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            lotEmptyList.playAnimation()
            lotEmptyList.visibility = View.VISIBLE
            tvEmptyList.visibility = View.VISIBLE
        } else {
            lotEmptyList.cancelAnimation()
            lotEmptyList.visibility = View.GONE
            tvEmptyList.visibility = View.GONE
        }
    }

    private fun onClickOrder(order: Order?, position: Int) {
        if (!viewModel.onLongClickableMode) {
            if (order != null) {
                val intent = Intent(this, RegisterActivity::class.java).apply {
                    this registerType RegisterType.EDIT
                    this orderId order.id
                }

                startActivityForResult(intent, updateListCode)
            } else {
                Toast.makeText(this, "Error to get order id", Toast.LENGTH_SHORT).show()
            }
        } else if (order != null) {
            controlSelectedList(order, position)
        } else {
            Toast.makeText(this, "Error to get order id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLongClickOrder(order: Order?, position: Int) {
        order ?: return
        controlSelectedList(order, position)
    }

    private fun controlSelectedList(order: Order, position: Int) {
        if (viewModel.ordersSelected.isEmpty()) {
            order.isSelected = true
            viewModel.ordersSelected.add(order)
            selectedItem()
        } else {
            val hasInList = viewModel.ordersSelected.find { it.id == order.id } != null
            if (hasInList) {
                order.isSelected = false
                viewModel.ordersSelected.remove(order)
                if (viewModel.ordersSelected.isEmpty()) {
                    selectedItem()
                }
            } else {
                order.isSelected = true
                viewModel.ordersSelected.add(order)
            }
        }
        orderAdapter.notifyItemChanged(position)
    }

    private fun selectedItem() {
        val visible = viewModel.onLongClickableMode
        _toolbar.menu.getItem(0).isVisible = !visible
        _toolbar.menu.getItem(1).isVisible = visible
        //_toolbar.menu.getItem(2).isVisible = !_toolbar.menu.getItem(2).isVisible
    }

    private fun cleanSelectedItems () {
        if (viewModel.onLongClickableMode) {
            viewModel.ordersSelected.clear()
            selectedItem()
            this.orderAdapter.updateList()
        }
        emptyList()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.toolbar -> {
                if (viewModel.clientCount > 0) {
                    cleanSelectedItems()
                    val intent = Intent(this, RegisterActivity::class.java).apply {
                        this registerType RegisterType.NEW
                        this userId viewModel.client?.id
                    }
                    startActivityForResult(intent, updateListCode)
                } else {
                    KeyboardUtils.showKeyboard(this, root)
                    root.postDelayed({
                        CustomerDialog(
                            this::loadCustomer,
                            CustomerType.NEW
                        ).show(supportFragmentManager, "CustomerDialog")
                    }, 100)
                }
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.itChangeCustomer -> {
                ShowCustomersDialog(this::loadCustomer).show(supportFragmentManager, "ShowCustomerDialog")
                true
            }
            R.id.itDelete -> {
                viewModel.deleteSelectedOrders {
                    viewModel.ordersSelected.clear()
                    this.orderAdapter.updateList()
                    selectedItem()
                    emptyList()
                }
                true
            }
            R.id.itSettings -> {
                KeyboardUtils.showKeyboard(this, root)
                root.postDelayed({
                    CustomerDialog(
                        this::loadCustomer,
                        CustomerType.EDIT,
                        viewModel.client?.id ?: -1
                    ).show(supportFragmentManager, "CustomerDialog")
                },100)
                true
            }
            R.id.itNewCustomer -> {
                cleanSelectedItems()
                KeyboardUtils.showKeyboard(this, root)
                root.postDelayed({
                    CustomerDialog(
                        this::loadCustomer,
                        CustomerType.NEW
                    ).show(supportFragmentManager, "CustomerDialog")
                }, 100)
                true
            }
            else -> false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {

            resultCode == Activity.RESULT_OK && requestCode == updateListCode -> loadCustomer()

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onStart() {
        if (viewModel.orders.isEmpty()) {
            lotEmptyList.playAnimation()
        }
        super.onStart()
    }

    override fun onStop() {
        lotEmptyList.cancelAnimation()
        super.onStop()
    }

    override fun onBackPressed() {
        if (viewModel.ordersSelected.isNotEmpty()) {
            cleanSelectedItems()
        } else super.onBackPressed()
    }

}
