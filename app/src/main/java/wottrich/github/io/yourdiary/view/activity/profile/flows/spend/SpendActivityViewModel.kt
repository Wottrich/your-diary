package wottrich.github.io.yourdiary.view.activity.profile.flows.spend

import androidx.lifecycle.MutableLiveData
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.widget.MonthYear
import java.util.*

class SpendActivityViewModel {

    val user: User
        get() = getUser()

    val boxSpendingList: List<Spending>
        get() {

            val spendList = user.spendingList

            spendList.sortWith(Comparator { actualSpend, nextSpend ->

                val actualDate = actualSpend.date ?: Date()
                val nextDate = nextSpend.date ?: Date()

                actualDate.compareTo(nextDate)
            })

            val spendFiltered = spendList.filter {

                val validYear = it.date?.exYear() == selectedMonthYear.year.toString()
                it != null && validYear

            }.filter {

                val validMonth = it.date?.exMonth()?.toInt().toString() == selectedMonthYear.month.num.toString()
                it != null && validMonth

            }

            var totalAmountByPeriod = 0.0

            for (spend in spendFiltered) {

                totalAmountByPeriod += spend.price ?: 0.0

            }

            this.totalAmountByPeriod.value = totalAmountByPeriod

            return spendFiltered
        }

    var selectedSpending: MutableList<Spending> = mutableListOf()
    val onLongClickEnable: Boolean
        get() = selectedSpending.isNotEmpty()

    //Search
    var totalAmountByPeriod = MutableLiveData<Double>()
    var selectedMonthYear = MonthYear(getActualMonthByMonth(), getActualYear())

    fun deleteSelectedItems(result: () -> Unit) {
        box<Spending>().remove(selectedSpending)
        result()
    }

    fun resetSearch () {
        this.selectedMonthYear = MonthYear(getActualMonthByMonth(), getActualYear())
    }

}