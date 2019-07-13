package wottrich.github.io.yourdiary.view.activity.firstAccess

import wottrich.github.io.yourdiary.extensions.isNotNullOrEmpty
import wottrich.github.io.yourdiary.extensions.put
import wottrich.github.io.yourdiary.model.User

class UserRegisterViewModel {

    var canReturn = false
    val user: User = User()

    fun saveNewUser (result: (Boolean) -> Unit) {
        if (user.name.isNotNullOrEmpty() && user.age != null && user.age!! > 0 && user.income > 0) {
            put(user)
            result(true)
        } else result(false)
    }

}