package com.rubayat.addabuzz

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rubayat.addabuzz.databinding.ActivityLoginactivityBinding
import com.rubayat.addabuzz.databinding.ActivityMainBinding


class Loginactivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginactivityBinding

    //firebass connect

    private lateinit var database: FirebaseDatabase
    private lateinit var ref : DatabaseReference
    private lateinit var auth: FirebaseAuth


    //on back pressed called back
    var backPressedTime: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = ActivityLoginactivityBinding.inflate(layoutInflater)

        val view = loginBinding.root
        setContentView(view)

        //Firebass connect
        var database = FirebaseDatabase.getInstance()
        ref = database.getReference("User")
        auth = FirebaseAuth.getInstance()


        //on back pressed call back
        val callback = object :
            OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                if (backPressedTime + 3000 > System.currentTimeMillis()) {
                    finish()
                } else {
                    Toast.makeText(
                        this@Loginactivity,
                        "press back again to leave the app",
                        Toast.LENGTH_LONG
                    ).show()
                }
                backPressedTime =
                    System.currentTimeMillis()
            }
        }
        onBackPressedDispatcher.addCallback(this,callback)


        loginBinding.logbtn.setOnClickListener {
            val email = loginBinding.email.text.toString().trim()
            val password = loginBinding.password.text.toString().trim()
             if(email.isEmpty()|| password.isEmpty()){
                 Toast.makeText(applicationContext,"please fill all the fields",Toast.LENGTH_SHORT).show()
             }else{
                 auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{ task ->
                         if (task.isSuccessful){
                             Toast.makeText(this,"login Successful",Toast.LENGTH_SHORT).show()
                             val intent = Intent(this,MainActivity::class.java)
                             intent.putExtra("E-mail",email)
                             startActivity(intent)
                             finish()
                         }else{
                             Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
                         }

                     }
             }
        }

        loginBinding.register.setOnClickListener {
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        loginBinding.forgot.setOnClickListener {
           val intent = Intent(this,ForgotPass::class.java)
            startActivity(intent)
        }
        }

    }

