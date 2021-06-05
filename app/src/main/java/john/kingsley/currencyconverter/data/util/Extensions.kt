package john.kingsley.currencyconverter.data.util

import android.content.Context
import android.text.format.DateFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.util.*

inline fun <T> Flow<T>.handleError(crossinline action: (value: String) -> Unit): Flow<T> =
    catch { e -> action(e.localizedMessage ?: "") }


fun Long.getFormattedDate(context: Context): String {
    val smsTime = Calendar.getInstance()
    smsTime.timeInMillis = this*1000

    val now = Calendar.getInstance()

    val timeFormatString = "h:mm aa"
    val dateTimeFormatString = "EEEE, MMMM d, h:mm aa"
    val HOURS = (60 * 60 * 60).toLong()
    return if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
        "Today " + DateFormat.format(timeFormatString, smsTime)
    } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
        "Yesterday " + DateFormat.format(timeFormatString, smsTime)
    } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
        DateFormat.format(dateTimeFormatString, smsTime).toString()
    } else {
        DateFormat.format("MMMM dd yyyy", smsTime).toString()
    }
}