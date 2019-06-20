package wottrich.github.io.yourdiary.view.activity.order

import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.extensions.getDate
import wottrich.github.io.yourdiary.extensions.put
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.model.OrderType
import java.util.*

class RegisterOrderViewModel(var userId: Long, orderId: Long, var type: OrderType) {

    var order: Order? = null

    init {
        fun newOrder () {
            if (order == null) {
                order = Order()
            }
        }

        if (orderId != -1L) {
            order = box<Order>().get(orderId)
            newOrder()
        } else {
            newOrder()
        }
    }

    fun saveData () {
        when (type) {
            OrderType.EDIT -> {
                put(order)
            }
            OrderType.NEW -> {
                if (order != null && (order?.description!!.isNotEmpty() || (order?.title != null && order?.title!!.isNotEmpty()))) {
                    val customer = box<Customer>().get(userId)
                    customer.orders.add(order!!)
                    put(customer)
                }
            }
        }
    }

    fun changeTitle (title: String) {
        order?.title = title
    }

    fun changeDescription (description: String) {
        order?.description = description
    }

    fun changePrice (price: Double) {
        order?.price = price
    }

    fun changeDate (date: Date) {
        order?.date = date
    }


}