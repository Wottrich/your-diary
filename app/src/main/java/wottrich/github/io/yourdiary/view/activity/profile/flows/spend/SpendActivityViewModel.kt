package wottrich.github.io.yourdiary.view.activity.profile.flows.spend

import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.getActualMonthByMonth
import wottrich.github.io.yourdiary.extensions.getActualYear
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.widget.MonthYear

class SpendActivityViewModel {

    val user: User
        get() = getUser()

    val boxSpendingList: List<Spending>
        get() {


            return user.spendingList
        }

    var selectedSpending: MutableList<Spending> = mutableListOf()
    val onLongClickEnable: Boolean
        get() = selectedSpending.isNotEmpty()

    var selectedMonthYear = MonthYear(getActualMonthByMonth(), getActualYear())

    fun deleteSelectedItems(result: () -> Unit) {
        box<Spending>().remove(selectedSpending)
        result()
    }

}