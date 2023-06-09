package kr.sjh.add.timepicker

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import com.google.android.material.textview.MaterialTextView
import kr.sjh.add.R

class AddTimePicker constructor(private val context: Context) : Dialog(context) {

    private var setHourValue: Int = 0

    private var setMinuteValue: Int = 0

    private lateinit var addTimePickerListener: (Int, Int) -> Unit

    fun setHourValue(setHourValue: Int) {
        this.setHourValue = setHourValue
    }

    fun setMinuteValue(setMinuteValue: Int) {
        this.setMinuteValue = setMinuteValue
    }

    fun setAddTimePickerListener(listener: (Int, Int) -> Unit) {
        this.addTimePickerListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timepicker_alert_dialog_two)

        val timePicker = findViewById<TimePicker>(R.id.tp_time_spinner)

        timePicker.apply {
            setIs24HourView(true)
            hour = setHourValue
            minute = setMinuteValue
            setOnTimeChangedListener { view, hourOfDay, minute ->
                setHourValue(hourOfDay)
                setMinuteValue(minute)

            }
        }

        val positive = findViewById<MaterialTextView>(R.id.mv_ok)
        positive.setOnClickListener {
            addTimePickerListener.invoke(setHourValue, setMinuteValue)
            dismiss()
        }
        val nagative = findViewById<MaterialTextView>(R.id.mv_cancle)
        nagative.setOnClickListener {

            dismiss()
        }

    }
}