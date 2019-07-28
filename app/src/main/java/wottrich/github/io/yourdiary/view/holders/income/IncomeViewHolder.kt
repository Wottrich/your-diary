package wottrich.github.io.yourdiary.view.holders.income

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.row_profile_income.view.*

class IncomeViewHolder (var view: View) : RecyclerView.ViewHolder(view) {

    fun initValues(income: String) {
        view.tvIncome.text = income
    }

}