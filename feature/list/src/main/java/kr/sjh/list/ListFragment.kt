package kr.sjh.list

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kizitonwose.calendar.core.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kr.sjh.add.AddFragment
import kr.sjh.list.adapter.RvListAdapter
import kr.sjh.list.databinding.FragmentListBinding

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
        rvAdapter = RvListAdapter()

        binding.rvTodoList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }
        val bottomSheetDialog = AddFragment()
        lifecycleScope.launchWhenStarted {
            list.addEvent.collectLatest {
                Log.i("sjh", "visible ${bottomSheetDialog.isVisible}")
                if (it) {
                    bottomSheetDialog.show(parentFragmentManager, "BottomSheet")
                }else{

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