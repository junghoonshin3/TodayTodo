package kr.sjh.list.dialog

import android.app.Dialog
import android.content.DialogInterface
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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kr.sjh.list.ListViewModel
import kr.sjh.list.R
import kr.sjh.list.databinding.FragmentDialogAddBinding
import kr.sjh.list.dialog.timepicker.AddTimePicker
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddDialogFragment : BottomSheetDialogFragment() {

    private val add: AddViewModel by viewModels(ownerProducer = { requireActivity() })

    private val list: ListViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var binding: FragmentDialogAddBinding

    private lateinit var timePickerDialog: AddTimePicker


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogAddBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            add = this@AddDialogFragment.add
            list = this@AddDialogFragment.list
        }

        timePickerDialog = AddTimePicker()

        observeData()

    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            add.timePickerOpen.collect {
                if (it) {
                    timePickerDialog.show(childFragmentManager, "timepicker")
                } else {
                    timePickerDialog.dismiss()
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

    override fun onDismiss(dialog: DialogInterface) {
        binding.tieName.text?.clear()
        super.onDismiss(dialog)
    }


}