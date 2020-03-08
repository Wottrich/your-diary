package wottrich.github.io.yourdiary.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.format
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.view.holders.ExpectedIncomeViewHolder
import wottrich.github.io.yourdiary.view.holders.FlowViewHolder
import wottrich.github.io.yourdiary.view.holders.dailyIncome.DailyIncomeViewHolder
import wottrich.github.io.yourdiary.view.holders.income.IncomeViewHolder
import wottrich.github.io.yourdiary.view.holders.graph.GraphViewHolder

class ProfileAdapter(
    private var context: Context,
    private var inflate: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val incomeLayout = R.layout.row_profile_income
    private val flowLayout = R.layout.row_flow
    private val dailyIncomeLayout = R.layout.row_profile_daily_income
    private val graphLayout = R.layout.row_profile_graph
    private val expectedIncomeLayout = R.layout.row_profile_expected_income

    var onLinkedEmailClick: (() -> Unit)? = null
    var onIncomeClick: (() -> Unit)? = null
    var onExpectedIncomeClick: (() -> Unit)? = null
    var onSpendClick: (() -> Unit)? = null
    var onCustomerClick: (() -> Unit)? = null

    private val hasValue: Boolean
        get() = user.allCustomerValue != 0f || user.allSpendingValue != 0f

    private val user: User
        get() = getUser()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = inflate.inflate(viewType, viewGroup, false)
        return when(viewType) {
            incomeLayout -> IncomeViewHolder(view)
            flowLayout -> FlowViewHolder(context, view)
            dailyIncomeLayout -> DailyIncomeViewHolder(view)
            graphLayout -> GraphViewHolder(context, view)
            expectedIncomeLayout -> ExpectedIncomeViewHolder(context, view)
            else -> GraphViewHolder(context, view)
        }
    }

    override fun getItemCount(): Int = if (hasValue) 5 else 4

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> incomeLayout
            1 -> flowLayout
            2 -> dailyIncomeLayout
            3 -> if (hasValue) graphLayout else expectedIncomeLayout
            4 -> expectedIncomeLayout

            else -> super.getItemViewType(position)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            incomeLayout -> {
                with(viewHolder as IncomeViewHolder) {
                    initValues(user) {
                        onLinkedEmailClick?.invoke()
                    }
                }
            }
            flowLayout -> {
                with(viewHolder as FlowViewHolder) {
                    initValues(Customer.selectedCustomer(), onSpendClick, onCustomerClick)
                }
            }
            dailyIncomeLayout -> {
                with(viewHolder as DailyIncomeViewHolder) {
                    this.onIncomeClick = this@ProfileAdapter.onIncomeClick
                    initValues()
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