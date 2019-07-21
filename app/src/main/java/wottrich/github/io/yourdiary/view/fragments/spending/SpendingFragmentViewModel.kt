package wottrich.github.io.yourdiary.view.fragments.spending

import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.model.User

class SpendingFragmentViewModel {

    val user: User
        get() = getUser()

    val boxSpendingList: List<Spending> get() = user.spendingList

    var selectedSpending: MutableList<Spending> = mutableListOf()
    val onLongClickEnable: Boolean
        get() = selectedSpending.isNotEmpty()

    fun deleteSelectedItems (result: () -> Unit) {
        box<Spending>().remove(selectedSpending)
        result()
    }

}