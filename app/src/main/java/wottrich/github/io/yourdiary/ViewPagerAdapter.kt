package wottrich.github.io.yourdiary

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import wottrich.github.io.yourdiary.fragments.ClientFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return ClientFragment.newInstance()
    }

    override fun getCount(): Int = 3
}