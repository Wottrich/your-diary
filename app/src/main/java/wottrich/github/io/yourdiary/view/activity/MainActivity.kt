package wottrich.github.io.yourdiary.view.activity

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.ViewPagerAdapter
import wottrich.github.io.yourdiary.generics.BaseActivity

class MainActivity : BaseActivity(R.layout.activity_main), TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private val viewPagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(this.supportFragmentManager)
    }

    override fun initValues () {
        vpFragment.adapter = viewPagerAdapter

        tabLayout.setupWithViewPager(vpFragment)
        tabLayout.addOnTabSelectedListener(this)

        vpFragment.offscreenPageLimit = 2
        vpFragment.addOnPageChangeListener(this)

        tabLayout.getTabAt(0)?.icon = getDrawable(R.drawable.baseline_attach_money_white_36)
        tabLayout.getTabAt(0)?.text = "Gastos"
        tabLayout.getTabAt(1)?.icon = getDrawable(R.drawable.baseline_business_center_white_36)
        tabLayout.getTabAt(1)?.text = "Clientes"
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

    override fun onTabSelected(tab: TabLayout.Tab?) = Unit

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                tabLayout.getTabAt(1)?.text = "Clientes"
                viewPagerAdapter.spendingFragment.reload()
            }
            1 ->  {
                tabLayout.getTabAt(1)?.text = "Adicionar Cliente"
                viewPagerAdapter.clientFragment
            }
        }
    }
}
