package com.example.busbookingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.busbookingapp.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private var sumAmount: String = ""
    private var insurance: Int? = 20
    private var onlinFree: Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val totalPrice = bundle!!.getString("totalPrice")
        val name = bundle!!.getString("name")
        val date = bundle!!.getString("date")
        val condition = bundle!!.getString("condition")
        val from_bus = bundle!!.getString("from_bus")
        val to_bus = bundle!!.getString("to_bus")
        val startpoint = bundle!!.getString("startpoint")


        binding.toolBarFromTvId.text = from_bus
        binding.toolBarToTvId.text = to_bus

        binding.detailsTicketPriceId.text ="TK."+totalPrice
        binding.detailsBusConditionId.text = condition
        binding.detailsDateId.text = date
        binding.detailsTravelNameId.text = name
        binding.detailsTotalAmountId.text = totalPrice
        binding.detailsFromId.text = from_bus+" to "+to_bus
        binding.detailsStartPointId.text = startpoint

        val bundle1 = Bundle()
        bundle1.putString("amount",totalPrice)

        binding.detailCardOrMobileTvBtnId.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.action_detailsFragment_to_paymentGatewayFragment, bundle1)
        }
    }

}