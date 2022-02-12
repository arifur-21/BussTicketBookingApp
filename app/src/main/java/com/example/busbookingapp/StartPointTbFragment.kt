package com.example.busbookingapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentStartPointTbBinding
import com.example.busbookingapp.model.Point
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class StartPointTbFragment : Fragment() {

   private lateinit var binding: FragmentStartPointTbBinding
   private lateinit var auth: FirebaseAuth
   private lateinit var db: FirebaseFirestore
   private var selectRb: String = ""
    private var endPoint: String = ""
    private var startPoint: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentStartPointTbBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val bundle = arguments
        val from_bus = bundle!!.getString("from_bus")
        val to_bus = bundle!!.getString("to_bus")
        val totalPrice = bundle!!.getString("totalPrice")
        val name = bundle!!.getString("name")
        val date = bundle!!.getString("date")
        val condition = bundle!!.getString("condition")


        val bundle1 = Bundle()
        bundle1.putString("name", name)
        bundle1.putString("startpoint", startPoint)
        bundle1.putString("date", date)
        bundle1.putString("from_bus", from_bus)
        bundle1.putString("to_bus", to_bus)
        bundle1.putString("condition", condition)
        bundle1.putString("totalPrice", totalPrice.toString())




        binding.toolBarFromTvId.text = from_bus
        binding.toolBarToTvId.text = to_bus

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()



        binding.radioGroupId.setOnCheckedChangeListener { radioGroup, i ->
            var rb = view.findViewById<RadioButton>(i)
            if (rb != null){
                startPoint = rb.text.toString()
                Toast.makeText(requireContext(), "Added Start Point"+rb.text.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.radioGroup1Id.setOnCheckedChangeListener { radioGroup, i ->
            var rb = view.findViewById<RadioButton>(i)
            if (rb != null){
                startPoint = rb.text.toString()
                Toast.makeText(requireContext(), "Added Start Point"+rb.text.toString(), Toast.LENGTH_SHORT).show()

            }
        }

        binding.radioGroup2Id.setOnCheckedChangeListener { radioGroup, i ->
            var rb = view.findViewById<RadioButton>(i)
            if (rb != null){
                startPoint = rb.text.toString()
                Toast.makeText(requireContext(), "Added Start Point"+rb.text.toString(), Toast.LENGTH_SHORT).show()

            }
        }
        binding.radioGroup3Id.setOnCheckedChangeListener { radioGroup, i ->
            var rb = view.findViewById<RadioButton>(i)
            if (rb != null){
                startPoint = rb.text.toString()
                Toast.makeText(requireContext(), "Added Start Point"+rb.text.toString(), Toast.LENGTH_SHORT).show()

            }
        }



        binding.endRadioGroupId.setOnCheckedChangeListener { radioGroup, i ->
            var rb = view.findViewById<RadioButton>(i)
            if (rb != null){
                endPoint = rb.text.toString()
                Toast.makeText(requireContext(), "Added Ent Point"+rb.text.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.endradioGroup1Id.setOnCheckedChangeListener { radioGroup, i ->
            var rb = view.findViewById<RadioButton>(i)
            if (rb != null){
                endPoint = rb.text.toString()
                Toast.makeText(requireContext(), "Added Ent Point"+rb.text.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        binding.endradioGroup2Id.setOnCheckedChangeListener { radioGroup, i ->
            var rb = view.findViewById<RadioButton>(i)
            if (rb != null){
                endPoint = rb.text.toString()
                Toast.makeText(requireContext(), "Added Ent Point"+rb.text.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        binding.endradioGroup3Id.setOnCheckedChangeListener { radioGroup, i ->
            var rb = view.findViewById<RadioButton>(i)
            if (rb != null){
                endPoint = rb.text.toString()
                Toast.makeText(requireContext(), "Added Ent Point"+rb.text.toString(), Toast.LENGTH_SHORT).show()
            }
        }




        binding.startEndPointBookingBtnId.setOnClickListener {
            val userId = auth.currentUser!!.uid
            val point = Point(startPoint,endPoint)
            db.collection("locationPoint").document(userId).set(point).addOnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(requireContext(), "Added Ent Point", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(view).navigate(R.id.action_startPointTbFragment_to_paybleFragment,bundle1)
                    Log.e("main",endPoint)
                }
            }

        }

    }


}