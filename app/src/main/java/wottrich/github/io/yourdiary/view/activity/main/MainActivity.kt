//package wottrich.github.io.yourdiary.view.activity.main
//
//
//class MainActivity : BaseActivity(R.layout.activity_main), TabLayout.OnTabSelectedListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener {
//
//    private val viewModel: MainViewModel by lazy {
//        MainViewModel()
//    }
//
//    private val viewPagerAdapter: ViewPagerAdapter by lazy {
//        ViewPagerAdapter(this.supportFragmentManager)
//    }
//
//    override fun initValues () {
//
//        //toTest()
//
//        vpFragment.adapter = viewPagerAdapter
//
//        tabLayout.setupWithViewPager(vpFragment)
//        tabLayout.addOnTabSelectedListener(this)
//
//        vpFragment.offscreenPageLimit = 3
//        vpFragment.addOnPageChangeListener(this)
//
//        tabLayout.getTabAt(0)?.icon = getDrawable(R.drawable.baseline_attach_money_white_36)
//        tabLayout.getTabAt(0)?.text = "Gastos"
//        tabLayout.getTabAt(1)?.icon = getDrawable(R.drawable.baseline_business_center_white_36)
//        tabLayout.getTabAt(1)?.text = "Clientes"
//        tabLayout.getTabAt(2)?.icon = getDrawable(R.drawable.sharp_perm_identity_white_36)
//        tabLayout.getTabAt(2)?.text = "Perfil"
//    }
//
//    override fun onTabReselected(tab: TabLayout.Tab?) = Unit
//
//    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
//
//    override fun onTabSelected(tab: TabLayout.Tab?) = Unit
//
//    override fun onPageScrollStateChanged(state: Int) = Unit
//
//    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit
//
//    /**
//     * Control what tab was select and control play/stop animations
//     * @param position: 1 - Spending | 2 - Customer | 3 - Profile
//     */
//    override fun onPageSelected(position: Int) {
//        viewModel.isSpendingTab =  position == 0
//        viewModel.isCustomerTab =  position == 1
//        viewModel.isProfileTab   =  position == 2
//        viewPagerAdapter.spendingFragment.playAnimation(position == 0)
//        viewPagerAdapter.customerFragment.playAnimation(position == 1)
//
//        if (position == 2) {
//            viewPagerAdapter.profileFragment.reloadList(false)
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//
//        if (resultCode == Activity.RESULT_OK) {
//            when (requestCode) {
//                UPDATE_ORDER_LIST -> {
//                    viewPagerAdapter.profileFragment.reloadList(true)
//                    viewPagerAdapter.customerFragment.loadCustomer()
//                }
//                UPDATE_SPENDING_LIST -> {
//                    viewPagerAdapter.profileFragment.reloadList(true)
//                    viewPagerAdapter.spendingFragment.reload()
//                }
//            }
//        }
//
//        super.onActivityResult(requestCode, resultCode, data)
//    }
//
//    override fun onBackPressed() {
//        if (viewModel.isSpendingTab && viewPagerAdapter.spendingFragment.viewModel.selectedSpending.isNotEmpty()) {
//            viewPagerAdapter.spendingFragment.cleanSelectedItems()
//            return
//        } else if (viewModel.isCustomerTab && viewPagerAdapter.customerFragment.viewModel.ordersSelected.isNotEmpty()) {
//            viewPagerAdapter.customerFragment.cleanSelectedItems()
//            return
//        } else super.onBackPressed()
//    }
//
//    override fun onDestroy() {
//        KeyboardUtils.hideKeyboard(this, root)
//        super.onDestroy()
//    }
//
//    companion object {
//        const val UPDATE_ORDER_LIST = 100
//        const val UPDATE_SPENDING_LIST = 200
//    }
//}
