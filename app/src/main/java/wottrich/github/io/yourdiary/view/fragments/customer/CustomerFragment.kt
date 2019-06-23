package wottrich.github.io.yourdiary.view.fragments.customer

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_clients.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.OrderAdapter
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.CustomerType
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.model.OrderType
import wottrich.github.io.yourdiary.view.activity.MainActivity
import wottrich.github.io.yourdiary.view.activity.order.RegisterOrderActivity
import wottrich.github.io.yourdiary.view.dialog.customer.CustomerDialog
import wottrich.github.io.yourdiary.view.dialog.ShowCustomersDialog
import java.util.*

@SuppressLint("StaticFieldLeak")
open class CustomerFragment : BaseFragment(R.layout.fragment_clients), View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    private val viewModel: CustomerFragmentViewModel by lazy {
        CustomerFragmentViewModel()
    }

    private lateinit var _toolbar: Toolbar

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter(viewModel.orders, requireActivity(), this::onClickOrder, this::onLongClickOrder)
    }

    companion object : CustomerFragment() {
        @JvmStatic
        fun newInstance() = CustomerFragment()
    }

    override fun initValues() {
        _toolbar = baseView.toolbar
        baseView.rvOrders.adapter = orderAdapter
        configMenu()
        loadCustomer()
    }

    fun loadCustomer() {
        baseView.clMoreInfo.visibility = View.GONE
        if (viewModel.clientCount > 0) {
            _toolbar.title = viewModel.client?.name
            _toolbar.subtitle = "Novo pedido..."
            updateInformation()
            showMenu(true)
            orderAdapter.updateList()
        } else {
            _toolbar.title = "Novo Cliente"
            _toolbar.subtitle = null
            showMenu(false)
        }
    }

    private fun updateInformation () {
        baseView.tvActualMonth.text = Calendar.getInstance().actualMonth()
        baseView.tvCountOrder.text = String.format("%d %s", viewModel.client?.orders?.size ?: 0, if(viewModel.client?.orders?.size == 1) "pedido" else "pedidos");
        baseView.tvPriceOrder.text = viewModel.client.totalPriceFromSelectedCustomer()
    }

    private fun showMenu (show: Boolean) {
        _toolbar.menu.getItem(0).isVisible = show
        _toolbar.menu.getItem(1).isVisible = show
    }

    private fun configMenu () {
        _toolbar.inflateMenu(R.menu.customer_options)
        _toolbar.menu.getItem(2).isVisible = false
        _toolbar.setOnMenuItemClickListener(this)
        _toolbar.setOnClickListener(this)
    }

    private fun onClickOrder (order: Order?, position: Int) {
        if (!viewModel.onLongClickableMode) {
            if (order != null) {
                val intent = Intent(activity, RegisterOrderActivity::class.java).apply {
                    this orderType OrderType.EDIT
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
        viewModel.onLongClickableMode = !viewModel.onLongClickableMode
        _toolbar.menu.getItem(0).isVisible = !_toolbar.menu.getItem(0).isVisible
        _toolbar.menu.getItem(1).isVisible = !_toolbar.menu.getItem(1).isVisible
        _toolbar.menu.getItem(2).isVisible = !_toolbar.menu.getItem(2).isVisible
    }

    fun cleanSelectedItems () {
        if (viewModel.onLongClickableMode) {
            selectedItem()
            viewModel.ordersSelected.clear()
            this.orderAdapter.updateList()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.toolbar -> {
                if (viewModel.clientCount > 0) {
                    cleanSelectedItems()
                    val intent = Intent(activity, RegisterOrderActivity::class.java).apply {
                        this orderType OrderType.NEW
                        this userId viewModel.client?.id
                    }
                    activity?.startActivityForResult(intent, MainActivity.UPDATE_ORDER_LIST)
                } else {
                    CustomerDialog(
                        this::loadCustomer,
                        CustomerType.NEW
                    ).show(activity?.supportFragmentManager, "CustomerDialog")
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
            R.id.itTimeLine -> {
                if (baseView.clMoreInfo.visibility == View.GONE) {
                    baseView.clMoreInfo.visibility = View.VISIBLE
                } else {
                    baseView.clMoreInfo.visibility = View.GONE
                }
                true
            }
            R.id.itDelete -> {
                viewModel.deleteSelectedOrders {
                    this.orderAdapter.updateList()
                    selectedItem()
                    updateInformation()
                }
                true
            }
            R.id.itSettings -> {
                CustomerDialog(
                    this::loadCustomer,
                    CustomerType.EDIT,
                    viewModel.client?.id ?: -1
                ).show(activity?.supportFragmentManager, "CustomerDialog")
                true
            }
            else -> false
        }
    }

}
