package wottrich.github.io.yourdiary.view.holders

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.View.VISIBLE
import android.view.View.GONE
import kotlinx.android.synthetic.main.row_profile_expected_income.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.format
import wottrich.github.io.yourdiary.extensions.getResourseColor
import wottrich.github.io.yourdiary.model.User

class ExpectedIncomeViewHolder(var context: Context, var view: View) : RecyclerView.ViewHolder(view) {


    fun initValues (user: User, onClick: () -> Unit) {

        var actualIncome = user.allCustomerValue.minus(user.allSpendingValue)

        val isNegativeValue = actualIncome < 0

        if (isNegativeValue) {
            actualIncome *= -1
        }

        view.tvActualIncome.setTextColor(if (isNegativeValue) {
            context.getResourseColor(R.color.venetian_red)
        } else {
            context.getResourseColor(R.color.shiny_shamrock)
        })

        view.tvActualIncome.text = actualIncome.format()

        val hasIncome = user.expectedIncome > 0.0

        view.tvExpectedIncome.visibility = if (hasIncome) VISIBLE else GONE
        view.expectedIncomeInfo.visibility = if (hasIncome) VISIBLE else GONE
        view.tvExpectedIncomeInfo.visibility = if (hasIncome) GONE else VISIBLE

        itemView.rootView.setOnClickListener {
            onClick()
        }

    }

}