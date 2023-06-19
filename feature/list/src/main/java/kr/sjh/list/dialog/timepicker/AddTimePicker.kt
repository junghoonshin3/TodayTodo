package kr.sjh.list.dialog.timepicker

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kr.sjh.list.R
import kr.sjh.list.databinding.TimepickerAlertDialogTwoBinding
import kr.sjh.list.dialog.AddViewModel
import java.util.*

class AddTimePicker(private val c: Calendar) : DialogFragment() {

    private val add: AddViewModel by viewModels(ownerProducer = { requireActivity() })

    private lateinit var binding: TimepickerAlertDialogTwoBinding

    companion object {
        fun newInstance(c: Calendar) = AddTimePicker(c)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.timepicker_alert_dialog_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = TimepickerAlertDialogTwoBinding.bind(view)
        binding.sharedAdd = add


        binding.tpTimeSpinner.apply {
            setIs24HourView(true)
            lifecycleScope.launchWhenStarted {
                add.hour.collect {
                    hour = String.format("%02d", it).toInt()
                }
            }
            lifecycleScope.launchWhenStarted {
                add.minute.collect {
                    minute = String.format("%02d", it).toInt()
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        lifecycleScope.launchWhenStarted {
            add._confirm.emit(false)
        }

    }
}