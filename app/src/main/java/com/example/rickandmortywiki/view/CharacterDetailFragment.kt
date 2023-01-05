package com.example.rickandmortywiki.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickandmortywiki.databinding.FragmentCharacterDetailBinding
import com.example.rickandmortywiki.epoxy.uimodel.CharacterDetailsEpoxyController
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import kotlinx.coroutines.launch

class CharacterDetailFragment : BaseFragment("Character Detail") {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding: FragmentCharacterDetailBinding?
        get() = _binding

    private val viewModel: SharedViewModel by viewModels()
    private val epoxyController = CharacterDetailsEpoxyController(::onClickEpisode)
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterDetailBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characterDetail.collect { character ->
                    epoxyController.characterResponse = character
                }
            }
        }
        viewModel.getCharacter(args.characterId.toString())

        binding?.epoxyRecyclerView?.setControllerAndBuildModels(epoxyController)

        setUpAnimation()
    }


    private fun setUpAnimation() {
         val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val fadeOut = AlphaAnimation(1f, 0f).apply {
                    interpolator = AccelerateInterpolator()
                    duration = 250
                }

                val animation = AnimationSet(false).apply {
                    addAnimation(fadeOut)
                }
                binding?.root?.animation = animation

                findNavController().navigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        val fadeIn = AlphaAnimation(0f, 1f).apply {
            interpolator = DecelerateInterpolator()
            duration = 250
        }

        val animation = AnimationSet(false).apply {
            addAnimation(fadeIn)
        }
        binding?.root?.animation = animation
    }

    private fun onClickEpisode(episodeId: Int?) {
        episodeId?.let {
            findNavController().navigate(
                CharacterDetailFragmentDirections.actionCharacterDetailFragmentToEpisodeDetailBottomSheetFragment(
                    it
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
