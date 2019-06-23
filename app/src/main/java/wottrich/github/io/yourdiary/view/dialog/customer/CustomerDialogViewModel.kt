package wottrich.github.io.yourdiary.view.dialog.customer

import wottrich.github.io.yourdiary.extensions.boxList
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.CustomerType

class CustomerDialogViewModel(var type: CustomerType?, var id: Long?) {

    val customer: Customer? = boxList<Customer>().find { it.id == id }

}