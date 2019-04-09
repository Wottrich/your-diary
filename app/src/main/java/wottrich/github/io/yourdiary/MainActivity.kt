package wottrich.github.io.yourdiary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import wottrich.github.io.yourdiary.adapter.ViewPagerAdapter

class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private val viewPagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(this.supportFragmentManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initValues()
    }

    private fun initValues () {
        vpFragment.adapter = viewPagerAdapter

        tabLayout.setupWithViewPager(vpFragment)
        tabLayout.addOnTabSelectedListener(this)

        vpFragment.offscreenPageLimit = 1
        vpFragment.addOnPageChangeListener(this)
        tabLayout.getTabAt(0)?.icon = getDrawable(R.drawable.add_circle_outline_white)
    }

    override fun onTabReselected(tab: TabLayout.Tab?) = Unit

    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

    override fun onTabSelected(tab: TabLayout.Tab?) = Unit

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> viewPagerAdapter.spendingFragment.reload()
        }
    }
}
