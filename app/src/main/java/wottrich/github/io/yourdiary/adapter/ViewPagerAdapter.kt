package wottrich.github.io.yourdiary.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import wottrich.github.io.yourdiary.model.User
import wottrich.github.io.yourdiary.view.fragments.customer.CustomerFragment
import wottrich.github.io.yourdiary.view.fragments.spending.SpendingFragment

open class ViewPagerAdapter(fm: FragmentManager, val user: User) : FragmentPagerAdapter(fm) {

    val spendingFragment: SpendingFragment by lazy {
        SpendingFragment.newInstance(user)
    }

    val clientFragment: CustomerFragment by lazy {
        CustomerFragment.newInstance(user)
    }

    val clientFragment2: CustomerFragment by lazy {
        CustomerFragment.newInstance(user)
    }

    //val profile

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> spendingFragment
            1 -> clientFragment
            else -> clientFragment2
        }
    }

    override fun getCount(): Int = 2
}