package wottrich.github.io.yourdiary.view.activity.order

import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.model.Order

class RegisterOrderViewModel(orderId: Long) {

    var order: Order? = null

    init {
        order = box<Order>().get(orderId)
    }

}