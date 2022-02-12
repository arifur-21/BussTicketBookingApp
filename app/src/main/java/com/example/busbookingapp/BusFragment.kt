package com.example.busbookingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.busbookingapp.adapter.BusAdapter
import com.example.busbookingapp.databinding.FragmentBusBinding
import com.example.busbookingapp.model.BusModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.util.*
import kotlin.collections.ArrayList

class BusFragment : Fragment() {

    private lateinit var binding: FragmentBusBinding
    private lateinit var busAdapter: BusAdapter
    private lateinit var busList: ArrayList<BusModel>
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentBusBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        db = FirebaseFirestore.getInstance()

        busList = ArrayList()
        busAdapter = BusAdapter(requireContext(), busList)
        binding.busRecycleViewId.layoutManager = LinearLayoutManager(requireContext())
        binding.busRecycleViewId.adapter = busAdapter


        val bundle = arguments
      val from_bus = bundle!!.getString("from_bus")
      val to_bus =  bundle!!.getString("to_bus")
      val bus_date = bundle!!.getString("bus_data")

        binding.toolBarFromTvId.text = from_bus+" to "+to_bus


        db.collection("BusDetails").whereEqualTo("from",from_bus)
                .whereEqualTo("to", to_bus)
                .addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    Log.e("error", error.message.toString())
                    return
                }

                for (dc: DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        busList.add(dc.document.toObject(BusModel::class.java))
                    }
                }
                busAdapter.notifyDataSetChanged()
            }

        })




    }


}