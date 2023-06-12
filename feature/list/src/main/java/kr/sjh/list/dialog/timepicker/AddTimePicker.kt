package kr.sjh.list.dialog.timepicker

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.textview.MaterialTextView
import kr.sjh.list.R
import kr.sjh.list.databinding.TimepickerAlertDialogTwoBinding
import kr.sjh.list.dialog.AddViewModel
import java.util.Calendar

class AddTimePicker(private val c: Calendar) : DialogFragment() {

    private var setHourValue: Int = 0

    private var setMinuteValue: Int = 0

    private val add: AddViewModel by viewModels(ownerProducer = { requireParentFragment() })

    private lateinit var addTimePickerListener: (Int, Int) -> Unit

    private lateinit var binding: TimepickerAlertDialogTwoBinding

    fun setHourValue(setHourValue: Int) {
        this.setHourValue = setHourValue
    }

    fun setMinuteValue(setMinuteValue: Int) {
        this.setMinuteValue = setMinuteValue
    }

    fun getHourValue() = setHourValue

    fun getMinuteValue() = setMinuteValue

    fun setAddTimePickerListener(listener: (Int, Int) -> Unit) {
        this.addTimePickerListener = listener
    }

    init {

        setHourValue(c.get(Calendar.HOUR_OF_DAY))
        setMinuteValue(c.get(Calendar.MINUTE))
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
            hour = c.get(Calendar.HOUR_OF_DAY)
            minute = c.get(Calendar.MINUTE)

            setOnTimeChangedListener { view, hourOfDay, minute ->
                setHourValue(hourOfDay)
                setMinuteValue(minute)
                addTimePickerListener.invoke(hourOfDay, minute)

            }
        }

    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.timepicker_alert_dialog_two)
//
//        val timePicker = findViewById<TimePicker>(R.id.tp_time_spinner)
//
//        timePicker.apply {
//            setIs24HourView(true)
//            hour = setHourValue
//            minute = setMinuteValue
//            setOnTimeChangedListener { view, hourOfDay, minute ->
//                setHourValue(hourOfDay)
//                setMinuteValue(minute)
//            }
//        }
//
//        val positive = findViewById<MaterialTextView>(R.id.mv_ok)
//        positive.setOnClickListener {
//            addTimePickerListener.invoke(setHourValue, setMinuteValue)
//            dismiss()
//        }
//        val nagative = findViewById<MaterialTextView>(R.id.mv_cancle)
//        nagative.setOnClickListener {
//
//            dismiss()
//        }
//
//    }
}