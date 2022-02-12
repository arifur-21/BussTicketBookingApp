package com.example.busbookingapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentPaybleBinding
import com.example.busbookingapp.model.PassengerModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.okhttp.internal.DiskLruCache


class PaybleFragment : Fragment() {

    private lateinit var binding: FragmentPaybleBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var gender: String ="Male"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPaybleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()




        binding.paybleCusDetailsBookingBtnId.setOnClickListener {view->
                passengerDetails(view)
        }

    }

    private fun passengerDetails(view: View) {

        val bundle = arguments
        val totalPrice = bundle!!.getString("totalPrice")
        val name = bundle!!.getString("name")
        val startpoint = bundle!!.getString("startpoint")
        val date = bundle!!.getString("date")
        val condition = bundle!!.getString("condition")
        val from_bus = bundle!!.getString("from_bus")
        val to_bus = bundle!!.getString("to_bus")

        val bundle1 = Bundle()
        bundle1.putString("name", name)
        bundle1.putString("startpoint", startpoint)
        bundle1.putString("date", date)
        bundle1.putString("from_bus", from_bus)
        bundle1.putString("to_bus", to_bus)
        bundle1.putString("condition", condition)
        bundle1.putString("totalPrice", totalPrice.toString())

        binding.toolBarFromTvId.text = from_bus+" to "+to_bus


        val usrname = binding.paybleCusDetailsNameId.text.toString()
        val email = binding.paybleCusDetailsEmailId.text.toString()
        val phone = binding.paybleCusDetailsPhoneId.text.toString()

        binding.payableRadioGroupId.setOnCheckedChangeListener{group, checkedId->
            if (checkedId == R.id.FemailId){
                gender = "Femail"
            }
            if (checkedId == R.id.maleId){
                gender = "Male"
            }
        }

        val userId = auth.currentUser!!.uid
        val passenger = PassengerModel(usrname,email,phone)
        db.collection("PassengerDetails").document(userId).set(passenger).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(requireContext(),"Passenger Details Added Successfull",Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_paybleFragment_to_detailsFragment,bundle1)
            }
        }
    }

}