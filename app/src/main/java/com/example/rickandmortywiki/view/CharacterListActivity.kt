package com.example.rickandmortywiki.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.rickandmortywiki.data.model.CharacterListPagingEpoxyController
import com.example.rickandmortywiki.databinding.ActivityCharacterListBinding
import com.example.rickandmortywiki.viewmodel.SharedViewModel

class CharacterListActivity : AppCompatActivity() {
    private val characterListPagingEpoxyController = CharacterListPagingEpoxyController()
    private val viewModel: SharedViewModel by viewModels()
    private lateinit var binding: ActivityCharacterListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.characterListRecyclerView.setController(characterListPagingEpoxyController)

        viewModel.listOfCharacters.observe(this) { pagedList ->
            characterListPagingEpoxyController.submitList(pagedList)
        }
    }
}
