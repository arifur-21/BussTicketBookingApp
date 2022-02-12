package com.example.busbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.busbookingapp.databinding.FragmentAdminAddBusInfoBinding
import com.example.busbookingapp.model.BusModel
import com.google.firebase.firestore.FirebaseFirestore


class AdminAddBusInfoFragment : Fragment() {

    private lateinit var binding: FragmentAdminAddBusInfoBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAdminAddBusInfoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()

        binding.adminSaveBtnId.setOnClickListener {
            saveInformation()
        }


    }

    private fun saveInformation() {
        val busCondition = binding.adminBusConditionNameId.text.toString()
        val busNumber = binding.adminBusNumberId.text.toString()
        val date = binding.adminDateNameId.text.toString()
        val from = binding.adminFromNameId.text.toString()
        val to = binding.adminToNameId.text.toString()
        val travelsName = binding.adminCompanyNameId.text.toString()

        val bus: HashMap<String, Any> = HashMap()

        bus["busCondition"] = busCondition
        bus["busNumber"] = busNumber
        bus["from"] = from
        bus["to"] = to
        bus["travelsName"] = travelsName
        bus["date"] = date

        db.collection("BusDetails").add(bus).addOnCompleteListener { task->
            if (task.isSuccessful){
                Toast.makeText(requireContext(), "Bus Information Added Successfull", Toast.LENGTH_SHORT).show()
            }
        }
    }

}