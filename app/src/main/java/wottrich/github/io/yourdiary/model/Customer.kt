package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.extensions.getUser
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
    lateinit var orders: ToMany<Order>

    lateinit var user: ToOne<User>

    companion object {
        fun selectedCustomer () : Customer? {
            return getUser().customers.find { it.selected }
        }

        fun deselectCustomer () {
            val customer = selectedCustomer()
            customer?.selected = false
            put(customer)
        }

        fun deleteCustomer (id: Long) {
            val user = getUser()
            user.customers.removeById(id)
            user.customers.takeIf { it.isNotEmpty() }?.let {
                it[0].selected = true
                put(it[0])
            }
            put(user)
        }

        fun newCustomer (customer: Customer) {

            val selectedCustomer = selectedCustomer()
            selectedCustomer?.selected = false
            customer.selected = true
            val user = getUser()
            if (selectedCustomer == null) {
                user.customers.add(customer)
                put(user)
            } else {
                user.customers.add(customer)
                put(selectedCustomer)
                put(user)
            }

        }

        fun changeCustomer(customer: Customer) {
            val selectedCustomer = selectedCustomer()
            selectedCustomer?.selected = false
            customer.selected = true
            if (selectedCustomer != null) {
                put(selectedCustomer)
            }
            put(customer)
        }
    }

}
