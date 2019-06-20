package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import java.util.*

@Entity
class Order() {
    @Id var id: Long = 0
    var title: String = ""
    var price: Double = 0.0
    var description: String = ""
    var date: Date = Date()

    lateinit var customer: ToOne<Customer>

    @Transient
    var isSelected: Boolean = false

    constructor(title: String, price: Double, date: Date, description: String) : this() {
        this.title = title
        this.price = price
        this.date = date
        this.description = description
    }

}

enum class OrderType(val type: String) {
    NEW("new"),
    EDIT("edit")
}