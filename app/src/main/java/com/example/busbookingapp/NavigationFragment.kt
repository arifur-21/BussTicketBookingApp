package com.example.busbookingapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentNavigationBinding
import com.example.busbookingapp.model.BookingDetail
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class NavigationFragment : Fragment() {

    private lateinit var binding: FragmentNavigationBinding
    private lateinit var formattedDate: String
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var databaseReference: DatabaseReference? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNavigationBinding.inflate(layoutInflater)


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tool_bar = view.findViewById(R.id.tool_barId) as androidx.appcompat.widget.Toolbar
        val drawer_layout = view.findViewById(R.id.drawer_layoutId) as DrawerLayout


        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
                requireActivity(),
                drawer_layout,
                tool_bar,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)

            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

            }
        }


        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.nvaViewId.setNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId){
                R.id.getTicketMenuId ->{
                    Navigation.findNavController(view).navigate(R.id.action_navigationFragment_to_getTicketFragment)
                }

                R.id.logOutMenuId ->{
                    auth.signOut()
                    Navigation.findNavController(view).navigate(R.id.action_navigationFragment_to_loginFragment)
                }
            }

            true
        }



        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        if (auth.currentUser == null){
            Navigation.findNavController(view).navigate(R.id.action_navigationFragment_to_loginFragment)
        }

        binding.tvDate.setOnClickListener {
            calender()
        }

        binding.angryBtn.setOnClickListener {
            val from = binding.spinner1.selectedItem.toString().trim()
            val to = binding.spinner2.selectedItem.toString().trim()
            val date = binding.tvDate.text.toString().trim()

            if (TextUtils.equals(from, "From")){
                Toast.makeText(
                    requireContext(),
                    "Please select departure place",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
           else if (TextUtils.equals(to, "To")){
                Toast.makeText(
                    requireContext(),
                    "Please select departure place",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
          else if (TextUtils.equals(date, "Date")){

                Toast.makeText(requireContext(), "Please select Journey date", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{

                val bookingDetail = BookingDetail(from, to, formattedDate)

                val userId = auth.currentUser
                db.collection("BookingDetails").document(userId!!.uid).collection("AllBookingDetails").document().set(bookingDetail).addOnCompleteListener { task->

                }

                val bundle = Bundle()
                bundle.putString("from_bus", from)
                bundle.putString("to_bus", to)
                bundle.putString("bus_date", formattedDate)
                Navigation.findNavController(view).navigate(R.id.action_navigationFragment_to_busFragment, bundle)
            }


        }

    }


    private fun calender() {
        val calendar = Calendar.getInstance()

        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]

        val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }
    var listener = DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 ->
        val calendar = Calendar.getInstance()
        calendar[i, i1] = i2
        val sfd = SimpleDateFormat("dd/MM/yyyy")
        formattedDate = sfd.format(calendar.time)
        binding.tvDate.text = formattedDate
    }
}