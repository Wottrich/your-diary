package wottrich.github.io.yourdiary.view.holders.dailyIncome

import android.view.View
import kotlinx.android.synthetic.main.row_profile_daily_income.view.*
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.generics.BaseViewHolder
import java.util.Date

class DailyIncomeViewHolder(var view: View) : BaseViewHolder(view) {

    var onIncomeClick: (() -> Unit)? = null

    override fun initValues() {
        view.tvDate.text = Date().getDateString()

        buildToday()
    }

    private fun buildToday () {
        var totalDay = 0.0
        val today = Date().withoutTime()

        for (customer in user.customers) {

            for (order in customer.orders) {

                val orderDate = order.date.withoutTime()

                if (orderDate == today) {
                    totalDay += order.price
                }
            }

        }

        view.tvIncome.text = totalDay.format()

        itemView.setOnClickListener {
            this.onIncomeClick?.invoke()
        }

    }

}