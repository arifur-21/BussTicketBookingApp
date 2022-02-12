package com.example.busbookingapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCShipmentInfoInitializer
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener


class PaymentGatewayFragment : Fragment(), SSLCTransactionResponseListener {
    private lateinit var success: TextView
    private lateinit var faild : TextView
    private lateinit var error: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_gateway, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        success = view.findViewById(R.id.successId)
        faild = view.findViewById(R.id.faildId)
        error = view.findViewById(R.id.errorId)


        val bundle = arguments
      val amount = bundle!!.getString("amount")

        Log.e("main","amount"+amount)

        val sslCommerzInitialization = SSLCommerzInitialization(
            "sbs60605f415c440", "sbs60605f415c440@ssl", amount!!.toDouble(),
            SSLCCurrencyType.BDT, "123456789098765", "yourProductType", SSLCSdkType.TESTBOX
        )

        val customerInfoInitializer = SSLCCustomerInfoInitializer(
            "customer name", "customer email",
            "address", "dhaka", "1214", "Bangladesh", "phoneNumber"
        )

        val productInitializer = SSLCProductInitializer(
            "food", "food", SSLCProductInitializer.ProductProfile.TravelVertical(
                "Travel", "xyz",
                "A", "12", "Dhk-Syl"
            )
        )

        val shipmentInfoInitializer = SSLCShipmentInfoInitializer(
            "Courier", 2, SSLCShipmentInfoInitializer.ShipmentDetails(
                "AA", "Address 1",
                "Dhaka", "1000", "BD"
            )
        )


        IntegrateSSLCommerz
            .getInstance(requireContext())
            .addSSLCommerzInitialization(sslCommerzInitialization) // .addCustomerInfoInitializer(customerInfoInitializer)
            //.addProductInitializer(productInitializer)
            .buildApiCall(this)


    }

    override fun transactionSuccess(p0: SSLCTransactionInfoModel?) {
        success.setText(p0!!.apiConnect +"--"+ p0!!.status)
    }

    override fun transactionFail(p0: String?) {
        faild.text = p0
    }

    override fun merchantValidationError(p0: String?) {
        error.text = p0
    }


    }

