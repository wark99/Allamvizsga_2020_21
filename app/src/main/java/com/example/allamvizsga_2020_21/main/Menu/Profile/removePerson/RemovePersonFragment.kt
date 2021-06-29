package com.example.allamvizsga_2020_21.main.Menu.Profile.removePerson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.allamvizsga_2020_21.Firebase.Data.ProfileData
import com.example.allamvizsga_2020_21.R
import com.example.allamvizsga_2020_21.Utils.ConnectionChecker
import com.example.allamvizsga_2020_21.Utils.LoadingSwitch
import kotlinx.coroutines.Dispatchers

class RemovePersonFragment : Fragment(), RemovePersonContract.View,
    ProfileRecyclerViewAdapter.OnItemLongClickListener {

    private val presenter: RemovePersonContract.Presenter = RemovePersonPresenter(this)

    private lateinit var loadingLayout: ConstraintLayout
    private lateinit var currentLayout: ConstraintLayout

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var errorTextView: TextView
    private lateinit var saveButton: Button

    private lateinit var dataSet: ArrayList<ProfileData>
    private lateinit var adapter: ProfileRecyclerViewAdapter
    private lateinit var removableData: ArrayList<ProfileData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_remove_person, container, false)
    }

    override fun onResume() {
        super.onResume()

        loadingLayout = requireActivity().findViewById(R.id.removePersonLoadingLayout)
        currentLayout = requireActivity().findViewById(R.id.removePersonLayout)

        swipeRefreshLayout = requireActivity().findViewById(R.id.profileSwipeLayout)

        recyclerView = requireActivity().findViewById(R.id.removePersonRecyclerView)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        errorTextView = requireActivity().findViewById(R.id.removePersonErrorTextView)
        saveButton = requireActivity().findViewById(R.id.saveButton)

        saveButton.setOnClickListener {
            saveChanges()
        }

        loadPersons()

        swipeRefreshLayout.setOnRefreshListener {
            loadPersons()
        }
    }

    private fun saveChanges() {
        if (ConnectionChecker(requireContext()).isConnected()) {
            Dispatchers.Main.run {
                LoadingSwitch().showLoading(currentLayout, loadingLayout)
            }

            Dispatchers.IO.run {
                presenter.saveChanges(removableData)
            }
        } else {
            presenter.connectionError()
        }
    }

    override fun savingSuccess() {
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
    }

    private fun loadPersons() {
        removableData = arrayListOf()
        if (ConnectionChecker(requireContext()).isConnected()) {
            Dispatchers.Main.run {
                LoadingSwitch().showLoading(currentLayout, loadingLayout)
            }

            Dispatchers.IO.run {
                presenter.loadPersons()
            }
        } else {
            presenter.connectionError()
        }
    }

    override fun loadingSuccess(profiles: ArrayList<ProfileData>) {
        errorTextView.text = ""

        dataSet = profiles
        adapter = ProfileRecyclerViewAdapter(dataSet, this)
        recyclerView.adapter = adapter

        swipeRefreshLayout.isRefreshing = false
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
    }

    override fun loadingFail(error: String) {
        errorTextView.text = error

        swipeRefreshLayout.isRefreshing = false
        LoadingSwitch().stopLoading(loadingLayout, currentLayout)
    }

    override fun onItemLongClick(position: Int) {
        removableData.add(dataSet[position])
        dataSet.removeAt(position)
        adapter.notifyDataSetChanged()
    }
}