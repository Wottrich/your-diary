package wottrich.github.io.yourdiary.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import wottrich.github.io.yourdiary.view.fragments.ClientsFragment
import wottrich.github.io.yourdiary.view.fragments.SpendingFragment

open class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    val spendingFragment: SpendingFragment by lazy {
        SpendingFragment.newInstance()
    }

    val clientFragment: ClientsFragment by lazy {
        ClientsFragment.newInstance()
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> spendingFragment
            else -> clientFragment
        }
    }

    override fun getCount(): Int = 2
}