package wottrich.github.io.yourdiary.view.fragments.customer

import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.Order

class CustomerFragmentViewModel {

    var onLongClickableMode: Boolean = false
    var ordersSelected: MutableList<Order> = mutableListOf()

    val clientCount: Int get() = boxList<Customer>().size

    val client: Customer? get() = Customer.selectedCustomer()
    val orders: List<Order> get() = client?.orders ?: listOf()

    fun deleteSelectedOrders (success: () -> Unit) {
        box<Order>().remove(ordersSelected)
        success()
    }

}