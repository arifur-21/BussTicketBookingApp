package com.example.busbookingapp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentSeatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SeatFragment : Fragment() {

    private lateinit var binding: FragmentSeatBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private val seatPrice: Double = 500.00
    private var totalCost: Double = 0.0
    private var totalSeat: Int = 0
    private var number: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       binding = FragmentSeatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val bundle1 = arguments
       val name = bundle1!!.getString("name")
        val date = bundle1!!.getString("date")
        val condition = bundle1!!.getString("condition")
        val BusNumber = bundle1!!.getString("busNumber")
        val from_bus = bundle1!!.getString("from_bus")
        val to_bus = bundle1!!.getString("to_bus")




        setToggleEvent(binding.mainGrid)
        binding.btnBook.setOnClickListener {

            val totalPrice = binding.totalCost.text.toString().trim()
            val totalBookSeat = binding.totalSeats.text.toString().trim()

            val busCost: HashMap<String, Any> = HashMap()

            busCost["totalPrice"] = totalPrice
            busCost["totalSeats"] = totalBookSeat
            busCost["SeatNumber"] = number.toString()
            busCost["BusName"] = name.toString()
            busCost["condition"] = condition.toString()
            busCost["busNumber"] = BusNumber.toString()

            val userId = auth.currentUser!!.uid
            db.collection("SeatDetails").document(userId).set(busCost).addOnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(requireContext(), "Seat Detils Added Successfully", Toast.LENGTH_SHORT).show()

                    val bundle = Bundle()
                    bundle.putString("name", name)
                    bundle.putString("date", date)
                    bundle.putString("from_bus", from_bus)
                    bundle.putString("to_bus", to_bus)
                    bundle.putString("condition", condition)
                    bundle.putString("totalPrice", totalCost.toString())
                    bundle.putString("totalSeats", totalSeat.toString())

                    Navigation.findNavController(it).navigate(R.id.action_seatFragment_to_startPointTbFragment, bundle)
                }
            }




        }


    }

    private fun setToggleEvent(mainGrid: GridLayout) {


        //Loop all child item of Main Grid
        for (i in 0 until mainGrid.childCount) {
            //You can see , all child item is CardView , so we just cast object to CardView
            val cardView: CardView = mainGrid.getChildAt(i) as CardView

            cardView.setOnClickListener(View.OnClickListener {
                if (cardView.getCardBackgroundColor().getDefaultColor() === -1) {
                    //Change background color
                    cardView.setCardBackgroundColor(Color.parseColor("#00FF00"))
                    totalCost += seatPrice
                    ++totalSeat
                    Toast.makeText(requireContext(), "You Selected Seat Number :" + (i + 1), Toast.LENGTH_SHORT).show()
                  number = i+1
                } else {
                    //Change background color
                    cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                    totalCost -= seatPrice
                    --totalSeat
                    Toast.makeText(requireContext(), "You Unselected Seat Number :" + (i + 1), Toast.LENGTH_SHORT).show()
                }
                binding.totalCost.setText("" + totalCost + "0")

                binding.totalSeats.setText("" + totalSeat)
            })
        }
    }


}