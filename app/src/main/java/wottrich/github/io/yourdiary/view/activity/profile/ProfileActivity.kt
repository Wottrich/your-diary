package wottrich.github.io.yourdiary.view.activity.profile

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_profile.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.ProfileAdapter
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.view.activity.profile.flows.customer.CustomerActivity
import wottrich.github.io.yourdiary.view.activity.profile.flows.spend.SpendActivity
import androidx.constraintlayout.widget.ConstraintLayout
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.model.Order
import wottrich.github.io.yourdiary.model.Spending
import wottrich.github.io.yourdiary.utils.CurrencyUtils
import wottrich.github.io.yourdiary.utils.KeyboardUtils
import wottrich.github.io.yourdiary.view.activity.income.IncomeDayActivity
import wottrich.github.io.yourdiary.view.dialog.SelectCustomerDialog
import java.util.*

class ProfileActivity : BaseActivity(R.layout.activity_profile) {

    private val updateLinkedEmailCode = 500
    private val updateIncomeCode = 700
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
        //profileAdapter.onLinkedEmailClick = this::onLinkedEmailClick
        profileAdapter.onIncomeClick = this::onIncomeClick
        profileAdapter.onCustomerClick = this::onCustomerClick
        profileAdapter.onSpendClick = this::onSpendClick
        rvProfileInfo.adapter = profileAdapter

        bottomSheetConfig()
        textWatcher()

        mToolbar = toolbar
        mToolbar.title = viewModel.user.name
        mToolbar.subtitle = "${viewModel.user.age} anos"
        hasSomeoneCustomer()

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

            KeyboardUtils.hideKeyboard(this, btnSheet)

            Handler().postDelayed({
                if (sheet.state == BottomSheetBehavior.STATE_EXPANDED) {
                    sheet.state = BottomSheetBehavior.STATE_COLLAPSED
                } else {
                    sheet.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }, 200)



        }

        imgOK.setOnClickListener {

            newOrder()

        }

    }

    private fun newOrder () {

        if (etTitle.text == null || etTitle.text.toString().isEmpty()
            || etPrice.text == null || etPrice.text.toString().isEmpty()
            || convertToDouble(etPrice.text.toString(), _locale) == 0.toDouble()) {
            Toast.makeText(this, "Preencha todos os campos para continuar ou um valor maior que 0", Toast.LENGTH_LONG).show()
            return
        }

        val title = etTitle.text.toString()
        val date = Date()
        val price = convertToDouble(etPrice.text.toString(), _locale)

        if (rbSpend.isChecked) {
            cleanBottomSheet()
            val user = viewModel.user
            val spend = Spending()
            spend.title = title
            spend.date = date
            spend.price = price
            user.spendingList.add(spend)
            put(user)
            Toast.makeText(this, "Gasto registrado com sucesso", Toast.LENGTH_SHORT).show()
            profileAdapter.notifyDataSetChanged()
        } else if (rbOrder.isChecked) {

            SelectCustomerDialog {
                cleanBottomSheet()
                val order = Order()
                order.title = title
                order.date = date
                order.price = price
                it.orders.add(order)
                put(it)
                Toast.makeText(this, "Pedido registrado com sucesso no cliente ${it.name}", Toast.LENGTH_SHORT).show()
                profileAdapter.notifyDataSetChanged()
            }.show(this.supportFragmentManager, "SelectCustomerDialog")

        }

    }

    private fun textWatcher () {

        etPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etPrice.removeTextChangedListener(this)
                etPrice setText CurrencyUtils.formatToLocale(s.toString(), withoutSymbol = true)
                etPrice.setSelection(etPrice.text.length)
                etPrice.addTextChangedListener(this)
            }

        })

    }

    private fun cleanBottomSheet() {
        KeyboardUtils.hideKeyboard(this, btnSheet)
        sheet.state = BottomSheetBehavior.STATE_COLLAPSED
        etTitle.setText("")
        etPrice.setText("")
        rbSpend.isChecked = true
        rbOrder.isChecked = false

    }

    private fun onExpectedIncomeClick () {
        Toast.makeText(this, "Em desenvolvimento", Toast.LENGTH_SHORT).show()
    }

    private fun onLinkedEmailClick () {
        //startMyActivity(SingInActivity::class, updateLinkedEmailCode)
    }

    private fun onIncomeClick () {
        startMyActivity(IncomeDayActivity::class, updateIncomeCode)
    }

    private fun onCustomerClick () {
        startMyActivity(CustomerActivity::class, updateSelectedCustomerCode)
    }

    private fun onSpendClick () {
        startMyActivity(SpendActivity::class, updateProfileListCode)
    }

    private fun hasSomeoneCustomer() {
        if (viewModel.user.customers.isEmpty()) {
            rbOrder.visibility = View.GONE
        } else {
            rbOrder.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                updateSelectedCustomerCode, updateProfileListCode -> {
                    hasSomeoneCustomer()
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
