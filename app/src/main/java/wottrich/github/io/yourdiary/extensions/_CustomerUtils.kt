package wottrich.github.io.yourdiary.extensions

import wottrich.github.io.yourdiary.model.Customer

fun Customer?.totalPriceFromSelectedCustomer(): String {
    val price = 0.0
    for (orders in this?.orders ?: arrayListOf()) {
        price.plus(orders.price ?: 0.0)
    }
    return price.addSymbol()
}