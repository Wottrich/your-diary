package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import wottrich.github.io.yourdiary.extensions.compareActualDate

@Entity
data class User(
    var uid: String = "",
    var email: String? = null,
    var name: String? = null,
    var age: Int? = null,
    var income: Double = 0.0,
    var lockApp: Boolean = false,
    var expectedIncome: Double = 0.0
) {

    @Id var id: Long = 0

    val allSpendingValue: Float
        get() =  loadAllSpending()

    val allCustomerValue: Float
        get() = loadAllCustomers()

    constructor(name: String, age: Int, income: Double, lockApp: Boolean) : this() {
        this.name = name
        this.age = age
        this.income = income
        this.lockApp = lockApp
    }

    @Backlink(to = "user")
    lateinit var customers: ToMany<Customer>

    @Backlink(to = "user")
    lateinit var spendingList: ToMany<Spending>


    private fun loadAllSpending () : Float {
        var finalValue = 0.0f

        for (spending in spendingList) {

            if (spending.date != null && spending.date!!.compareActualDate(month = true, year = true)) {
                val sum = spending.price?.toFloat() ?: 0f
                finalValue += sum
            }

        }

        return finalValue
    }

    private fun loadAllCustomers () : Float {
        var finalValue = 0.0f

        for (customer in customers) {

            for (order in customer.orders) {

                if (order.date.compareActualDate(month = true, year = true)) {
                    val sum = order.price.toFloat()
                    finalValue += sum
                }

            }

        }

        return finalValue
    }


}