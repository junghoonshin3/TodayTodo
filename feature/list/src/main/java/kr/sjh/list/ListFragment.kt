package kr.sjh.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendar.core.*
import dagger.hilt.android.AndroidEntryPoint
import kr.sjh.list.adapter.RvListAdapter
import kr.sjh.list.databinding.FragmentListBinding
import kr.sjh.list.dialog.AddDialogFragment
import java.util.*

@AndroidEntryPoint
class ListFragment : Fragment() {
    companion object {
        fun newInstance() = ListFragment()
    }

    private val list: ListViewModel by viewModels()

    private lateinit var binding: FragmentListBinding

    private lateinit var rvAdapter: RvListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListBinding.bind(view)
        binding.vmList = list
        rvAdapter = RvListAdapter()

        binding.rvTodoList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }

        val addDialogFragment = AddDialogFragment(Calendar.getInstance())

        lifecycleScope.launchWhenStarted {
            list.openAdd.collect {
                if (it) {
                    addDialogFragment.show(childFragmentManager, "addDialog")
                } else {
                    addDialogFragment.dismiss()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            list.todoList.collect {
                rvAdapter.submitList(it)
            }
        }


    }
}