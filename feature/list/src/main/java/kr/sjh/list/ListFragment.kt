package kr.sjh.list

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendar.core.*
import dagger.hilt.android.AndroidEntryPoint
import kr.sjh.domain.model.Todo
import kr.sjh.list.adapter.ListDividerItemDeco
import kr.sjh.list.adapter.RvListAdapter
import kr.sjh.list.databinding.FragmentListBinding
import kr.sjh.list.dialog.AddDialogFragment
import kr.sjh.list.listener.ItemClickListener
import java.util.*

@AndroidEntryPoint
class ListFragment : Fragment() {
    companion object {
        fun newInstance() = ListFragment()
    }

    private val list: ListViewModel by viewModels()

    private lateinit var binding: FragmentListBinding

    private lateinit var rvAdapter: RvListAdapter

    private lateinit var addDialogFragment: AddDialogFragment


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
        rvAdapter = RvListAdapter(list)
        binding.rvTodoList.apply {
            addItemDecoration(ListDividerItemDeco())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }

        addDialogFragment = AddDialogFragment(Calendar.getInstance())
        observeData()
    }

    private fun observeData() {

        lifecycleScope.launchWhenStarted {
            list.openAdd.collect {
                if (it && !addDialogFragment.isAdded) {
                    addDialogFragment.updateTimeNow()
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