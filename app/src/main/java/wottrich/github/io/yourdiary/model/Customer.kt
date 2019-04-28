package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import wottrich.github.io.yourdiary.extensions.addSymbol
import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.extensions.put

@Entity
open class Customer() {
    @Id var id: Long = 0
    var name: String? = null
    var selected: Boolean = false

    constructor(name: String, selected: Boolean = true) : this () {
        this.name = name
        this.selected = selected
    }

    @Backlink(to = "customer")
    lateinit var orders: List<Order>

    companion object {
        fun selectedCustomer () : Customer? {
            return boxList<Customer>().find { it.selected }
        }

        fun deselectCustomer () {
            val customer = selectedCustomer()
            customer?.selected = false
            put(customer)
        }

        fun changeCustomer(customer: Customer) {
            val selectedCustomer = selectedCustomer()
            selectedCustomer?.selected = false
            customer.selected = true
            put(selectedCustomer, customer)
        }
    }

}