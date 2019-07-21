package wottrich.github.io.yourdiary.view.dialog

import android.annotation.SuppressLint
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import kotlinx.android.synthetic.main.dialog_show_customers.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.CustomerAdapter
import wottrich.github.io.yourdiary.generics.BaseDialog
import wottrich.github.io.yourdiary.utils.KeyboardUtils

@SuppressLint("ValidFragment")
class ShowCustomersDialog(var onSelectedCustomer: () -> Unit) : BaseDialog(R.layout.dialog_show_customers), View.OnClickListener {

    private val customerAdapter by lazy {
        CustomerAdapter(activity?.applicationContext)
    }

    lateinit var toolbar: Toolbar

    override fun initValues() {
        this.parent = baseView.llParent
        customerAdapter.onClick = this
        baseView.rvCustomers.adapter = customerAdapter
        val manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        baseView.rvCustomers.layoutManager = manager
        toolbar = baseView.toolbar
        toolbar.setNavigationOnClickListener {
            this.dismissAnimation()
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            else -> {
                onSelectedCustomer()
                dismissAnimation()
            }
        }
    }
}