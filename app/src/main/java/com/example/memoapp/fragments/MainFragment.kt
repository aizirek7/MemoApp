package com.example.memoapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memoapp.R
import com.example.memoapp.data.Memo
import com.example.memoapp.data.Utils
import com.example.memoapp.data.adapters.Adapter
import com.google.android.material.navigation.NavigationView
import java.util.*
import android.view.Menu as Menu
import com.example.memoapp.MainActivity as MainActivity1


class MainFragment : Fragment(), Adapter.OnItemClickListener{
    lateinit var  memoList: RecyclerView
    lateinit var adapter: Adapter
    lateinit var toggle : ActionBarDrawerToggle
    lateinit var nav : NavigationView
    lateinit var userNotes: MutableList<Memo>
    var date: StringBuilder = setCurrentDateOnView()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_main, container, false)

    }


    fun setCurrentDateOnView(): java.lang.StringBuilder{
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]


        date = StringBuilder()
            .append(day).append("/").append(month + 1).append("/")
            .append(year)



        return date
    }







    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var context: Context? = getContext()
        super.onViewCreated(view, savedInstanceState)


        val recyclerView: RecyclerView = view.findViewById(R.id.memoList)
        userNotes = mutableListOf()

        userNotes.add(Memo("First Title", "First Desc", date))
        userNotes.add(Memo("Second Title", "Second Desc",date))
        userNotes.add(Memo("Third Title", "Third Desc", date))
        userNotes.add(Memo("Four Title", "Four Desc", date))
        userNotes.add(Memo("Five Title", "Five Desc", date))


        val drawerLayout = view.findViewById<DrawerLayout>(R.id.frameLayout)
        nav = view.findViewById(R.id.nav)

        toggle = ActionBarDrawerToggle(activity, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()







        nav.setNavigationItemSelectedListener{
            val fragment = SettingFragment()
            val fragment1 = LinkFragment()
            when(it.itemId){
                R.id.setting -> parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container,fragment)
                    commit()
                }

                R.id.link -> parentFragmentManager.beginTransaction().apply {
                    replace(R.id.fragment_container,fragment1)
                    commit()
                }
            }
            true
        }





        memoList = view.findViewById(R.id.memoList)
        memoList.layoutManager = LinearLayoutManager(context)
        memoList.setHasFixedSize(true)

        adapter = Adapter(userNotes, this)
        memoList.adapter = adapter






    }

    override fun onItemClick(position: Int) {
        var bundle = Bundle()
        bundle.putSerializable(Utils.KEY, userNotes[position])

        val fargment = DetailsFragment()
        fargment.arguments = bundle

        parentFragmentManager.beginTransaction().apply { replace(R.id.fragment_container, fargment)
        addToBackStack(null)
            commit()

        }



    }

    override fun onEditClick(position: Int) {
        val fragment = EditFragment()
        parentFragmentManager.beginTransaction().apply { replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()

        }

        val bundle = arguments
        if (bundle != null) {
            val editD = bundle.getSerializable(Utils.KEY) as String
            val editT = bundle.getSerializable(Utils.KEY1) as String

            userNotes.add(Memo(editT, editT, date))
            adapter.notifyDataSetChanged()



        }


    }

    override fun onDeleteClick(position: Int) {
        userNotes.removeAt(position)
        adapter.notifyItemRemoved(position)



    }



}