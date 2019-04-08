package wottrich.github.io.yourdiary.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import wottrich.github.io.yourdiary.R

class ClientFragment : Fragment() {

    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_client, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ClientFragment().apply {

        }
    }
}
