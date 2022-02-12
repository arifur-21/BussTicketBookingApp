package com.example.busbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentCustomerInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class CustomerInfoFragment : Fragment() {

    private lateinit var binding: FragmentCustomerInfoBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentCustomerInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.btnBook.setOnClickListener { view ->
           passengerInfo(view)
        }

    }

    private fun passengerInfo(view: View?) {

        val email = binding.editTextEmail.text.toString()
        val phone = binding.editTextPhoneNumber.text.toString()
        val name = binding.editTextName.text.toString()
        val age = binding.editTextAge.text.toString()

        val customer : HashMap<String, Any> = HashMap()
        customer["email"] = email
        customer["phone"] = phone
        customer["name"] = name
        customer["age"] = age

        val userId = auth.currentUser!!.uid

        db.collection("CustomerInformation").document(userId).set(customer).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(requireContext(), "Customer Information added successfull", Toast.LENGTH_SHORT).show()
                if (view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_customerInfoFragment_to_placePointFragment)
                }
            }
        }

    }

}