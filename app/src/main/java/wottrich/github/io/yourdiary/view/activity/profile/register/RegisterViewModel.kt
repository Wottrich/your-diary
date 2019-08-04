package wottrich.github.io.yourdiary.view.activity.profile.register

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.enumerators.RegisterType
import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.extensions.isNotNullOrEmpty
import wottrich.github.io.yourdiary.extensions.put
import wottrich.github.io.yourdiary.model.*
import java.util.*

class RegisterViewModel() {

    //Objects
    var order: Order? = null
    var spending: Spending? = null

    //Id's
    private var orderId: Long = -1
    private var spendingId: Long = -1

    private var userId: Long = -1

    //validates
    var isSpending: Boolean = false
    var type: RegisterType = RegisterType.NEW

    // Order Constructor
    constructor(
        userId: Long,
        orderId: Long,
        type: RegisterType,
        isSpending: Boolean = false
    ) : this() {
        this.userId = userId
        this.orderId = orderId
        this.type = type
        this.isSpending = isSpending

        fun newOrder() {
            if (order == null) {
                order = Order()
            }
        }

        if (orderId != -1L) {
            order = box<Order>().get(orderId)
            newOrder()
        } else {
            newOrder()
        }
    }

    // Spending Constructor
    constructor(spendingId: Long, type: RegisterType, isSpending: Boolean = true) : this() {
        this.spendingId = spendingId
        this.type = type
        this.isSpending = isSpending

        fun newSpending() {
            if (spending == null) {
                spending = Spending()
            }
        }

        if (spendingId != -1L) {
            spending = box<Spending>().get(spendingId)
            newSpending()
        } else {
            newSpending()
        }
    }

    fun saveData(result: (message: Int?, success: Boolean) -> Unit) {
        when (type) {
            RegisterType.EDIT -> {
                if (isSpending) {
                    if (spending?.title.isNotNullOrEmpty() || spending?.description.isNotNullOrEmpty()) {
                        put(spending)
                        result(null, true)
                    } else {
                        result(R.string.activity_register_name_description_empty, false)
                    }
                } else {
                    if (order?.title.isNotNullOrEmpty() || order?.description.isNotNullOrEmpty()) {
                        put(order)
                        result(null, true)
                    } else {
                        result(R.string.activity_register_name_description_empty, false)
                    }
                }
            }
            RegisterType.NEW -> {
                val user = getUser()
                if (isSpending) {
                    if (spending != null && (spending?.description.isNotNullOrEmpty() || spending?.title.isNotNullOrEmpty())) {
                        user.spendingList.add(spending!!)
                        put(user)
                        result(null, true)
                    }
                } else {
                    if (order != null && (order?.description.isNotNullOrEmpty() || order?.title.isNotNullOrEmpty())) {
                        val customer = user.customers.getById(userId)//box<Customer>().get(userId)
                        customer.orders.add(order!!)
                        put(customer)
                        result(null, true)
                    }
                }
            }
        }
    }

    fun getTitle(): String {
        return (if (isSpending) spending?.title else order?.title) ?: ""
    }

    fun getDescription(): String {
        return (if (isSpending) spending?.description else order?.description) ?: ""
    }

    fun getPrice(): Double {
        return (if (isSpending) spending?.price else order?.price) ?: 0.0
    }

    fun getDate(): Date {
        return (if (isSpending) spending?.date else order?.date) ?: Date()
    }

    fun changeTitle(title: String) {
        if (isSpending) spending?.title = title else order?.title = title
    }

    fun changeDescription(description: String) {
        if (isSpending) spending?.description = description else order?.description = description
    }

    fun changePrice(price: Double) {
        if (isSpending) spending?.price = price else order?.price = price
    }

    fun changeDate(date: Date) {
        if (isSpending) spending?.date = date else order?.date = date
    }


}