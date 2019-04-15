package wottrich.github.io.yourdiary.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.Date

@Entity
data class Spending(
    var title: String?,
    var description: String?,
    var price: Double?,
    var date: Date?
) {
    @Id var id: Long = 0
    var selected: Boolean = false
}
