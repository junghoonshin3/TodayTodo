package kr.sjh.list

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kizitonwose.calendar.core.*
import dagger.hilt.android.AndroidEntryPoint
import kr.sjh.domain.model.Todo
import kr.sjh.list.adapter.ListDividerItemDeco
import kr.sjh.list.adapter.RvListAdapter
import kr.sjh.list.common.BaseFragment
import kr.sjh.list.databinding.FragmentListBinding
import kr.sjh.list.dialog.AddDialogFragment
import kr.sjh.list.listener.ItemClickListener
import java.util.*

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(R.layout.fragment_list) {

    private val list: ListViewModel by viewModels()

    lateinit var dialog: AddDialogFragment

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = AddDialogFragment()
        with(binding) {
            vmList = list
            adapter = RvListAdapter(list)
            itemDeco = ListDividerItemDeco()
        }

        observeData()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            list.openAdd.collect {
                if (it) {
                    dialog.show(childFragmentManager, "addDialog")
                } else {
                    dialog.dismiss()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            list.isEmptyName.collect {
                if (it) {
                    Snackbar.make(
                        dialog.requireView().rootView,
                        "이름을 입력해주세요",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

            }
        }
    }

}