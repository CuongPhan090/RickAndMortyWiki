package com.example.rickandmortywiki.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rickandmortywiki.databinding.FragmentCharacterSearchBinding
import com.example.rickandmortywiki.epoxy.uimodel.CharacterSearchEpoxyController
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterSearchFragment : BaseFragment("Search Character") {

    private var _binding: FragmentCharacterSearchBinding? = null
    private val binding
        get() = _binding

    private var currentText: String = ""
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val sharedViewModel: SharedViewModel by viewModels()
    private val epoxyController = CharacterSearchEpoxyController(::onCharacterClick)

    private val runnable: Runnable = Runnable {
        sharedViewModel.submitQuery(currentText)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterSearchBinding.inflate(inflater)
        return binding?.root
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.searchEditText?.doAfterTextChanged {
            it?.let { text ->
                currentText = text.toString()
                // Remove runnable object from the handler queue
                handler.removeCallbacks(runnable)
                // Add Runnable object to the handler queue
                handler.postDelayed(runnable, 500)
            }
        }

        binding?.searchEpoxyController?.setControllerAndBuildModels(epoxyController)

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.localExceptionEvent.collectLatest { event ->
                event.getContent()?.let {
                    epoxyController.localException = it
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.searchCharacterPagination.collect {
                epoxyController.localException = null
                epoxyController.submitData(it)
            }
        }
    }

    private fun onCharacterClick(id: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}