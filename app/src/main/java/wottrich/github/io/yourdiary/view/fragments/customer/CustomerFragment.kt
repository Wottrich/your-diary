package wottrich.github.io.yourdiary.view.fragments.customer

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_clients.*
import kotlinx.android.synthetic.main.fragment_clients.view.*
import kotlinx.android.synthetic.main.fragment_clients.view.lotEmptyList

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.OrderAdapter
import wottrich.github.io.yourdiary.enumerators.CustomerType
import wottrich.github.io.yourdiary.enumerators.RegisterType
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.utils.KeyboardUtils
import wottrich.github.io.yourdiary.view.activity.main.MainActivity
import wottrich.github.io.yourdiary.view.activity.register.RegisterActivity
import wottrich.github.io.yourdiary.view.dialog.customer.CustomerDialog
import wottrich.github.io.yourdiary.view.dialog.ShowCustomersDialog

@SuppressLint("StaticFieldLeak", "ValidFragment")
open class CustomerFragment() : BaseFragment(R.layout.fragment_clients), View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    lateinit var user: User

    val viewModel: CustomerFragmentViewModel by lazy {
        CustomerFragmentViewModel()
    }

    constructor(user: User) : this() {
        this.user = user
    }

    private lateinit var _toolbar: Toolbar

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter(viewModel.orders, requireActivity(), this::onClickOrder, this::onLongClickOrder)
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) = CustomerFragment(user)
    }

    override fun initValues() {
        _toolbar = baseView.toolbar
        baseView.rvOrders.adapter = orderAdapter
        configMenu()
        loadCustomer()
    }

    private fun emptyList () {
        if (viewModel.orders.isEmpty()) {
            baseView.lotEmptyList.playAnimation()
            baseView.lotEmptyList.visibility = View.VISIBLE
            baseView.tvEmptyList.visibility = View.VISIBLE
        } else {
            baseView.lotEmptyList.cancelAnimation()
            baseView.lotEmptyList.visibility = View.GONE
            baseView.tvEmptyList.visibility = View.GONE
        }
    }

    fun playAnimation (play: Boolean) {
        if (play && baseView.lotEmptyList.visibility == View.VISIBLE) baseView.lotEmptyList.playAnimation() else baseView.lotEmptyList.cancelAnimation()
    }

    fun loadCustomer() {
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

    private fun showMenu (show: Boolean) {
        _toolbar.menu.getItem(0).isVisible = show
        //_toolbar.menu.getItem(1).isVisible = show
    }

    private fun configMenu () {
        _toolbar.inflateMenu(R.menu.customer_options)
        _toolbar.menu.getItem(1).isVisible = false
        _toolbar.setOnMenuItemClickListener(this)
        _toolbar.setOnClickListener(this)
    }

    private fun onClickOrder (order: Order?, position: Int) {
        if (!viewModel.onLongClickableMode) {
            if (order != null) {
                val intent = Intent(activity, RegisterActivity::class.java).apply {
                    this registerType  RegisterType.EDIT
                    this orderId order.id
                }
                activity?.startActivityForResult(intent, MainActivity.UPDATE_ORDER_LIST)
            } else {
                Toast.makeText(activity, "Error to get order id", Toast.LENGTH_SHORT).show()
            }
        } else if (order != null) {
            controlSelectedList(order, position)
        } else {
            Toast.makeText(activity, "Error to get order id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLongClickOrder (order: Order?, position: Int) {
        order ?: return
        controlSelectedList(order, position)
    }

    private fun controlSelectedList (order: Order, position: Int) {
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

    fun cleanSelectedItems () {
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
                    val intent = Intent(activity, RegisterActivity::class.java).apply {
                        this registerType  RegisterType.NEW
                        this userId viewModel.client?.id
                    }
                    activity?.startActivityForResult(intent, MainActivity.UPDATE_ORDER_LIST)
                } else {
                    KeyboardUtils.showKeyboard(requireActivity(), baseView)
                    baseView.postDelayed({
                        CustomerDialog(
                            this::loadCustomer,
                            CustomerType.NEW
                        ).show(activity?.supportFragmentManager, "CustomerDialog")
                    }, 100)
                }
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.itChangeCustomer -> {
                ShowCustomersDialog(this::loadCustomer).show(activity?.supportFragmentManager, "ShowCustomerDialog")
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
                KeyboardUtils.showKeyboard(requireActivity(), baseView)
                baseView.postDelayed({
                    CustomerDialog(
                        this::loadCustomer,
                        CustomerType.EDIT,
                        viewModel.client?.id ?: -1
                    ).show(activity?.supportFragmentManager, "CustomerDialog")
                },100)
                true
            }
            R.id.itNewCustomer -> {
                cleanSelectedItems()
                KeyboardUtils.showKeyboard(requireActivity(), baseView)
                baseView.postDelayed({
                    CustomerDialog(
                        this::loadCustomer,
                        CustomerType.NEW
                    ).show(activity?.supportFragmentManager, "CustomerDialog")
                }, 100)
                true
            }
            else -> false
        }
    }

}
