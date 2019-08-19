package wottrich.github.io.yourdiary.view.activity.singIn

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sing_in.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.firebase.MyFirebase
import wottrich.github.io.yourdiary.generics.BaseActivity

class SingInActivity : BaseActivity(R.layout.activity_sing_in), View.OnClickListener {

    val viewModel: SingInViewModel by lazy {
        SingInViewModel()
    }

    override fun initValues() {

        toolbar.setNavigationOnClickListener {
            finish()
        }

        val name =  (viewModel.user.name ?: "").split("/")[0]
        tvWelcome.text = getString(R.string.activity_sing_in_welcome_message, name)

        textWatcher()
        btnContinue.setOnClickListener(this)
    }

    private fun textWatcher () {

        etUserEmail.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.user.email = s.toString()
            }

        })

        etUserPassword.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.password = s.toString()
            }

        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnContinue -> {

                if (etUserEmail.text.isNotEmpty() && etUserPassword.text.isNotEmpty()) {
                    MyFirebase.createAccount(this, etUserEmail.text.toString(), etUserPassword.text.toString()) {
                        if (it) {

                        } else {

                        }
                    }
                } else {
                    Toast.makeText(this, "Complete todos os campos para continuar", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

}
