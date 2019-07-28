package wottrich.github.io.yourdiary.generics

import android.support.v7.widget.RecyclerView
import android.view.View
import wottrich.github.io.yourdiary.extensions.getUser
import wottrich.github.io.yourdiary.model.User

abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val user: User
        get() = getUser()

    open fun initValues () {}


    open fun initValues (user: User) {}

}