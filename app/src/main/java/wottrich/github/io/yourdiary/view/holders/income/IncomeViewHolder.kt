package wottrich.github.io.yourdiary.view.holders.income

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.row_profile_income.view.*
import wottrich.github.io.yourdiary.extensions.format
import wottrich.github.io.yourdiary.extensions.isNotNullOrEmpty
import wottrich.github.io.yourdiary.firebase.gAuth
import wottrich.github.io.yourdiary.model.User

class IncomeViewHolder (var view: View) : RecyclerView.ViewHolder(view) {

    fun initValues(user: User, onClick: () -> Unit) {
        view.tvIncome.text = user.income.format()

        var hasUserLogged = false

        gAuth.currentUser?.let {
            if (it.email.isNotNullOrEmpty()) {
                view.tvLinkedEmail.text = it.email
                hasUserLogged = true
            }
        }

        if(!hasUserLogged) {
            itemView.rootView.setOnClickListener {
                onClick()
            }
        }
    }

}