package wottrich.github.io.yourdiary.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import wottrich.github.io.yourdiary.view.fragments.SpendingFragment

open class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    internal val spendingFragment: SpendingFragment by lazy {
        SpendingFragment.newInstance()
    }

    override fun getItem(position: Int): Fragment {
        return spendingFragment
    }

    override fun getCount(): Int = 1
}