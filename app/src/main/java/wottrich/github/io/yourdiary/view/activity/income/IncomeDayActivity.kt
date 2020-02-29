package wottrich.github.io.yourdiary.view.activity.income

import kotlinx.android.synthetic.main.activity_income_day.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.IncomeDayAdapter
import wottrich.github.io.yourdiary.generics.BaseActivity

class IncomeDayActivity : BaseActivity(R.layout.activity_income_day) {

    private val viewModel: IncomeDayViewModel by lazy {
        initViewModelProvider<IncomeDayViewModel>()
    }

    private val adapter: IncomeDayAdapter by lazy {
        IncomeDayAdapter(this)
    }

    override fun initValues() {

        rvDay.adapter = adapter
        adapter.update(viewModel.days)

    }

    override fun onInitListeners() {

        toolbar.setNavigationOnClickListener {
            finish()
        }

    }


}
