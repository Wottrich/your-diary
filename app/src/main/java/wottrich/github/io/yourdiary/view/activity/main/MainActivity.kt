package wottrich.github.io.yourdiary.view.activity.main

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
import java.util.*

class MainActivity : BaseActivity(R.layout.activity_main), TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private val viewModel: MainViewModel by lazy {
        MainViewModel()
    }

    private val viewPagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(this.supportFragmentManager, viewModel.user)
    }

    override fun initValues () {

        //toTest()

        vpFragment.adapter = viewPagerAdapter

        tabLayout.setupWithViewPager(vpFragment)
        tabLayout.addOnTabSelectedListener(this)

        vpFragment.offscreenPageLimit = 3
        vpFragment.addOnPageChangeListener(this)

        tabLayout.getTabAt(0)?.icon = getDrawable(R.drawable.baseline_attach_money_white_36)
        tabLayout.getTabAt(0)?.text = "Gastos"
        tabLayout.getTabAt(1)?.icon = getDrawable(R.drawable.baseline_business_center_white_36)
        tabLayout.getTabAt(1)?.text = "Clientes"
        tabLayout.getTabAt(2)?.icon = getDrawable(R.drawable.sharp_perm_identity_white_36)
        tabLayout.getTabAt(2)?.text = "Perfil"
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

    override fun onTabReselected(tab: TabLayout.Tab?) = Unit

    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

    override fun onTabSelected(tab: TabLayout.Tab?) = Unit

    override fun onPageScrollStateChanged(state: Int) = Unit

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

    /**
     * Control what tab was select and control play/stop animations
     * @param position: 1 - Spending | 2 - Customer | 3 - Profile
     */
    override fun onPageSelected(position: Int) {
        viewModel.isSpendingTab =  position == 0
        viewModel.isCustomerTab =  position == 1
        viewModel.isProfileTab   =  position == 2
        viewPagerAdapter.spendingFragment.playAnimation(position == 0)
        viewPagerAdapter.customerFragment.playAnimation(position == 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                UPDATE_ORDER_LIST -> viewPagerAdapter.customerFragment.loadCustomer()
                UPDATE_SPENDING_LIST -> viewPagerAdapter.spendingFragment.reload()
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (viewModel.isSpendingTab && viewPagerAdapter.spendingFragment.viewModel.selectedSpending.isNotEmpty()) {
            viewPagerAdapter.spendingFragment.cleanSelectedItems()
            return
        } else if (viewModel.isCustomerTab && viewPagerAdapter.customerFragment.viewModel.ordersSelected.isNotEmpty()) {
            viewPagerAdapter.customerFragment.cleanSelectedItems()
            return
        } else super.onBackPressed()
    }

    override fun onDestroy() {
        KeyboardUtils.hideKeyboard(this, root)
        super.onDestroy()
    }

    companion object {
        const val UPDATE_ORDER_LIST = 100
        const val UPDATE_SPENDING_LIST = 200
    }
}
