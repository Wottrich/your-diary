package wottrich.github.io.yourdiary.model

import java.util.Date

/**
 * @author Wottrich
 * @author lucas.wottrich@operacao.rcadigital.com.br
 * @since 2020-02-25
 *
 * Copyright © 2020 your-diary. All rights reserved.
 *
 */

class Day () {

    var profit: Double = 0.0
    var loss: Double = 0.0
    var isProfit: Boolean = false
    var date: Date = Date()

    constructor(date: Date) : this () {
        this.date = date
    }

}