package wottrich.github.io.yourdiary.view.fragments.spending

import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.model.User

class SpendingFragmentViewModel(val user: User) {

    val boxSpendingList: List<Spending> get() = user.spendingList
    var selectedSpending: MutableList<Spending> = mutableListOf()

}