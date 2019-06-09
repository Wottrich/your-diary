package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Order() {
    @Id var id: Long = 0
    var title: String = ""
    var price: Double = 0.0
    var description: String = ""

    lateinit var customer: ToOne<Customer>

    constructor(price: Double, description: String) : this() {
        this.price = price
        this.description = description
    }


}