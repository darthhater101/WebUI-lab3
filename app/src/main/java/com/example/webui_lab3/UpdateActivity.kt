package com.example.webui_lab3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.webui_lab3.databinding.AddPopupBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: AddPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_popup)
        binding = AddPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.confirmButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("id", getIntent().getIntExtra("id", -1))
            intent.putExtra("firstname", binding.editTextFirstName.text.toString())
            intent.putExtra("lastname", binding.editTextLastName.text.toString())
            intent.putExtra("phone", binding.editTextPhone.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}