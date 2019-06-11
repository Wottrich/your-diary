package wottrich.github.io.yourdiary.view.activity.order

import android.view.View
import kotlinx.android.synthetic.main.activity_register_order.*
import kotlinx.android.synthetic.main.activity_register_order.btnDate
import kotlinx.android.synthetic.main.activity_register_order.etTitle
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.format
import wottrich.github.io.yourdiary.extensions.getDateString
import wottrich.github.io.yourdiary.extensions.setText
import wottrich.github.io.yourdiary.generics.BaseActivity

class RegisterOrderActivity : BaseActivity(R.layout.activity_register_order), View.OnClickListener {

    private val viewModel: RegisterOrderViewModel by lazy {
        RegisterOrderViewModel(intent.getLongExtra("orderId", -1))
    }

    override fun initValues() {
        etTitle setText viewModel.order?.title
        etAmount setText viewModel.order?.price?.format()
        etDescription setText viewModel.order?.description
        btnDate.text = viewModel.order?.date?.getDateString()
        btnDate.setOnClickListener(this)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnDate -> {

            }
        }
    }

}
