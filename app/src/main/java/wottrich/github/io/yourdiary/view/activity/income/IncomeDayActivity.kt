package wottrich.github.io.yourdiary.view.activity.income

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.generics.BaseActivity

class IncomeDayActivity : BaseActivity(R.layout.activity_income_day) {

    private val viewModel: IncomeDayViewModel by lazy {
        initViewModelProvider<IncomeDayViewModel>()
    }

    override fun initValues() {

    }


}
