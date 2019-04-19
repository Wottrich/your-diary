package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class Order {
    @Id var id: Long = 0
    var price: Double? = null
    var description: String = ""

    lateinit var customer: ToOne<Customer>
}