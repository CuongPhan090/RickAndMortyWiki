package com.example.rickandmortywiki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.VisibleForTesting
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.FragmentListOfCharacterBinding
import com.example.rickandmortywiki.epoxy.controller.CharacterListPagingEpoxyController
import com.example.rickandmortywiki.repository.SharedRepository
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import com.example.rickandmortywiki.viewmodel.SharedViewModelFactory
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier

class ListOfCharacterFragment : BaseFragment("List of Character") {

    private var _binding: FragmentListOfCharacterBinding? = null
    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    val binding: FragmentListOfCharacterBinding?
        get() = _binding

    private val characterListPagingEpoxyController =
        CharacterListPagingEpoxyController(::onCharacterClick)
    private val viewModel: SharedViewModel by viewModels{ SharedViewModelFactory(SharedRepository()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOfCharacterBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.characterListRecyclerView?.setControllerAndBuildModels(characterListPagingEpoxyController)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.charactersPagination.collect { pagedList ->
                    characterListPagingEpoxyController.showShimmering = false
                    characterListPagingEpoxyController.submitData(pagedList)
                }
            }
        }
    }

    @VisibleForTesting(otherwise = Modifier.PRIVATE)
    fun onCharacterClick(index: Int) {
        val direction =
            ListOfCharacterFragmentDirections.actionListOfCharacterFragmentToCharacterDetailFragment(
                index
            )
        findNavController().navigate(directions = direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
