package com.example.playground

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.koin.android.scope.ScopeFragment
import org.koin.android.scope.lifecycleScope
import org.koin.core.parameter.parametersOf

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : ScopeFragment(), WelcomeContract.View {

    override val presenter: WelcomeContract.Presenter by inject {
        parametersOf(this)
    }

    lateinit var textView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textView = view.findViewById(R.id.textview_first)
        presenter.fetchServers("1")
    }

    override fun onError(errorMessage: String) {
        textView.text = errorMessage
    }

    override fun onSuccess(string: String) {
        textView.text = string
    }


    override fun isActive(): Boolean {
        return isVisible
    }

    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }
}