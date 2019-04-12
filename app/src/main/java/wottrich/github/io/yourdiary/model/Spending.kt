package wottrich.github.io.yourdiary.model

import java.util.Date

class Spending (
        internal var title: String?,
        internal var description: String?,
        internal var price: Double?,
        internal var date: Date?
) {
        internal var selected: Boolean = false
}
