package wottrich.github.io.yourdiary.view.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_clients.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.OrderAdapter
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.model.OrderType
import wottrich.github.io.yourdiary.view.activity.MainActivity
import wottrich.github.io.yourdiary.view.activity.order.RegisterOrderActivity
import wottrich.github.io.yourdiary.view.dialog.CustomerDialog
import wottrich.github.io.yourdiary.view.dialog.ShowCustomersDialog

@SuppressLint("StaticFieldLeak")
open class ClientsFragment : BaseFragment(R.layout.fragment_clients), View.OnClickListener {

    private val clientCount: Int get() = boxList<Customer>().size

    private val client: Customer? get() = Customer.selectedCustomer()
    private val orders: List<Order> get() = client?.orders ?: listOf()

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter(orders, requireActivity(), this::onClickOrder)
    }

    companion object : ClientsFragment() {
        @JvmStatic
        fun newInstance() = ClientsFragment()
    }

    override fun initValues() {
        baseView.constHeaderClients.setOnClickListener (this)
        baseView.constNewOrder.setOnClickListener(this)
        baseView.btnFirstRegister.setOnClickListener(this)
        loadCustomer()
    }

    fun loadCustomer() {
        if (clientCount > 0) {
            customerViews(View.VISIBLE)
            baseView.clFirstLogin.visibility = View.GONE
            baseView.tvCustomer.text = client?.name
            baseView.tvCountOrder.text = String.format("%d pedidos no mês", client?.orders?.size ?: 0)
            baseView.tvPriceOrder.text = String.format("%s no mês", client?.totalPriceFromSelectedCustomer() ?: "0")
            baseView.rvOrders.adapter = orderAdapter
            orderAdapter.updateList()
        } else {
            customerViews(View.GONE)
            baseView.clFirstLogin.visibility = View.VISIBLE
        }
    }

    private fun customerViews (visible: Int) {
        baseView.constHeaderClients.visibility = visible
        baseView.constInformation.visibility = visible
        baseView.constNewOrder.visibility = visible
        baseView.ivDrop.visibility = visible
    }

    private fun onClickOrder (order: Order?) {
        if (order != null) {
            val intent = Intent(activity, RegisterOrderActivity::class.java).apply {
                this orderType OrderType.EDIT
                this orderId order.id
            }
            activity?.startActivityForResult(intent, MainActivity.UPDATE_ORDER_LIST)
        } else {
            Toast.makeText(activity, "Error to get order id", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.constNewOrder -> {
                val intent = Intent(activity, RegisterOrderActivity::class.java).apply {
                    this orderType OrderType.NEW
                    this userId client?.id
                }
                activity?.startActivityForResult(intent, MainActivity.UPDATE_ORDER_LIST)
            }
            R.id.btnFirstRegister -> {
                CustomerDialog {
                    loadCustomer()
                }.show(activity?.supportFragmentManager, "CustomerDialog")
            }
            R.id.constHeaderClients -> {
                ShowCustomersDialog(this::loadCustomer).show(activity?.supportFragmentManager, "ShowCustomerDialog")
            }
        }
    }

}
