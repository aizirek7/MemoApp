package com.example.memoapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.memoapp.R
import com.example.memoapp.data.Memo
import com.example.memoapp.data.Utils
import java.text.SimpleDateFormat
import java.util.*


class DetailsFragment : Fragment() {
    lateinit var goodT: TextView
    lateinit var goodD: TextView
    lateinit var description: TextView
    lateinit var viewG : ViewGroup





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_details, container, false)
        if (container != null) {
            viewG = container
        }




        description = view.findViewById(R.id.description)
        goodD = view.findViewById(R.id.goodD)
        goodT = view.findViewById(R.id.goodT)

        val bundle = arguments
        if (bundle != null) {
            val details = bundle.getSerializable(Utils.KEY) as Memo

            description.setText(details.memoDescription)
            goodD.setText(details.date.toString())
            goodT.setText(details.memoTitle)

        }


        return view
    }



}