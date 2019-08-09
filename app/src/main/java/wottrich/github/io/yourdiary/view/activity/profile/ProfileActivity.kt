package wottrich.github.io.yourdiary.view.activity.profile

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_profile.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.ProfileAdapter
import wottrich.github.io.yourdiary.extensions.startMyActivity
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.view.activity.profile.flows.customer.CustomerActivity
import wottrich.github.io.yourdiary.view.activity.profile.flows.spend.SpendActivity
import wottrich.github.io.yourdiary.view.activity.singIn.SingInActivity
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout

class ProfileActivity : BaseActivity(R.layout.activity_profile) {

    private val updateLinkedEmailCode = 500
    private val updateSelectedCustomerCode = 600
    private val updateProfileListCode = 400

    private val profileAdapter : ProfileAdapter by lazy {
        ProfileAdapter(this)
    }

    private val viewModel: ProfileActivityViewModel by lazy {
        ProfileActivityViewModel()
    }

    private lateinit var mToolbar: Toolbar
    private lateinit var sheet: BottomSheetBehavior<*>

    override fun initValues() {

        profileAdapter.onExpectedIncomeClick = this::onExpectedIncomeClick
        profileAdapter.onLinkedEmailClick = this::onLinkedEmailClick
        profileAdapter.onCustomerClick = this::onCustomerClick
        profileAdapter.onSpendClick = this::onSpendClick
        rvProfileInfo.adapter = profileAdapter

        bottomSheetConfig()

        mToolbar = toolbar
        mToolbar.title = viewModel.user.name
        mToolbar.subtitle = "${viewModel.user.age} anos"

    }

    private fun bottomSheetConfig () {

        sheet = BottomSheetBehavior.from(btnSheet)

        sheet.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) = Unit
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

                val params = imgArrow.layoutParams as ConstraintLayout.LayoutParams
                params.horizontalBias = (1f-slideOffset) * 0.5f
                imgArrow.layoutParams = params

                imgArrow.rotation = slideOffset * 180f + 90f
                imgOK.alpha = slideOffset
                viewHeader.alpha = slideOffset
            }
        })

        imgArrow.setOnClickListener {

            if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) {
                sheet.state = BottomSheetBehavior.STATE_COLLAPSED
            } else {
                sheet.state = BottomSheetBehavior.STATE_EXPANDED
            }

        }

        imgOK.setOnClickListener {

            newOrder()

        }

    }

    private fun newOrder () {

        if (rbSpend.isChecked) {



        } else if (rbOrder.isChecked) {



        }

    }

    fun reloadList (updateNow: Boolean) {
        viewModel.canUpdate {
            if (updateNow || it) {
                profileAdapter.notifyItemChanged(1)
                profileAdapter.notifyItemChanged(2)
                profileAdapter.notifyItemChanged(3)
            }
        }
    }

    private fun onExpectedIncomeClick () {
        Toast.makeText(this, "Em desenvolvimento", Toast.LENGTH_SHORT).show()
    }

    private fun onLinkedEmailClick () {
        startMyActivity(SingInActivity::class, updateLinkedEmailCode)
    }

    private fun onCustomerClick () {
        startMyActivity(CustomerActivity::class, updateSelectedCustomerCode)
    }

    private fun onSpendClick () {
        startMyActivity(SpendActivity::class, updateProfileListCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                updateSelectedCustomerCode, updateProfileListCode -> {
                    profileAdapter.notifyDataSetChanged()
                }

                updateLinkedEmailCode -> {
                    profileAdapter.notifyItemChanged(0)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}
