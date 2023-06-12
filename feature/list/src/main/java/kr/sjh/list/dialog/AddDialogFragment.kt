package kr.sjh.list.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kr.sjh.list.ListViewModel
import kr.sjh.list.R
import kr.sjh.list.databinding.FragmentDialogAddBinding
import kr.sjh.list.dialog.timepicker.AddTimePicker
import java.util.*


@AndroidEntryPoint
class AddDialogFragment(private val c: Calendar) : BottomSheetDialogFragment() {

    private val add: AddViewModel by viewModels()

    private val list: ListViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var binding: FragmentDialogAddBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDialogAddBinding.bind(view)
        binding.add = add
        binding.list = list


        val dialog = AddTimePicker(c).apply {
            binding.tvSelectTime.text = "${getHourValue()}:${getMinuteValue()}"
            setAddTimePickerListener { h, m ->
                binding.tvSelectTime.text = "$h:$m"
            }
        }

        lifecycleScope.launchWhenStarted {
            add.hour.collect {
                Log.i("sjh", "it : ${it}")
                if (it) {
                    dialog.show(childFragmentManager, "timepicker")
                } else {
                    dialog.dismiss()
                }
            }
        }


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.isDraggable = false //드레그 막기
                isCancelable = false // 영역 밖 클릭시 dismiss 막기
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = (resources.displayMetrics.heightPixels * 0.8).toInt()
        bottomSheet.layoutParams = layoutParams
    }

}