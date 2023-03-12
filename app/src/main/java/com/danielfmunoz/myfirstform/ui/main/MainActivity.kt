package com.danielfmunoz.myfirstform.ui.main


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.danielfmunoz.myfirstform.R
import com.danielfmunoz.myfirstform.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainBinding.root)

        mainBinding.btnGuardar.setOnClickListener {
            validarCampos()
        }
    }

    private fun validarCampos() {
        val nombre = mainBinding.etNombre.text.toString()
        val correo = mainBinding.etCorreo.text.toString()
        val password = mainBinding.etPassword.text.toString()
        val repetirPassword = mainBinding.etRepetirPassword.text.toString()

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || repetirPassword.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            mainBinding.tvResultado.text = ""
        } else if (password != repetirPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            mainBinding.tvResultado.text = ""
        } else {
            val sexo = if (mainBinding.radioSexo.checkedRadioButtonId == R.id.rb_femenino) "Femenino" else "Masculino"
            val hobbies = mutableListOf<String>()
            val lugarNacimiento = mainBinding.spinnerLugarNacimiento.selectedItem.toString()
            if (mainBinding.cbHobby1.isChecked) hobbies.add(mainBinding.cbHobby1.text.toString())
            if (mainBinding.cbHobby2.isChecked) hobbies.add(mainBinding.cbHobby2.text.toString())
            if (mainBinding.cbHobby3.isChecked) hobbies.add(mainBinding.cbHobby3.text.toString())
            if (mainBinding.cbHobby4.isChecked) hobbies.add(mainBinding.cbHobby4.text.toString())

            val datePicker = mainBinding.datePicker
            val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"

            var datos = "Nombre: $nombre\n"
            datos += "Correo electrónico: $correo\n"
            datos += "Sexo: $sexo\n"
            datos += "Fecha de nacimiento: $selectedDate\n"
            if (hobbies.isNotEmpty()) {
                datos += "Hobbies: ${hobbies.joinToString(", ")}\n"
            }
            datos += "Ciudad: $lugarNacimiento\n"
            mainBinding.tvResultado.text = datos
        }
    }
}
