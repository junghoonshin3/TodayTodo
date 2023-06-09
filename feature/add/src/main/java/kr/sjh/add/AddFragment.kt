package kr.sjh.add

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView.OnDismissListener
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kr.sjh.add.databinding.FragmentAddBinding
import kr.sjh.add.timepicker.AddTimePicker
import java.util.*


@AndroidEntryPoint
class AddFragment : BottomSheetDialogFragment() {

    private val add: AddViewModel by viewModels()

    private lateinit var binding: FragmentAddBinding

    lateinit var onDismissListener: () -> Unit


    fun setOnDismissListener(listener: () -> Unit) {
        this.onDismissListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        val dialog = AddTimePicker(requireContext()).apply {
            setAddTimePickerListener { h, m ->
                binding.tvSelectTime.text = "$h:$m"
            }
        }

        binding.llSelectTime.setOnClickListener {
            val today = Calendar.getInstance()
            Log.i(
                "sjh",
                "hour : ${today.get(Calendar.HOUR_OF_DAY)} minute: ${today.get(Calendar.MINUTE)}"
            )

            dialog.apply {
                setHourValue(today.get(Calendar.HOUR_OF_DAY))
                setMinuteValue(today.get(Calendar.MINUTE))
                show()
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
                behaviour.isDraggable = false
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = (resources.displayMetrics.heightPixels * 0.8).toInt()
        bottomSheet.layoutParams = layoutParams
    }

    override fun dismiss() {
        super.dismiss()
        onDismissListener.invoke()
    }

}