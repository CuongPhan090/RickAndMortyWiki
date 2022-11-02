package com.example.rickandmortywiki

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.rickandmortywiki.databinding.ActivityMainBinding
import com.example.rickandmortywiki.viewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.refreshCharacter(1)
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.characterDetail.collect { character ->
                if (character != null) {
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

                } else {
                    Toast.makeText(binding.root.context, "response null", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

