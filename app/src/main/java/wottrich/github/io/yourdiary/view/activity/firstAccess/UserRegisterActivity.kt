package wottrich.github.io.yourdiary.view.activity.firstAccess

import android.annotation.TargetApi
import android.app.Activity
import android.app.KeyguardManager
import android.content.Intent
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_user_register.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.*
import wottrich.github.io.yourdiary.generics.BaseActivity
import wottrich.github.io.yourdiary.utils.CurrencyUtils
import wottrich.github.io.yourdiary.view.activity.MainActivity

class UserRegisterActivity : BaseActivity(R.layout.activity_user_register), View.OnClickListener, KeyguardManager.OnKeyguardExitResult {

    val RETURN_FINGERPRINT = 300

    private val viewModel: UserRegisterViewModel by lazy {
        UserRegisterViewModel()
    }

    override fun initValues() {
        btnContinue.setOnClickListener(this)
        toolbar.setNavigationOnClickListener {
            reloadView()
        }
        onChanged()
    }

    private fun onChanged () {

        etUserIncome.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                etUserIncome.removeTextChangedListener(this)
                etUserIncome setText  CurrencyUtils.formatToLocale(s.toString(), withoutSymbol = true)
                etUserIncome.setSelection(etUserIncome.text.length)
                etUserIncome.addTextChangedListener(this)
                if (etUserIncome.text.isEmpty()) {
                    viewModel.user.income = 0.0
                } else {
                    viewModel.user.income = convertToDouble(etUserIncome.getString())
                }
            }

        })

        etUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.user.name = s.toString()
            }

        })

        etUserAge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.user.age = s.toString().toInt()
            }
        })

        cbLockApp.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.user.lockApp = isChecked
        }

    }

    /**
     * Used to reload edit text rules
     * When [UserRegisterViewModel.canReturn] is false we made visible [etUserName] and [etUserAge]
     * and when is true we made visible only [etUserIncome] and if user go back canReturn will false again
     */
    private fun reloadView () {
        if (!viewModel.canReturn) {// When user name and age was completed
            tvWelcome.text = getString(R.string.activity_user_register_why_income)
            tvMessage.text = getString(R.string.activity_user_register_because_income)
            etUserName.visibility = View.GONE
            etUserAge.visibility = View.GONE
            etUserIncome.visibility = View.VISIBLE
            tvSymbol.visibility = View.VISIBLE
            cbLockApp.visibility = View.VISIBLE
            toolbar.navigationIcon = getDrawable(R.drawable.arrow_back_white_24)
            btnContinue.text = getString(R.string.activity_user_register_btn_finish)
            viewModel.canReturn = true
        } else { // Default or when someone return to this page
            tvWelcome.text = getString(R.string.activity_user_register_welcome_message)
            tvMessage.text = getString(R.string.activity_user_register_insert_data)
            etUserName.visibility = View.VISIBLE
            etUserAge.visibility = View.VISIBLE
            etUserIncome.visibility = View.GONE
            tvSymbol.visibility = View.GONE
            cbLockApp.visibility = View.GONE
            toolbar.navigationIcon = null
            btnContinue.text = getString(R.string.activity_user_register_btn_continue)
            viewModel.canReturn = false
        }
    }

    private fun firstFormCompleted () = etUserName.text.trim().isNotEmpty() && etUserAge.text.isNotEmpty()

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.btnContinue -> {

                if (!viewModel.canReturn) {

                    if (firstFormCompleted()) {
                        if (etUserAge.text.toString().toInt() > 0)
                            reloadView()
                        else {
                            Toast.makeText(this, getString(R.string.activity_user_register_age_0), Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.activity_user_register_fill_all_fields), Toast.LENGTH_SHORT).show()
                    }

                } else if (etUserIncome.text.isNotEmpty()) {
                    if (convertToDouble(etUserIncome.getString(), _locale) <= 0) {
                        Toast.makeText(this, getString(R.string.activity_user_register_income_0), Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (cbLockApp.isChecked) {
                        startActivityForResult(intentLockActivity("Confirme sua senha!"), RETURN_FINGERPRINT)
                    } else {
                        viewModel.saveNewUser {
                            if (it) {
                                startActivity(Intent(this, MainActivity::class.java))
                            } else {
                                Toast.makeText(this, getString(R.string.activity_user_register_error), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, getString(R.string.activity_user_register_fill_all_fields), Toast.LENGTH_SHORT).show()
                }

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RETURN_FINGERPRINT -> {
                    viewModel.saveNewUser {
                        if (it) {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(this, getString(R.string.activity_user_register_error), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        if (viewModel.canReturn) {
            reloadView()
        } else super.onBackPressed()
    }

    override fun onKeyguardExitResult(success: Boolean) {
        print("success")
    }

}
