package wottrich.github.io.yourdiary.view.fragments


import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.fragment_clients.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Customer

@SuppressLint("StaticFieldLeak")
open class ClientsFragment : BaseFragment(R.layout.fragment_clients) {

    private val client: Customer? by lazy {
        Customer.selectedCustomer()
    }

    companion object : ClientsFragment() {
        @JvmStatic
        fun newInstance() = ClientsFragment()
    }

    override fun initValues() {
        baseView.constHeaderClients.setOnClickListener {  }
    }

    private fun loadCustomer() {
        if (client != null) {
            customerViews(View.VISIBLE)
            baseView.tvCountOrder.text = String.format("%d pedidos por mês", client?.orders?.size)
            baseView.tvPriceOrder.text = String.format("%s no mês", Customer.totalPriceFromSelectedCustomer())
        } else {
            customerViews(View.GONE)
        }
    }

    private fun customerViews (visible: Int) {
        baseView.tvPriceOrder.visibility = visible
        baseView.tvCountOrder.visibility = visible
        baseView.btnRegisterOrder.visibility = visible
    }


}
