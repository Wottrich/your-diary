package wottrich.github.io.yourdiary.view.activity.income

import androidx.lifecycle.ViewModel
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.extensions.withoutTime
import wottrich.github.io.yourdiary.model.*
import java.util.*
import kotlin.Comparator

/**
 * @author Wottrich
 * @author lucas.wottrich@operacao.rcadigital.com.br
 * @since 2020-02-25
 *
 * Copyright © 2020 your-diary. All rights reserved.
 *
 */
 
class IncomeDayViewModel : ViewModel() {

    val user: User get () = getUser()

    private val spendingList: List<Spending> get() = user.spendingList

    var spendOrderList: MutableList<OrderSpending> = mutableListOf()
    var days: MutableList<Day> = mutableListOf()

    init {
        joinSpendWithOrder()
        sortByDateSpendAndOrder()
        separateByDay()
    }

    private fun separateByDay () {

        var date: Date? = null
        var day: Day? = null

        val days: MutableList<Day> = mutableListOf()

        for (item in spendOrderList) {

            fun setItem (addDay: Boolean) {

                val price = item.price

                if (item.isOrder) {
                    day!!.loss += price
                } else {
                    day!!.profit += price
                }

                if (addDay) {
                    days.add(day!!)
                }

            }

            when {
                date == null || (date.withoutTime() != item.date.withoutTime()) -> {
                    date = item.date
                    day = Day(date)
                    setItem(true)
                }
                else -> setItem(false)
            }

        }

        this.days = days

    }

    private fun sortByDateSpendAndOrder () {

        spendOrderList.sortWith(Comparator { actualItem, nextItem ->

            val actualDate = actualItem.date
            val nextDate = nextItem.date

            actualDate.compareTo(nextDate)
        })

    }

    private fun joinSpendWithOrder () {

        val items = mutableListOf<OrderSpending>()

        for (customer in user.customers) {

            if (customer != null) {

                for (order in customer.orders) {

                    items.add(OrderSpending(order))

                }

            }

        }

        for (spend in spendingList) {

            items.add(OrderSpending(spend))

        }

        this.spendOrderList = items

    }

}