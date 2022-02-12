package com.example.busbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentCardInformatinBinding
import com.google.firebase.firestore.FirebaseFirestore


class CardInformatinFragment : Fragment() {


    private lateinit var binding: FragmentCardInformatinBinding
    private lateinit var db: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentCardInformatinBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()

        binding.btnFinish.setOnClickListener {view->
            addCard(view)
        }

    }

    private fun addCard(view: View) {

        val cardNumber = binding.textViewCardNumber.text.toString()
        val cardDate = binding.textViewDate.text.toString()
        val cvvNumber = binding.textViewCvvNumber.text.toString()
        val cardName = binding.textViewCardName.text.toString()

        val card : HashMap<String, Any> = HashMap()

         card["cardName"] = cardName
        card["cardDate"] = cardDate
        card["cvvNumber"] = cvvNumber
        card["cardNumber"] = cardNumber

        db.collection("PaymentDetails").add(card).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(requireContext(), "Payment details added successfull", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_cardInformatinFragment_to_customerInfoFragment)

            }
        }
    }

}