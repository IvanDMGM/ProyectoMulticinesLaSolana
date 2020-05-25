package net.azarquiel.multicineslasolana.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AbsSeekBar
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import net.azarquiel.multicineslasolana.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNombre:EditText
    private lateinit var txtApellido:EditText
    private lateinit var txtEmail:EditText
    private lateinit var txtContrasena:EditText
    private lateinit var progressBar:ProgressBar
    private lateinit var database:FirebaseDatabase
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        txtNombre=findViewById(R.id.txtNombre)
        txtApellido=findViewById(R.id.txtApellido)
        txtEmail=findViewById(R.id.txtEmail)
        txtContrasena=findViewById(R.id.txtContrasena)

        progressBar= findViewById(R.id.progressBar)

        //INSTANCIA PARA LA BASE DE DATOS Y AUNTETICACION
        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()


    }

    fun register(view: View){
        createNewAccount()
    }

    //Obtenemos los valores que se obtienen de las Cajas de texto
    private fun createNewAccount(){
        val nombre: String=txtNombre.text.toString()
        val apellido: String=txtApellido.text.toString()
        val email: String=txtEmail.text.toString()
        val contrasena: String=txtContrasena.text.toString()

       //Comprobamos que los campos no estan vacios
        if(!TextUtils.isEmpty(nombre) && !TextUtils.isEmpty(apellido) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(contrasena) ){
            progressBar.visibility=View.VISIBLE

            auth.createUserWithEmailAndPassword(email,contrasena).addOnCompleteListener(this){
                task ->

                if(task.isComplete){
                    val user: FirebaseUser?=auth.currentUser
                    verifyEmail(user)

                    action()
                }
            }
        }
    }

    //Cuando se realice el proceso correctamente se ejecutara este metodo
    private fun action(){
        startActivity(Intent(this,LoginActivity::class.java))
    }

   //Email de verificacion
    private fun verifyEmail(user:FirebaseUser?){
       user?.sendEmailVerification()?.addOnCompleteListener(this){
           task ->

           if(task.isComplete){
               Toast.makeText(this,"Email enviado", Toast.LENGTH_LONG).show()
           } else {
               Toast.makeText(this,"Error al enviar el email", Toast.LENGTH_LONG).show()
               casoerror()
           }
       }

   }

    private fun casoerror(){
        startActivity(Intent(this,RegisterActivity::class.java))
    }

}
