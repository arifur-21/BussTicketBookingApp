package com.example.busbookingapp

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentEndPointTbBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class EndPointTbFragment : Fragment() {

    private lateinit var binding: FragmentEndPointTbBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var selectRb: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentEndPointTbBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.radioGroupId1.setOnCheckedChangeListener { radioGroup, i ->

            var rb = view.findViewById<RadioButton>(i)
            if (rb != null){
                Log.e("main", "radion button"+ rb.text.toString())

                val radioButton: HashMap<String, Any> = HashMap()
                radioButton["EndPoint"] = rb.text.toString()
                val userId = auth.currentUser!!.uid

                db.collection("End Point").document(userId).set(radioButton).addOnCompleteListener { task->
                    if (task.isSuccessful){
                        Toast.makeText(requireContext(), "Added Start Point", Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }


        binding.btnEnd.setOnClickListener {
            val dialog  = Dialog(requireContext())
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.alert_dialog_layout)

            val btnCloser = dialog.findViewById<Button>(R.id.cancelBtnId)
            btnCloser.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }


}