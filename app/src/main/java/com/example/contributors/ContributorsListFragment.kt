package com.example.contributors

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContributorsListFragment : Fragment() {

    private lateinit var _contributors_adapter : ContributorsListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _contributors_adapter = ContributorsListAdapter(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_contributors_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contributors_list = view.findViewById<RecyclerView>(R.id.recycler_view)

        contributors_list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ItemDecoration(requireContext()))
            adapter = _contributors_adapter
        }
    }

    override fun onDetach() {
        super.onDetach()
    }

    fun addContributor(user : User?, contribs : Int) {
        Handler(Looper.getMainLooper()).post {
            when ( user ) {
                null -> Log.d(null, "variable \'user\' is null.")
                else -> {
                    _contributors_adapter.add(user, contribs)
                    _contributors_adapter.notifyItemInserted(_contributors_adapter.itemCount-1)
                }
            }
        }
    }

}