package com.example.rickandmortywiki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmortywiki.databinding.FragmentListOfCharacterBinding
import com.example.rickandmortywiki.epoxy.uimodel.CharacterListPagingEpoxyController
import com.example.rickandmortywiki.viewmodel.SharedViewModel

class ListOfCharacterFragment : BaseFragment("List of Character") {
    private lateinit var binding: FragmentListOfCharacterBinding
    private val characterListPagingEpoxyController = CharacterListPagingEpoxyController(::onCharacterClick)
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListOfCharacterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.characterListRecyclerView.setController(characterListPagingEpoxyController)

        viewModel.listOfCharacters.observe(viewLifecycleOwner) { pagedList ->
            characterListPagingEpoxyController.submitList(pagedList)
        }
    }

    private fun onCharacterClick(index: Int) {
        val direction = ListOfCharacterFragmentDirections.actionListOfCharacterFragmentToCharacterDetailFragment(index)
        findNavController().navigate(directions = direction)
    }
}
