package wottrich.github.io.yourdiary.view.fragments


import android.annotation.SuppressLint

import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.generics.BaseFragment

@SuppressLint("StaticFieldLeak")
open class ClientsFragment : BaseFragment(R.layout.fragment_clients) {


    companion object : ClientsFragment() {
        @JvmStatic
        fun newInstance() = ClientsFragment()
    }

    override fun initValues() {

    }


}
