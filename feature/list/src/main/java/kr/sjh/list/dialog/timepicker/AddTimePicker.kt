package kr.sjh.list.dialog.timepicker

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
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

class AddTimePicker : DialogFragment() {

    private val add: AddViewModel by viewModels(ownerProducer = { requireActivity() })

    private lateinit var binding: TimepickerAlertDialogTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TimepickerAlertDialogTwoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.sharedAdd = add
    }
}