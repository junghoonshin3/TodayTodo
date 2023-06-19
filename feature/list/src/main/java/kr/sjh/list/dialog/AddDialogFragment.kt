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
import kr.sjh.list.ListViewModel
import kr.sjh.list.R
import kr.sjh.list.databinding.FragmentDialogAddBinding
import kr.sjh.list.dialog.timepicker.AddTimePicker
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class AddDialogFragment(private var c: Calendar) : BottomSheetDialogFragment() {

    private val add: AddViewModel by viewModels(ownerProducer = { requireActivity() })

    private val list: ListViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var binding: FragmentDialogAddBinding

    private lateinit var timePickerDialog: AddTimePicker


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

        timePickerDialog = AddTimePicker.newInstance(c)

        observeData()

    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            add.isHourOpen.collect {
                if (it && !timePickerDialog.isAdded) {
                    timePickerDialog.show(childFragmentManager, "timepicker")
                } else if (!it && timePickerDialog.isAdded) {
                    timePickerDialog.dismiss()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            add._confirm.collect {
                if (it) {
                    binding.tvSelectTime.text =
                        String.format("%02d:%02d", add.hour.value, add.minute.value)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            add.save.collect {
                list.update()
                view?.let { it1 -> list.close(it1) }
            }
        }

        lifecycleScope.launchWhenStarted {
            add.isEmptyName.collect {
                if (it) {

                    Snackbar.make(
                        requireView().rootView,
                        "이름을 입력해주세요",
                        Snackbar.LENGTH_LONG
                    ).show()
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

    fun updateTimeNow() {
        this.c = Calendar.getInstance()
    }

    override fun onDismiss(dialog: DialogInterface) {
        binding.tieName.text?.clear()
        super.onDismiss(dialog)

    }


}