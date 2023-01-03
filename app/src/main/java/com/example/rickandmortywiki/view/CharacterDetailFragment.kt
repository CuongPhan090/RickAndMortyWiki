package com.example.rickandmortywiki.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickandmortywiki.databinding.FragmentCharacterDetailBinding
import com.example.rickandmortywiki.epoxy.uimodel.CharacterDetailsEpoxyController
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailFragment : BaseFragment("Character Detail") {

    private lateinit var binding: FragmentCharacterDetailBinding
    private val viewModel: SharedViewModel by viewModels()
    private val epoxyController = CharacterDetailsEpoxyController(::onClickEpisode)
    val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.charactersDetail.collect { character ->
                    epoxyController.charactersResponse = character
                }
            }
        }
        viewModel.refreshCharacter(args.characterIndex)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onClickEpisode(episodeId: Int?) {
        episodeId?.let {
            findNavController().navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToEpisodeDetailBottomSheetFragment(it))
        }
    }
}