package com.danielfmunoz.myfirstform.ui.main

import MainViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.danielfmunoz.myfirstform.R
import com.danielfmunoz.myfirstform.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // establecer la referencia al Context en el ViewModel
        mainViewModel.setContext(this)

        mainBinding.btnGuardar.setOnClickListener {
            try {
                validarCampos()
            } catch (e: IllegalArgumentException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                mainBinding.tvResultado.text = ""
            }
        }
    }

    private fun validarCampos() {
        val nombre = mainBinding.etNombre.text.toString()
        val correo = mainBinding.etCorreo.text.toString()
        val password = mainBinding.etPassword.text.toString()
        val repetirPassword = mainBinding.etRepetirPassword.text.toString()

        val datePicker = mainBinding.datePicker
        val dia = datePicker.dayOfMonth
        val mes = datePicker.month + 1
        val ano = datePicker.year
        val fechaNacimiento = "$dia/$mes/$ano"

        if (mainViewModel.realizarValidateNulls(nombre, correo, password, repetirPassword)) {
            if (mainViewModel.realizarValidatePass(password, repetirPassword)) {
                Toast.makeText(this, getString(R.string.pass_no_match), Toast.LENGTH_SHORT).show()
                mainBinding.tvResultado.text = ""
            } else {
                if (mainViewModel.realizarValidateEmail(correo)) {
                    try {
                        mainViewModel.guardarDatos(
                            nombre,
                            correo,
                            password,
                            mainBinding.radioSexo.checkedRadioButtonId,
                            mainBinding.cbHobby1.isChecked,
                            mainBinding.cbHobby2.isChecked,
                            mainBinding.cbHobby3.isChecked,
                            mainBinding.cbHobby4.isChecked,
                            mainBinding.spinnerLugarNacimiento.selectedItem.toString(),
                            fechaNacimiento
                        )
                        mainBinding.tvResultado.text = mainViewModel.obtenerDatos()
                    } catch (e: Exception) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                        mainBinding.tvResultado.text = ""
                    }
                } else {
                    Toast.makeText(this, getString(R.string.email_valid), Toast.LENGTH_SHORT).show()
                    mainBinding.tvResultado.text = ""
                }
            }
        }else{
            Toast.makeText(this, getString(R.string.required_fields), Toast.LENGTH_SHORT).show()
            mainBinding.tvResultado.text = ""
        }
    }
}
