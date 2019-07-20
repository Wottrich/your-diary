package wottrich.github.io.yourdiary.view.dialog

import android.annotation.SuppressLint
import android.support.v7.widget.GridLayoutManager
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

    override fun initValues() {
        customerAdapter.color = activity?.getDrawable(R.color.transparent_white)
        customerAdapter.onClick = this
        baseView.rvCustomers.adapter = customerAdapter
        val manager = GridLayoutManager(activity, 2)
        baseView.rvCustomers.layoutManager = manager
        baseView.ivClose.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivClose -> {
                KeyboardUtils.hideKeyboard(requireActivity(), baseView)
                baseView.postDelayed(this::dismiss, 10)
            }
            else -> {
                onSelectedCustomer()
                dismiss()
            }
        }
    }
}