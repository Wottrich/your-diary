package wottrich.github.io.yourdiary.view.fragments.profile

import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.User

class ProfileViewModel {

    val user: User
        get() = getUser()

    private var countOrders: Int = -1

    private var countSpending: Int = -1

    fun canUpdate (result: (update: Boolean) -> Unit) {
        var actualCountOrders = 0
        val actualCountSpending = user.spendingList.count()

        for (customer in user.customers) {
            for (order in customer.orders) {
                actualCountOrders++
            }
        }

        if (actualCountSpending != countSpending && actualCountOrders != countOrders) {
            countSpending = actualCountSpending
            countOrders = actualCountOrders
            result(true)
        } else if (actualCountSpending != countSpending) {
            countSpending = actualCountSpending
            result(true)
        } else if (actualCountOrders != countOrders) {
            countOrders = actualCountOrders
            result(true)
        } else {
            result(false)
        }

    }

}