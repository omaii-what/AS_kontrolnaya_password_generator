package com.example.as_kontrolnaya_password_generator

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.as_kontrolnaya_password_generator.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.generateButton.setOnClickListener {
            generate()
        }
    }

    private fun generate() = with(binding) {
        try {
            val lengthText = lengthInput.text.toString()
            if (lengthText.isEmpty()) {
                Toast.makeText(this@MainActivity, "Введите длину пароля", Toast.LENGTH_SHORT).show()
                return
            }
            val length = lengthText.toIntOrNull()
            if (length == null || length < 6 || length > 20) {
                Toast.makeText(this@MainActivity, "Длина пароля должна быть от 6 до 20", Toast.LENGTH_SHORT).show()
                return
            }

            val countText = countInput.text.toString()
            if (countText.isEmpty()) {
                Toast.makeText(this@MainActivity, "Введите количество паролей", Toast.LENGTH_SHORT).show()
                return
            }
            val count = countText.toIntOrNull()
            if (count == null || count < 1 || count > 15) {
                Toast.makeText(this@MainActivity, "Количество паролей должно быть от 1 до 15", Toast.LENGTH_SHORT).show()
                return
            }

            val useLowercase = lowercaseCheckbox.isChecked
            val useUppercase = uppercaseCheckbox.isChecked
            val useDigits = digitsCheckbox.isChecked
            val useSpecial = specialCheckbox.isChecked

            if (!useLowercase && !useUppercase && !useDigits && !useSpecial) {
                Toast.makeText(this@MainActivity, "Выберите хотя бы один тип символов", Toast.LENGTH_SHORT).show()
                return
            }

            val chars = buildString {
                if (useLowercase) append("abcdefghijklmnopqrstuvwxyz")
                if (useUppercase) append("ABCDEFGHIJKLMNOPQRSTUVWXYZ")
                if (useDigits) append("0123456789")
                if (useSpecial) append("!@#$%&*")
            }

            val passwords = mutableListOf<String>()
            for (i in 1..count) {
                passwords.add(generatePassword(length, chars))
            }

            resultText.text = "Сгенерированные пароли:\n\n${passwords.joinToString("\n")}"

        } catch(e: Exception) {
            Toast.makeText(this@MainActivity, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generatePassword(length: Int, chars: String): String {
        val password = StringBuilder()
        for (i in 1..length) {
            val randomIndex = Random.nextInt(0, chars.length)
            password.append(chars[randomIndex])
        }
        return password.toString()
    }
}