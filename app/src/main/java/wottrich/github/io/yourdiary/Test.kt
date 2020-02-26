package wottrich.github.io.yourdiary

import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.model.OrderSpending
import java.util.*

class Test (var title: String, var amount: Double, var date: Date, var isProfit: Boolean = false) {

    override fun toString(): String {
        return title
    }

}
class Day2 () {

    var profit: Double = 0.0
    var loss: Double = 0.0
    var date: Date = Date()

    constructor(profit: Double, loss: Double, date: Date) : this() {
        this.profit = profit
        this.loss = loss
        this.date = date
    }

    constructor(date: Date) : this() {
        this.date = date
    }

    override fun toString(): String {
        return "\nProfit: $profit" +
                "\nLoss: $loss" +
                "\nDate: ${date.getDateString("yyyy-MM-dd")}" +
                "\n========"
    }

}

fun main() {

    val tests = mutableListOf<Test>(
        Test("1", 1.0, Date(), true),
        Test("3", 2.0, Date().addDays(-1)),
        Test("2", 1.2, Date(), true),
        Test("4", 1.5, Date().addDays(-2))
    )

    println(tests)

    tests.sortWith(Comparator { actualItem, nextItem ->

        val actualDate = actualItem.date
        val nextDate = nextItem.date

        nextDate.compareTo(actualDate)

    })

    println(tests)

    //fixed
    var date: Date? = null
    var day: Day2? = null

    var days = mutableListOf<Day2>()

    for (test in tests) {

        fun isProfit (addDay: Boolean = false) {
            if (day == null) {
                day = Day2(test.date)
            }

            if (test.isProfit) {
                day!!.profit += test.amount
            } else {
                day!!.loss += test.amount
            }

            if (addDay) {
                days.add(day!!)
            }

        }

        when {
            date == null -> {
                date = test.date
                isProfit(true)
            }
            date.withoutTime() != test.date.withoutTime() -> {
                date = test.date
                day = Day2(date)
                isProfit(true)
            }
            else -> isProfit(false)
        }

    }

    println(days)

}

