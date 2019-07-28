package wottrich.github.io.yourdiary.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.view.fragments.customer.CustomerFragment
import wottrich.github.io.yourdiary.view.fragments.profile.ProfileFragment
import wottrich.github.io.yourdiary.view.fragments.spending.SpendingFragment

open class ViewPagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {

    val spendingFragment: SpendingFragment by lazy {
        SpendingFragment.newInstance()
    }

    val customerFragment: CustomerFragment by lazy {
        CustomerFragment.newInstance()
    }

    val profileFragment: ProfileFragment by lazy {
        ProfileFragment.newInstance()
    }

    //val profile

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return when (position) {
            0 -> spendingFragment
            1 -> customerFragment
            else -> profileFragment
        }
    }

    override fun getCount(): Int = 3
}