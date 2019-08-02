package wottrich.github.io.yourdiary.view.activity.profile

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_profile.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.ProfileAdapter
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.view.activity.singIn.SingInActivity

class ProfileActivity : BaseActivity(R.layout.activity_profile) {

    private val profileAdapter : ProfileAdapter by lazy {
        ProfileAdapter(this)
    }

    private val viewModel: ProfileActivityViewModel by lazy {
        ProfileActivityViewModel()
    }

    private lateinit var mToolbar: Toolbar

    override fun initValues() {

        profileAdapter.onExpectedIncomeClick = this::onExpectedIncomeClick
        profileAdapter.onLinkedEmailClick = this::onLinkedEmailClick
        rvProfileInfo.adapter = profileAdapter

        mToolbar = toolbar
        mToolbar.title = viewModel.user.name
        mToolbar.subtitle = "${viewModel.user.age} anos"

    }

    fun reloadList (updateNow: Boolean) {
        viewModel.canUpdate {
            if (updateNow || it) {
                profileAdapter.notifyItemChanged(1)
                profileAdapter.notifyItemChanged(2)
                profileAdapter.notifyItemChanged(3)
            }
        }
    }

    private fun onExpectedIncomeClick () {
        Toast.makeText(this, "Em desenvolvimento", Toast.LENGTH_SHORT).show()
    }

    private fun onLinkedEmailClick () {
        startActivity(Intent(this, SingInActivity::class.java))
    }

}
