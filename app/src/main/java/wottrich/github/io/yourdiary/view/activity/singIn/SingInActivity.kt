package wottrich.github.io.yourdiary.view.activity.singIn

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sing_in.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.extensions.getString
import wottrich.github.io.yourdiary.firebase.createAccount
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
                    createAccount(etUserEmail.getString(), etUserPassword.getString(),
                        onCreatedAccount = this::onCreateAccount, onSavedAccount = this::onSavedAccount)
                } else {
                    Toast.makeText(this, "Complete todos os campos para continuar", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun onCreateAccount (success: Boolean) {
        if (!success) {
            Toast.makeText(this, "OnCreateAccountError", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onSavedAccount (success: Boolean) {
        if (!success) {
            Toast.makeText(this, "OnSavedAccountError", Toast.LENGTH_SHORT).show()
        } else {
            val message = "Conta criada com sucesso, agora só aproveitar o melhor do aplicativo!"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

}
