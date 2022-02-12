package com.example.busbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

       /* if (auth != null){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_navigationFragment)
        }*/
        binding.adminTvId.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_adminAddBusInfoFragment)
        }

        binding.loginSignInTvId.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.userRegisterBtnId.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.userLoginEmailId.text.toString()
        val pass = binding.userLoginPasswordId.text.toString()

        if (email.isEmpty()){binding.userLoginEmailId.setError("Enter your Email")}
        else if (pass.isEmpty()){binding.userLoginPasswordId.setError("Enter your Password")}
        else{
            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener { task->

                if (!task.isSuccessful){
                    Toast.makeText(requireContext(), "Error ${task.exception.toString()}", Toast.LENGTH_SHORT).show()
                }else{

                    val userInfo: MutableMap<String, Any> = HashMap()
                    userInfo["email"] = email
                    db.collection("UserInfo").add(userInfo).addOnCompleteListener { task->
                        if (task.isSuccessful){

                        }
                    }
                }
            }
        }
    }
    }
