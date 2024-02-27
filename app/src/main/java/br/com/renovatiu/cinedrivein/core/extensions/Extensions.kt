package br.com.renovatiu.cinedrivein.core.extensions

import br.com.renovatiu.cinedrivein.core.functions.getMonthByString

fun String.setCnpjMask() : String {
    val regex = Regex("""(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})""")
    return regex.replace(this, "$1.$2.$3/$4-$5")
}

fun Boolean.convertToAudioText() : String {
    return if (this) {
        "O"
    } else {
        "D"
    }
}

fun Boolean.convertToYesNo() : String {
    return if (this) {
        "S"
    } else {
        "N"
    }
}

fun String.toFormattedDate(): String {
    val parts = this.split("/")
    if (parts.size != 3) {
        throw IllegalArgumentException("Data inválida: $this")
    }
    return "${parts[2]}-${parts[1]}-${parts[0]}"
}

fun Float.formatToReal(): String {
    val formattedValue = String.format("%.2f", this)
    return "R$ $formattedValue".replace(".", ",")
}

fun String.formatDateForQuery(): String {
    val month = getMonthByString(this.substring(0, 3))
    val year = this.substring(4, 8)
    return "$year-$month"
}

fun String.formatDateBR(): String {
    val parts = this.split("-")
    return "${parts[2]}/${parts[1]}/${parts[0]}"
}
