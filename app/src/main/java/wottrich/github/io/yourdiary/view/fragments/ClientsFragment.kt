package wottrich.github.io.yourdiary.view.fragments

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_clients.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.OrderAdapter
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.extensions.totalPriceFromSelectedCustomer
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.Order
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
        baseView.btnRegisterOrder.setOnClickListener(this)
        baseView.btnFirstRegister.setOnClickListener(this)
        loadCustomer()
    }

    fun loadCustomer() {
        if (clientCount > 0) {
            customerViews(View.VISIBLE)
            baseView.clFirstLogin.visibility = View.GONE
            baseView.tvCustomer.text = client?.name
            baseView.tvCountOrder.text = String.format("%d pedidos por mês", client?.orders?.size ?: 0)
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
        baseView.btnRegisterOrder.visibility = visible
        baseView.ivDrop.visibility = visible
        //baseView.divider.visibility = visible

    }

    private fun onClickOrder (order: Order?) {

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRegisterOrder -> {

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
