package com.example.memoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.memoapp.R
import com.example.memoapp.data.Memo
import com.example.memoapp.data.Utils

class EditFragment : Fragment() {
     lateinit var editD: EditText
     lateinit var editT: EditText


     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         // Inflate the layout for this fragment
         return inflater.inflate(R.layout.fragment_edit, container, false)
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         editD = view.findViewById(R.id.editD)
         editT = view.findViewById(R.id.editT)


         var bundle = Bundle()
         bundle.putSerializable(Utils.KEY,editD.text.toString())
         bundle.putSerializable(Utils.KEY1,editT.text.toString())

         val fargment = MainFragment()
         fargment.arguments = bundle

         parentFragmentManager.beginTransaction().apply { replace(R.id.fragment_container, fargment)
             addToBackStack(null)
             commit()

         }




         super.onViewCreated(view, savedInstanceState)
     }
 }

