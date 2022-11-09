package com.example.rickandmortywiki.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.rickandmortywiki.epoxy.uimodel.CharacterListPagingEpoxyController
import com.example.rickandmortywiki.databinding.ActivityCharacterListBinding
import com.example.rickandmortywiki.util.Constant
import com.example.rickandmortywiki.viewmodel.SharedViewModel

class CharacterListActivity : AppCompatActivity() {
    private val characterListPagingEpoxyController = CharacterListPagingEpoxyController(::onCharacterClick)
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

    private fun onCharacterClick(index: Int) {
        val intent = Intent(this@CharacterListActivity, CharacterDetailActivity::class.java).apply {
            putExtra(Constant.CHARACTER_ID, index)
        }
        startActivity(intent)
    }
}
