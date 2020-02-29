package wottrich.github.io.yourdiary.view.activity.profile.flows.customer

import androidx.lifecycle.MutableLiveData
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.widget.MonthYear
import java.util.*
import kotlin.Comparator

class CustomerActivityViewModel {

    var ordersSelected: MutableList<Order> = mutableListOf()
    val onLongClickableMode: Boolean
        get() = ordersSelected.isNotEmpty()

    val user: User get() = getUser()

    val clientCount: Int get() = user.customers.size

    val client: Customer? get() = Customer.selectedCustomer()
    val orders: List<Order> get() {
        val internalOrderList = client?.orders ?: mutableListOf<Order>()

        internalOrderList.sortWith(Comparator { actualOrder, nextOrder ->

            val actualDate = actualOrder.date
            val nextDate = nextOrder.date

            nextDate.compareTo(actualDate)
        })

        val internalOrderFiltered = internalOrderList.filter {
            it.date.exYear() == selectedMonthYear.year.toString()
        }.filter {
            it.date.exMonth().toInt().toString() == selectedMonthYear.month.num.toString()
        }

        var totalAmountByPeriod = 0.0

        for (order in internalOrderFiltered) {

            totalAmountByPeriod += order.price

        }

        this.totalAmountByPeriod.value = totalAmountByPeriod

        return internalOrderFiltered
    }

    //Search
    var totalAmountByPeriod = MutableLiveData<Double>()
    var selectedMonthYear = MonthYear(getActualMonthByMonth(), getActualYear())

    fun deleteSelectedOrders (success: () -> Unit) {
        box<Order>().remove(ordersSelected)
        success()
    }

    fun resetSearch () {
        this.selectedMonthYear = MonthYear(getActualMonthByMonth(), getActualYear())
    }

}