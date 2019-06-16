package wottrich.github.io.yourdiary.view.activity

import android.app.Activity
import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.activity_main.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.ViewPagerAdapter
import wottrich.github.io.yourdiary.extensions.box
import wottrich.github.io.yourdiary.extensions.put
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.model.Customer
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.utils.KeyboardUtils
import wottrich.github.io.yourdiary.view.dialog.CustomerDialog
import java.util.*

class MainActivity : BaseActivity(R.layout.activity_main), TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private val viewPagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(this.supportFragmentManager)
    }

    override fun initValues () {

        //toTest()

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

    fun toTest () {

        val newCustumer = Customer("Wottrich")
        put(newCustumer)
        val customer = box<Customer>().get(newCustumer.id)
        val order = Order("Order1", 15.0, Date(), "Varias coisas")
        val order2 = Order("Order 2", 12.0, Date(), "15 crepiocas")
        val order3 = Order("Order 3", 12.0, Date(), "10 crepiocas")
        customer.orders.add(order)
        customer.orders.add(order2)
        customer.orders.add(order3)
        put(customer)
        val orders = box<Customer>().get(newCustumer.id).orders

        for (itemOrder in orders) {
            println(itemOrder.description)
            println(itemOrder.customer.targetId)
        }

        return
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        if (tab?.position == 1) {
            KeyboardUtils.showKeyboard(this, vpFragment)
            vpFragment.postDelayed({
                CustomerDialog {
                    viewPagerAdapter.clientFragment.loadCustomer()
                }.show(this.supportFragmentManager, "CustomerDialog")
            }, 60)
        }
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
                //viewPagerAdapter.clientFragment.loadCustomer()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == UPDATE_ORDER_LIST && resultCode == Activity.RESULT_OK) {
            viewPagerAdapter.clientFragment.loadCustomer()
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        KeyboardUtils.hideKeyboard(this)
        super.onDestroy()
    }

    companion object {
        const val UPDATE_ORDER_LIST = 100
    }
}
