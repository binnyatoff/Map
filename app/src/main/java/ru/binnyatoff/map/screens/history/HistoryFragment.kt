package ru.binnyatoff.map.screens.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.binnyatoff.map.R
import ru.binnyatoff.map.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment(R.layout.fragment_map) {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyFragmentVM: HistoryFragmentVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = HistoryAdapter()
        binding.historyList.adapter = adapter
        binding.historyList.layoutManager = LinearLayoutManager(requireContext())
        historyFragmentVM = ViewModelProvider(this).get(HistoryFragmentVM::class.java)
        historyFragmentVM.readAllData()
        historyFragmentVM.coordinats.observe(viewLifecycleOwner, {
            adapter.setData(it)
        })
    }
}