package wottrich.github.io.yourdiary.extensions

import wottrich.github.io.yourdiary.model.Customer

fun Customer?.totalPriceFromSelectedCustomer(): String {
    var price = 0.0
    val orders = this?.orders ?: return price.format()
    for (itemOrder in orders) {
        price += itemOrder.price
    }
    return price.format()
}