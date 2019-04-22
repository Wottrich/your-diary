package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import wottrich.github.io.yourdiary.extensions.addSymbol
import wottrich.github.io.yourdiary.extensions.boxList

@Entity
open class Customer {
    @Id var id: Long = 0
    var name: String? = null
    var selected: Boolean = false

    @Backlink(to = "customer")
    lateinit var orders: List<Order>

    companion object {
        fun selectedCustomer () : Customer? {
            return boxList<Customer>().find { it.selected }
        }
    }

}