package wottrich.github.io.yourdiary.extensions

import wottrich.github.io.yourdiary.model.Customer
import java.util.*

fun Customer?.totalPriceFromSelectedCustomer(): String {
    var price = 0.0
    val orders = this?.orders ?: return price.format()
    for (itemOrder in orders) {
        price += itemOrder.price
    }
    return price.format()
}

fun Customer?.lastDateOrder (): String {
    var latestDate: Date? = null

    val orders = this?.orders ?: return ""
    for (order in orders) {
        if (latestDate == null) {
            latestDate = order.date
        } else if (latestDate.before(order.date)) {
            latestDate = order.date
        }
    }

    return latestDate?.getDateString() ?: ""
}