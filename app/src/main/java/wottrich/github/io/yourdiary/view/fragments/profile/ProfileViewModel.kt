package wottrich.github.io.yourdiary.view.fragments.profile

import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.User

class ProfileViewModel {

    val user: User
        get() = getUser()

}