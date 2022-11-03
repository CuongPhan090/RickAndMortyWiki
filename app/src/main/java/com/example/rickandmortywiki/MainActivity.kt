package com.example.rickandmortywiki

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortywiki.data.model.CharacterDetailsEpoxyController
import com.example.rickandmortywiki.databinding.ActivityMainBinding
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: SharedViewModel by viewModels()
    private val epoxyController = CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.characterDetail.collect { character ->
                epoxyController.characterResponse = character
            }
        }
        viewModel.refreshCharacter(1)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

    }
}

