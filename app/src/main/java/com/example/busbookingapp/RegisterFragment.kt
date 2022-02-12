package com.example.busbookingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentRegisterBinding
import com.example.busbookingapp.model.RegisterUserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var emailPattern : String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.registSignUpTvId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.userRegisterBtnId.setOnClickListener {view->
            register(view)
        }
    }

    private fun register(view: View) {
        val email = binding.userRegisterEmailId.text.toString()
        val pass = binding.userRegisterPasswordId.text.toString()
        val name = binding.userRegisterNameId.text.toString()

         emailPattern  = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

      if (email.isEmpty()){binding.userRegisterEmailId.setError("Enter your Email")}
        else if (name.isEmpty()){binding.userRegisterNameId.setError("Enter your Name")}
        else if (pass.isEmpty()){binding.userRegisterPasswordId.setError("Enter your Password")}
        else{
           auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task->

               if (!task.isSuccessful){
                   Toast.makeText(
                       requireContext(),
                       "Error ${task.exception.toString()}",
                       Toast.LENGTH_SHORT
                   ).show()
               }else{

                   val user =  RegisterUserModel(name,email)

                   db.collection("Register User").add(user).addOnCompleteListener { task->
                       if (task.isSuccessful){
                            Toast.makeText(requireContext(),"Register Successfull", Toast.LENGTH_SHORT).show()
                           Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_navigationFragment)
                       }
                   }
               }
           }
      }
    }

}