package com.example.api

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.api.api.RetrofitInstance
import com.example.api.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val year = binding.yearEditText.text.toString().toIntOrNull()
            if (year != null) {
                searchHolidays(year)
            } else {
                Toast.makeText(this, "Por favor, insira um ano válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchHolidays(year: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.api.getHolidays(year)
                if (response.isSuccessful && response.body() != null) {
                    val holidays = response.body()!!
                    val holidaysText = holidays.joinToString("\n") { "${it.date}: ${it.name}" }
                    binding.holidaysTextView.text = holidaysText
                } else {
                    Toast.makeText(this@MainActivity, "Erro ao buscar feriados", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Falha na conexão: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}