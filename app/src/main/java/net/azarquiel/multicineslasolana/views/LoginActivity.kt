package net.azarquiel.multicineslasolana.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import net.azarquiel.multicineslasolana.R
import net.azarquiel.multicineslasolana.model.Pelicula

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUsuario: EditText
    private lateinit var txtContrasena: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsuario=findViewById(R.id.txtUsuario)
        txtContrasena=findViewById(R.id.txtContrasena)

        progressBar= findViewById(R.id.progressBar)
        auth=FirebaseAuth.getInstance()
    }

    fun forgotPassword(view: View){
        startActivity(Intent(this, ForgotPassActivity::class.java))
    }

    fun register(view: View){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun login(view: View){
        loginUser()
    }

    private fun loginUser(){
        val user:String=txtUsuario.text.toString()
        val password:String=txtContrasena.text.toString()

        //Comprobamos que no estan vacios
        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBar.visibility=View.VISIBLE

            auth.signInWithEmailAndPassword(user,password).addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    action()
                }else{
                    Toast.makeText(this,"Error en la autenticacion", Toast.LENGTH_LONG).show()
                    casoerror()
                }
            }
        }
    }

    private fun action(){
        startActivity(Intent(this,MainActivity::class.java))
    }

    private fun casoerror(){
        startActivity(Intent(this,LoginActivity::class.java))
    }

    fun onClickRegistrar(v: View){
        startActivity(Intent(this,RegisterActivity::class.java))
    }
}
