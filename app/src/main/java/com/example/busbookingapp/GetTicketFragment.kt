package com.example.busbookingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.busbookingapp.databinding.FragmentGetTicketBinding
import com.example.busbookingapp.model.BookingDetail
import com.example.busbookingapp.model.CustomerInfoModel
import com.example.busbookingapp.model.SeatModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import java.lang.StringBuilder


class GetTicketFragment : Fragment() {

    private lateinit var binding: FragmentGetTicketBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentGetTicketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val userId = auth.currentUser!!.uid


        db.collection("PassengerDetails").addSnapshotListener(
            object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        return
                    }
                    for (dc in value!!.documents){
                        val user = dc.toObject(CustomerInfoModel::class.java)
                        binding.getTicketUserNameId.text = user!!.name
                        binding.getTicketUserEmailId.text = user!!.email
                        binding.getTicketUserPhoneId.text = user!!.phone
                    }


                }

            })

        db.collection("BookingDetails").addSnapshotListener(
            object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        return
                    }
                    for (dc in value!!.documents){
                        val user = dc.toObject(BookingDetail::class.java)
                        binding.getTicketFromId.text = user!!.from
                        binding.getTicketToId.text = user!!.to
                        binding.getTicketBookingDateId.text = user!!.date
                    }
                }

            })


        db.collection("SeatDetails").addSnapshotListener(
            object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        return
                    }
                    for (dc in value!!.documents){
                        val user = dc.toObject(SeatModel::class.java)
                        binding.getTicketNumberOfSeatId.text = user!!.totalSeats.toString()
                        binding.getTicketTotalCostId.text = "TK."+user!!.totalPrice.toString()
                        binding.getTicketBusConditiond.text = user!!.condition
                        binding.getTicketTravelNameId.text = user!!.BusName
                        binding.getTicketBusNumberId.text = user!!.busNumber
                    }
                }
            })


    }

}