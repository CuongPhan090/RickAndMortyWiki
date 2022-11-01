package com.example.rickandmortywiki

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.rickandmortywiki.databinding.ActivityMainBinding
import com.example.rickandmortywiki.viewmodel.CharacterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: CharacterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.characterDetail.collect { character ->
                binding.characterName.text = character.name
                binding.characterImage.load(character.image)
                if (character.gender.toString().lowercase() == "male") {
                    binding.characterGender.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.male_symbol))
                } else {
                    binding.characterGender.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.female_symbol))
                }
                binding.characterOrigin.text = character.origin?.name
                binding.characterSpecies.text = character.species
                binding.characterStatus.text = character.status
            }
        }
    }
}

