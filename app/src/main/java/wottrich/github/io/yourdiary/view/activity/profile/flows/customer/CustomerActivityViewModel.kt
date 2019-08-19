package wottrich.github.io.yourdiary.view.activity.profile.flows.customer

import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.model.User

class CustomerActivityViewModel {

    var ordersSelected: MutableList<Order> = mutableListOf()
    val onLongClickableMode: Boolean
        get() = ordersSelected.isNotEmpty()

    val user: User get() = getUser()

    val clientCount: Int get() = user.customers.size

    val client: Customer? get() = Customer.selectedCustomer()
    val orders: List<Order> get() = client?.orders ?: listOf()

    fun deleteSelectedOrders (success: () -> Unit) {
        box<Order>().remove(ordersSelected)
        success()
    }

}