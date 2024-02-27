package br.com.renovatiu.cinedrivein.core.functions

import br.com.renovatiu.cinedrivein.data.remote.model.request.SessionRequest
import com.google.firebase.firestore.memoryEagerGcSettings
import java.time.LocalDate
import java.util.Calendar
import java.util.UUID

fun extractMoviesName(sessions: List<SessionRequest>?) = sessions?.joinToString(
    separator = ", ",
    transform = { it.movies?.firstOrNull()?.title ?: "" }
) ?: ""

fun extractTicketsQuantity(sessions: List<SessionRequest>?) = sessions?.sumBy { session ->
    session.seats?.sumBy { seat ->
        seat.ticketsSold?.sumBy { ticket ->
            ticket.quantitySold ?: 0
        } ?: 0
    }!!
} ?: 0

fun extractTicketsValue(sessions: List<SessionRequest>?) : Float {
    var value = 0f

    sessions?.forEach { session ->
        session.seats?.forEach { seat ->
            seat.ticketsSold?.forEach { ticket ->
                ticket.revenue?.forEach { revenue ->
                    revenue.totalRecieved?.let {
                        value += it
                    }
                }
            }
        }
    }

    return value
}

fun generateUniqueId(): String {
    val timestamp = System.currentTimeMillis()
    val uuid = UUID.randomUUID().toString()
    return timestamp.toString()+uuid.replace("-", "")
}

fun getSelectedDate(month: Int, year: Int): String {
    val month = month + 1
    val year = year + 1900

    val monthName = when (month) {
        1 -> "JAN"
        2 -> "FEV"
        3 -> "MAR"
        4 -> "ABR"
        5 -> "MAI"
        6 -> "JUN"
        7 -> "JUL"
        8 -> "AGO"
        9 -> "SET"
        10 -> "OUT"
        11 -> "NOV"
        12 -> "DEZ"
        else -> ""
    }
    return "$monthName/$year"
}

fun getMonthByString(month: String) : String {
    return when(month) {
        "JAN" -> "01"
        "FEV" -> "02"
        "MAR" -> "03"
        "ABR" -> "04"
        "MAI" -> "05"
        "JUN" -> "06"
        "JUL" -> "07"
        "AGO" -> "08"
        "SET" -> "09"
        "OUT" -> "10"
        "NOV" -> "11"
        "DEZ" -> "12"
        else  -> "0"
    }
}
