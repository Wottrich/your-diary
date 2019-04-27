package wottrich.github.io.yourdiary.view.fragments


import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_clients.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.extensions.totalPriceFromSelectedCustomer
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.view.dialog.CustomerDialog
import kotlin.properties.Delegates

@SuppressLint("StaticFieldLeak")
open class ClientsFragment : BaseFragment(R.layout.fragment_clients), View.OnClickListener {

    private val clientCount: Int get() = boxList<Customer>().size

    private val client: Customer? by lazy {
        Customer.selectedCustomer()
    }

    companion object : ClientsFragment() {
        @JvmStatic
        fun newInstance() = ClientsFragment()
    }

    override fun initValues() {
        baseView.constHeaderClients.setOnClickListener (this)
        baseView.btnRegisterOrder.setOnClickListener(this)
        loadCustomer()
    }

    fun loadCustomer() {
        if (clientCount > 0) {
            customerViews(View.VISIBLE)
            baseView.tvCustomer.text = client?.name
            baseView.tvCountOrder.text = String.format("%d pedidos por mês", client?.orders?.size ?: "0")
            baseView.tvPriceOrder.text = String.format("%s no mês", client?.totalPriceFromSelectedCustomer() ?: "0")
        } else {
            customerViews(View.GONE)
            baseView.tvCustomer.text = getString(R.string.fragment_clients_register_customer)
        }
    }

    private fun customerViews (visible: Int) {
        baseView.constInformation.visibility = visible
        baseView.btnRegisterOrder.visibility = visible
        baseView.ivDrop.visibility = visible
        baseView.divider.visibility = visible
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRegisterOrder -> {

            }
            R.id.constHeaderClients -> {
                if(clientCount > 0) {

                } else {
                    CustomerDialog {
                        Toast.makeText(activity, "Cliente Registrado", Toast.LENGTH_SHORT).show()
                        loadCustomer()
                    }.show(activity?.supportFragmentManager, "CustomerDialog")
                }
            }
        }
    }

}
