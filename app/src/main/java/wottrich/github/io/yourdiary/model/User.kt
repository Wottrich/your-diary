package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.annotation.Transient
import io.objectbox.relation.ToMany

@Entity
class User() {

    @Id var id: Long = 0
    var name: String? = null
    var age: Int? = null
    var income: Double = 0.0
    var lockApp: Boolean = false
    var expectedIncome: Double = 0.0

    val allSpendingValue: Float
        get() =  loadAllSpending()

    val allCustomerValue: Float
        get() = loadAllCustomers()

    constructor(name: String?, age: Int?, income: Double, lockApp: Boolean) : this() {
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

        for (speding in spendingList) {
            val sum = speding.price?.toFloat() ?: 0f
            finalValue += sum
        }

        return finalValue
    }

    private fun loadAllCustomers () : Float {
        var finalValue = 0.0f

        for (customer in customers) {

            for (order in customer.orders) {
                val sum = order.price.toFloat()
                finalValue += sum
            }

        }

        return finalValue
    }


}