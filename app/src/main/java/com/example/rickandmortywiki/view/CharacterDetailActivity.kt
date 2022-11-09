package com.example.rickandmortywiki.view

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmortywiki.epoxy.uimodel.CharacterDetailsEpoxyController
import com.example.rickandmortywiki.databinding.ActivityCharacterDetailBinding
import com.example.rickandmortywiki.util.Constant
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityCharacterDetailBinding
    private val viewModel: SharedViewModel by viewModels()
    private val epoxyController = CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.characterDetail.collect { character ->
                epoxyController.characterResponse = character
            }
        }
        viewModel.refreshCharacter(intent.getIntExtra(Constant.CHARACTER_ID, 1))

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

