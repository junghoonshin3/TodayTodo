package kr.sjh.main.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.core.content.ContextCompat.startActivity
import kr.sjh.main.receiver.TodoAlarmReceiver
import org.joda.time.DateTime

class TodoAlarmManager constructor(private val context: Context) {

    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager



    // Android 12 이상부터 알람 권한체크
    fun canScheduleExactAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    //알람 권한설정 화면으로 이동
    fun openSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            context.startActivity(
                Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM).addFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                )
            )
        }
    }

    private fun createExactAlarmIntent(): PendingIntent {
        val intent = Intent(context, TodoAlarmReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            101,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun setExactAlarmSetAlarmClock(triggerTime: Long) {
        val pendingIntent = createExactAlarmIntent()
        val alarmClockInfo =
            AlarmManager.AlarmClockInfo(triggerTime, pendingIntent)
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent)
    }

    fun clearExactAlarm() {
        val pendingIntent = createExactAlarmIntent()
        alarmManager.cancel(pendingIntent) // 2
    }
}