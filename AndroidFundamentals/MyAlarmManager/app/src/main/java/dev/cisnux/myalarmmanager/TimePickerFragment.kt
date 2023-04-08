package dev.cisnux.myalarmmanager

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private typealias OnDialogTimeSet = (String?, Int, Int) -> Unit

class TimePickerFragment(private inline val onDialogTimeSet: OnDialogTimeSet) : DialogFragment(),
    TimePickerDialog.OnTimeSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val formatHour24 = true
        return TimePickerDialog(activity, this, hour, minute, formatHour24)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        onDialogTimeSet(tag, hourOfDay, minute)
    }
}