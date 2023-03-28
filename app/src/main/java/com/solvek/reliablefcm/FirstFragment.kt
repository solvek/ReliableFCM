package com.solvek.reliablefcm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.solvek.reliablefcm.data.LogRecord
import com.solvek.reliablefcm.databinding.FragmentFirstBinding
import com.solvek.reliablefcm.utils.formatTime
import kotlinx.coroutines.launch

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = requireContext().logDao

        lifecycleScope.launch {
            dao.get(100).collect {records ->
                if (records.isEmpty()){
                    binding.log.setText(R.string.no_logs)
                    return@collect
                }
                populateLogRecords(records)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun populateLogRecords(records: List<LogRecord>) {
        with(binding.log) {
            text = ""
            records.forEach {record ->
                append("${record.timestamp.formatTime()}: ${record.text}\n")
            }
        }
    }
}