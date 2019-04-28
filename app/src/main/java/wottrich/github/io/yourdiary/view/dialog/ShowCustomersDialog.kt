package wottrich.github.io.yourdiary.view.dialog

import android.annotation.SuppressLint
import android.view.View
import kotlinx.android.synthetic.main.show_customers_dialog.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.CustomerAdapter
import wottrich.github.io.yourdiary.generics.BaseDialog

@SuppressLint("ValidFragment")
class ShowCustomersDialog(var onSelectedCustomer: () -> Unit) : BaseDialog(R.layout.show_customers_dialog), View.OnClickListener {

    private val customerAdapter by lazy {
        CustomerAdapter(activity?.applicationContext)
    }

    override fun initValues() {
        customerAdapter.color = activity?.getDrawable(R.color.dark_white)
        customerAdapter.onClick = this
        baseView.rvCustomers.adapter = customerAdapter
        baseView.ivClose.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivClose -> dismiss()
            else -> {
                onSelectedCustomer()
                dismiss()
            }
        }
    }
}