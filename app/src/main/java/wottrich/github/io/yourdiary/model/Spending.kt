package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne
import java.util.Date

@Entity
class Spending() {
    @Id var id: Long = 0

    var title: String? = null
    var description: String? = null
    var price: Double? = null
    var date: Date? = Date()

    @Transient
    var isSelected: Boolean = false

    lateinit var user: ToOne<User>

    constructor(title: String?, description: String?, price: Double?, date: Date?) : this() {
        this.title = title
        this.description = description
        this.price = price
        this.date = date
    }

}
