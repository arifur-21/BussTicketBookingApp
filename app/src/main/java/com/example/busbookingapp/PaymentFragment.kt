package com.example.busbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busbookingapp.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val totalPrice = bundle!!.getString("totalPrice")

        binding.textViewTotal.text = ("Payment : TK. "+totalPrice)

        binding.buttonCredit.setOnClickListener {

        }
    }

}