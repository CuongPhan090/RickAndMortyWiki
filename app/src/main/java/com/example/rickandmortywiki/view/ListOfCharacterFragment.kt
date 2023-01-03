package com.example.rickandmortywiki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmortywiki.R
import com.example.rickandmortywiki.databinding.FragmentListOfCharacterBinding
import com.example.rickandmortywiki.epoxy.uimodel.CharacterListPagingEpoxyController
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import com.google.android.material.navigation.NavigationView

class ListOfCharacterFragment : BaseFragment("List of Character") {
    private lateinit var binding: FragmentListOfCharacterBinding
    private val characterListPagingEpoxyController =
        CharacterListPagingEpoxyController(::onCharacterClick)
    private val viewModel: SharedViewModel by viewModels()
    private lateinit var navView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListOfCharacterBinding.inflate(layoutInflater)
        navView = requireActivity().findViewById(R.id.nav_view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.characterListRecyclerView.setController(characterListPagingEpoxyController)

        viewModel.listOfCharacters.observe(viewLifecycleOwner) { pagedList ->
            characterListPagingEpoxyController.submitList(pagedList)
        }

        val onBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
                if (drawerLayout.isDrawerOpen(navView)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    requireActivity().finish()
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressed)
        navView.menu.findItem(R.id.listOfCharacterFragment).isChecked = true
    }

    private fun onCharacterClick(index: Int) {
        val direction =
            ListOfCharacterFragmentDirections.actionListOfCharacterFragmentToCharacterDetailFragment(
                index
            )
        findNavController().navigate(directions = direction)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navView.menu.findItem(R.id.listOfCharacterFragment).isChecked = false
    }
}
