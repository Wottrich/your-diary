package wottrich.github.io.yourdiary.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.format
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.view.holders.ExpectedIncomeViewHolder
import wottrich.github.io.yourdiary.view.holders.income.IncomeViewHolder
import wottrich.github.io.yourdiary.view.holders.graph.GraphViewHolder

class ProfileAdapter(
    var context: Context,
    var inflate: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val graphLayout = R.layout.row_profile_graph
    private val incomeLayout = R.layout.row_profile_income
    private val expectedIncomeLayout = R.layout.row_profile_expected_income

    var onExpectedIncomeClick: (() -> Unit)? = null

    val hasValue: Boolean
        get() = user.allCustomerValue != 0f || user.allSpendingValue != 0f

    private val user: User
        get() = getUser()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflate.inflate(viewType, viewGroup, false)
        return when(viewType) {
            incomeLayout -> IncomeViewHolder(view)
            graphLayout -> GraphViewHolder(context, view)
            expectedIncomeLayout -> ExpectedIncomeViewHolder(context, view)
            else -> GraphViewHolder(context, view)
        }
    }

    override fun getItemCount(): Int = if (hasValue) 3 else 2

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> incomeLayout
            1 -> if (hasValue) graphLayout else expectedIncomeLayout
            2 -> expectedIncomeLayout
            else -> super.getItemViewType(position)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            incomeLayout -> {
                with(viewHolder as IncomeViewHolder) {
                    initValues(user.income.format())
                }
            }
            graphLayout -> {
                with(viewHolder as GraphViewHolder) {
                    initValues(user)
                }
            }
            expectedIncomeLayout -> {
                with(viewHolder as ExpectedIncomeViewHolder) {
                    initValues(user) {
                        onExpectedIncomeClick?.invoke()
                    }
                }
            }
        }
    }
}