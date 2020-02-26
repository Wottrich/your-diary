package wottrich.github.io.yourdiary.model

import java.util.*

/**
 * @author Wottrich
 * @author lucas.wottrich@operacao.rcadigital.com.br
 * @since 2020-02-25
 *
 * Copyright © 2020 your-diary. All rights reserved.
 *
 */

class OrderSpending(
    var title: String,
    var price: Double,
    var description: String,
    var date: Date,
    var isOrder: Boolean
) {

    constructor(order: Order) : this(
        order.title,
        order.price,
        order.description,
        order.date,
        true
    )

    constructor(spend: Spending) : this(
        spend.title ?: "",
        spend.price ?: 0.0,
        spend.description ?: "",
        spend.date ?: Date(),
        false
    )

}