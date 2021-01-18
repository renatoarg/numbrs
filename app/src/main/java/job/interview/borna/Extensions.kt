package job.interview.borna

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.convertToDate(pattern: String): String {
    val timeZoneUTC = TimeZone.getDefault()
    val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(this).time + offsetFromUTC)
}

fun Double.convertToCurrency(): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = 2
    format.currency = Currency.getInstance(Locale.getDefault())
    return format.format(this)
}