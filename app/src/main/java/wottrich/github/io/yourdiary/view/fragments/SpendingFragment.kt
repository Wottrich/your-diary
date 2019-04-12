package wottrich.github.io.yourdiary.view.fragments

import android.annotation.SuppressLint
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_spending.view.*

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.SpendingAdapter
import wottrich.github.io.yourdiary.generics.BaseFragment
import wottrich.github.io.yourdiary.model.Spending
import java.util.*

@SuppressLint("StaticFieldLeak")
open class SpendingFragment : BaseFragment(R.layout.fragment_spending) {

    private var spendingAdapter: SpendingAdapter? = null

    companion object : SpendingFragment() {
        @JvmStatic
        fun newInstance() = SpendingFragment()
    }

    override fun initValues() {
        reload()
        if (spendingAdapter == null) {
            spendingAdapter = SpendingAdapter(
                    arrayListOf(
                            Spending("1", "12daskljkljlkjlkjlkjlkjlkjlkjsdlkfjslkdfjlksdjlskadjaklsjdlkajsdlkasjdklajsdlkasjdkljaslkdjaskldjaskldjalksdjlasjlkasjdlkasjdlajsldkjaskljaslkjaskldjlassjdlkasfklsjdfkljsdflkjskldfjslkdjflksdjf3", 10.0, Date()),
                            Spending("2", "123", 100.0, Date()),
                            Spending("3", "123", 11.0, Date()),
                            Spending("4", "123", 11.0, Date()),
                            Spending("4", "123", 11.0, Date()),
                            Spending("4", "123", 11.0, Date()),
                            Spending("4", "123", 11.0, Date()),
                            Spending("4", "123", 11.0, Date()),
                            Spending("4", "123", 11.0, Date()),
                            Spending("4", "123", 11.0, Date())
                    ),
                    activity ?: return
            )
        }

        baseView.rvSpending.adapter = spendingAdapter
        baseView.rvSpending.setHasFixedSize(true)
    }

    open fun reload() {
        //SpendingDialog(this::onSpending).show(activity?.supportFragmentManager, "SpendingDialog")
    }

    private fun onSpending (spending: Spending) {
        Toast.makeText(activity ?: return, spending.description, Toast.LENGTH_SHORT).show()
    }
}
