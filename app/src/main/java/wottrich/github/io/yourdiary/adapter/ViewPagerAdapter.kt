package wottrich.github.io.yourdiary.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.view.fragments.customer.CustomerFragment
import wottrich.github.io.yourdiary.view.fragments.profile.ProfileFragment
import wottrich.github.io.yourdiary.view.fragments.spending.SpendingFragment

open class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

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

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> spendingFragment
            1 -> customerFragment
            else -> profileFragment
        }
    }

    override fun getCount(): Int = 3
}