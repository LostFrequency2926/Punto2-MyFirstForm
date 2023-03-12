import android.content.Context
import android.provider.Settings.Secure.getString
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import com.danielfmunoz.myfirstform.R

class MainViewModel : ViewModel() {

    private var context: Context? = null

    fun setContext(context: Context) {
        this.context = context
    }

    private var nombre: String? = null
    private var correo: String? = null
    private var password: String? = null
    private var sexo: String? = null
    private var hobbies: MutableList<String> = mutableListOf()
    private var lugarNacimiento: String? = null
    private var fechaNacimiento : String? = null

    fun guardarDatos(
        nombre: String,
        correo: String,
        password: String,
        idRadioSexo: Int,
        hobby1: Boolean,
        hobby2: Boolean,
        hobby3: Boolean,
        hobby4: Boolean,
        lugarNacimiento: String,
        fechaNacimiento: String
    ) {

        //limpias la lista para que cada vez que se oprima el boton save se restablezca y no se acumulen
        hobbies.clear()

        this.nombre = nombre
        this.correo = correo
        this.fechaNacimiento = fechaNacimiento

        // Verificar la longitud de la contraseña
        if (password.length < 6 || password.length > 10) {
            throw Exception(context?.getString(R.string.pass_err))
        }

        this.password = password

        sexo = if (idRadioSexo == R.id.rb_femenino) context?.getString(R.string.female_gendre) else context?.getString(R.string.male_gendre)

        if (hobby1) hobbies.add(context?.getString(R.string.music_hobbie) ?: "")
        if (hobby2) hobbies.add(context?.getString(R.string.movie_hobbie) ?: "")
        if (hobby3) hobbies.add(context?.getString(R.string.read_hobbie) ?: "")
        if (hobby4) hobbies.add(context?.getString(R.string.games_hobbie) ?: "")

        this.lugarNacimiento = lugarNacimiento
    }

    fun obtenerDatos(): String {
        var datos = "${context?.getString(R.string.nameShow)} $nombre\n"
        datos += "${context?.getString(R.string.emailShow)} $correo\n"
        datos += "${context?.getString(R.string.gendreShow)} $sexo\n"
        if (hobbies.isNotEmpty()) {
            datos += "${context?.getString(R.string.hobbiesShow)} ${hobbies.joinToString(", ")}\n"
        }
        datos += "${context?.getString(R.string.dateBirthShow)} $fechaNacimiento\n"
        datos += "${context?.getString(R.string.placeBirthShow)} $lugarNacimiento\n"
        return datos
    }

    fun realizarValidateNulls(nombre: String, correo: String, password: String, repetirPassword: String): Boolean {
        return !(nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || repetirPassword.isEmpty())
    }

    fun realizarValidateEmail(correo: String): Boolean {
        return (esCorreoValido(correo))
    }

    fun realizarValidatePass(password: String, repetirPassword: String): Boolean {
        return (password != repetirPassword)
    }

    fun esCorreoValido(correo: String): Boolean {
        val regex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return regex.matches(correo)
    }
}
