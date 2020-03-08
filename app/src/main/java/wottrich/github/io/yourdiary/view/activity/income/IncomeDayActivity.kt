package wottrich.github.io.yourdiary.view.activity.income

import android.view.View
import kotlinx.android.synthetic.main.activity_income_day.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.IncomeDayAdapter
import wottrich.github.io.yourdiary.extensions.OnCalendarPicker
import wottrich.github.io.yourdiary.extensions.getDateString
import wottrich.github.io.yourdiary.extensions.initDatePickerDialog
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.model.Day
import wottrich.github.io.yourdiary.model.OrderSpending
import java.util.*

class IncomeDayActivity : BaseActivity(R.layout.activity_income_day), OnCalendarPicker {

    private val viewModel: IncomeDayViewModel by lazy {
        initViewModelProvider<IncomeDayViewModel>()
    }

    private val adapter: IncomeDayAdapter by lazy {
        IncomeDayAdapter(this)
    }

    override fun initValues() {

        rvDay.adapter = adapter

        viewModel.days observer this::onDaysUpdate

        if (viewModel.isEmptySearch()) {
            clSearchLabel.visibility = View.GONE
        }

        setupMenu()

    }

    override fun onInitListeners() {

        toolbar.setNavigationOnClickListener {
            finish()
        }

        tvInitialDate.setOnClickListener {
            initDatePickerDialog(this, IncomeDayViewModel.tagIncomeInitialDateTextViewClick).show()
        }

        tvFinalDate.setOnClickListener {
            initDatePickerDialog(this, IncomeDayViewModel.tagIncomeFinalDateTextViewClick).show()
        }

        clSearchLabel.setOnClickListener {
            viewModel.clearSearch()
            clSearchLabel.visibility = View.GONE
            tvInitialDate.text = ""
            tvFinalDate.text = ""
        }

    }

    private fun setupMenu () {

        toolbar.inflateMenu(R.menu.range_date)
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.itDateRange) {
                initDatePickerDialog(this, IncomeDayViewModel.tagIncomeInitialDate).show()
                return@setOnMenuItemClickListener true
            }

            return@setOnMenuItemClickListener false
        }

    }

    private fun onDaysUpdate (days: MutableList<Day>?) {

        if (days != null) {
            adapter.update(days)
        } else {
            showAlertDialog(
                title = "Ops!",
                message = "Ocorreu um erro desconhecido, por favor tente novamente mais tarde",
                cancelable = false
            ) {
                finish()
            }
        }

    }

    override fun onDate(date: Date, dateString: String, tag: Int) {
        if (tag == IncomeDayViewModel.tagIncomeInitialDate
            || tag == IncomeDayViewModel.tagIncomeInitialDateTextViewClick) {

            viewModel.initialDate = date
            tvInitialDate.text = viewModel.initialDate?.getDateString()
            tvUntilOrNoSearch.text = getString(R.string.activity_income_day_search_label_until)

            clSearchLabel.visibility = View.VISIBLE

            if (tag == IncomeDayViewModel.tagIncomeInitialDateTextViewClick) {
                viewModel.initSearch()
            } else {
                initDatePickerDialog(this).show()
            }

        } else {
            clSearchLabel.visibility = View.VISIBLE
            viewModel.finalDate = date
            tvFinalDate.text = viewModel.finalDate?.getDateString()
            viewModel.initSearch()
        }
    }


}
