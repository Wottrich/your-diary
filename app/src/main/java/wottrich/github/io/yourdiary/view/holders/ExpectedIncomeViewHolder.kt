package wottrich.github.io.yourdiary.view.holders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.row_profile_expected_income.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.format
import wottrich.github.io.yourdiary.model.User

class ExpectedIncomeViewHolder(var context: Context, var view: View) : RecyclerView.ViewHolder(view) {

    fun initValues (user: User, onClick: () -> Unit) {
        view.tvActualIncome.text = (user.allCustomerValue.minus(user.allSpendingValue)).format()

        if (user.expectedIncome == 0.0) {
            view.tvExpectedIncome.visibility = View.GONE
            view.expectedIncomeInfo.visibility = View.GONE
            view.tvExpectedIncomeInfo.visibility = View.VISIBLE
        } else {
            view.tvExpectedIncome.visibility = View.VISIBLE
            view.expectedIncomeInfo.visibility = View.VISIBLE
            view.tvExpectedIncomeInfo.visibility = View.GONE
        }
        itemView.rootView.setOnClickListener {
            onClick()
        }
    }

}