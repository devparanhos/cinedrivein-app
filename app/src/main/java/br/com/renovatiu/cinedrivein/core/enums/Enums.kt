package br.com.renovatiu.cinedrivein.core.enums

import androidx.compose.ui.graphics.Color
import br.com.renovatiu.cinedrivein.ui.theme.Green
import br.com.renovatiu.cinedrivein.ui.theme.LigthRed
import br.com.renovatiu.cinedrivein.ui.theme.Orange
import br.com.renovatiu.cinedrivein.ui.theme.Primary

enum class Modality(val type: String) {
    A("Sessão regular"),
    B("Pré-Estreia"),
    C("Sessão de Mostra ou Festiva"),
    D("Sessão Privada");
}

enum class TypeStatus(val acronym: String, val description: String, val color: Color) {
    A("A","Em análise", Primary),
    N("N","Não acatado", Color.Black),
    E("E","Com erro", LigthRed),
    V("V","Validado", Green),
    R("R","Recusado", LigthRed),
    X("X","Não enviado", Orange);

    companion object {
        fun fromAcronym(acronym: String?): TypeStatus {
            return values().find { it.acronym == acronym } ?: X
        }
    }
}