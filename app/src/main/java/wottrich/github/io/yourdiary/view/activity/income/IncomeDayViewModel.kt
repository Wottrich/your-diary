package wottrich.github.io.yourdiary.view.activity.income

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.extensions.reInit
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

    companion object {
        //initial date
        const val tagIncomeInitialDate = 100
        const val tagIncomeInitialDateTextViewClick = 200

        //final date
        const val tagIncomeFinalDate = 300
        const val tagIncomeFinalDateTextViewClick = 400
    }

    //Search
    var initialDate: Date? = null
    var finalDate: Date? = null

    val user: User get () = getUser()

    private val spendingList: List<Spending> get() = user.spendingList
    private var spendOrderList: MutableList<OrderSpending> = mutableListOf()

    var days: MutableLiveData<MutableList<Day>> = MutableLiveData()

    init {
        initSearch()
    }

    fun initSearch() {
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
                    day!!.profit += price
                } else {
                    day!!.loss += price
                }

                if (addDay) {
                    day?.let {
                        it.isProfit = it.profit > it.loss
                        days.add(it)
                    }
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

        this.days.value = days

    }

    private fun sortByDateSpendAndOrder () {

        spendOrderList.sortWith(Comparator { actualItem, nextItem ->

            val actualDate = actualItem.date
            val nextDate = nextItem.date

            nextDate.compareTo(actualDate)
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

        if (initialDate != null && finalDate != null) {

            val filteredItems = mutableListOf<OrderSpending>()

            for (item in items) {

                if (item.date.reInit() >= initialDate.reInit() && item.date.reInit() <= finalDate.reInit()) {
                    filteredItems.add(item)
                }

            }

            this.spendOrderList = filteredItems

            return
        }

        this.spendOrderList = items

    }

    fun isEmptySearch () : Boolean {
        return initialDate == null || finalDate == null
    }

    fun clearSearch () {
        initialDate = null
        finalDate = null
        initSearch()
    }

}