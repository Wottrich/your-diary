package wottrich.github.io.yourdiary.view.activity.main

import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.User

class MainViewModel {

    var user: User = getUser()

    var isSpendingTab = true
    var isCustomerTab = false

}