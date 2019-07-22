package wottrich.github.io.yourdiary.view.fragments.profile

import android.support.v7.widget.Toolbar
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_profile.view.*
import wottrich.github.io.yourdiary.R
import wottrich.github.io.yourdiary.adapter.ProfileAdapter
import wottrich.github.io.yourdiary.generics.BaseFragment

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    private val profileAdapter : ProfileAdapter by lazy {
        ProfileAdapter(requireActivity())
    }

    private val viewModel: ProfileViewModel by lazy {
        ProfileViewModel()
    }

    lateinit var toolbar: Toolbar

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    override fun initValues() {
        profileAdapter.onExpectedIncomeClick = this::onExpectedIncomeClick
        baseView.rvProfileInfo.adapter = profileAdapter

        toolbar = baseView.toolbar
        toolbar.title = viewModel.user.name
        toolbar.subtitle = "${viewModel.user.age} anos"

    }

    fun reloadList () {
        profileAdapter.notifyItemChanged(1)
        profileAdapter.notifyItemChanged(2)
    }

    private fun onExpectedIncomeClick () {
        Toast.makeText(requireContext(), "Em desenvolvimento", Toast.LENGTH_SHORT).show()
    }

}