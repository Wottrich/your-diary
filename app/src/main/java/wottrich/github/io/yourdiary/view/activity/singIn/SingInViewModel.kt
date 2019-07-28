package wottrich.github.io.yourdiary.view.activity.singIn

import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.User

class SingInViewModel {

    val user: User
        get() = getUser()

    var password: String = ""

}