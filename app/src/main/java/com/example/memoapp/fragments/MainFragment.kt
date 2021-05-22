package com.example.memoapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memoapp.R
import com.example.memoapp.data.Memo
import com.example.memoapp.data.Utils
import com.example.memoapp.data.adapters.Adapter
import com.google.firebase.firestore.FirebaseFirestore

class MainFragment : Fragment(), Adapter.OnItemClickListener {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: Adapter
    private var userNotes = mutableListOf<Memo>()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrieveDataFromFirestore()
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.KEY, userNotes[position])

        val fragment = DetailsFragment()
        fragment.arguments = bundle
    }

    override fun onDeleteClick(position: Int) {
        val note = userNotes[position]
        val warningDialog = AlertDialog.Builder(context)
            .setTitle("Do you really want to delete?")
            .setIcon(R.drawable.ic_baseline_delete_forever_24)
            .setPositiveButton("Delete") { _, _ ->
                db.collection("notes")
                    .document(note.id)
                    .delete()
                    .addOnSuccessListener { Log.d("MyData", "Note successfully deleted!") }
                    .addOnFailureListener { e -> Log.w("MyData", "Error deleting document", e) }
                retrieveDataFromFirestore()
                adapter.notifyItemRemoved(position)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()
        warningDialog.show()
    }

    override fun onEditClick(position: Int) {
        val bundle = Bundle()
        bundle.putSerializable(Utils.KEY, userNotes[position])

        val fragment = EditFragment()
        fragment.arguments = bundle

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun handleRecyclerView(userNotes: MutableList<Memo>) {
        Log.i("MyData handle", userNotes.toString())
        recyclerView = requireView().findViewById(R.id.notesList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        adapter = context?.let { Adapter(it, userNotes, this) }!!
        recyclerView.adapter = adapter
    }

    private fun retrieveDataFromFirestore() {
        var title: String
        var description: String
        var date: String
        var id: String
        val list = mutableListOf<Memo>()
        db.collection("notes")
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        title = document.data["title"] as String
                        description = document.data["description"] as String
                        date = document.data["publicDate"] as String
                        id = document.data["id"] as String
                        val temp = Memo(title, description, date, id)
                        list.add(temp)
                    }
                    userNotes.clear()
                    userNotes.addAll(list)
                    handleRecyclerView(list)
                }
            }
            .addOnFailureListener { exception ->
                Log.i("MyData", exception.toString())
            }
    }
}