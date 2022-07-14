package states

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import models.Note
import models.getNotes
import mu.KotlinLogging
import kotlin.concurrent.thread

private val logger = KotlinLogging.logger {}

// Y si lo pongo como object tendría un singleton
object AppState {
    var state by mutableStateOf(UiState())
    fun loadNotes() {
        //debemos hacerlo en un hilo aparte para no bloquear, hasta qu eveamos las corrutinas
        thread {
            state = UiState(isLoading = true)
            logger.debug { "Cargando notas" }
            // No es necesario ya que el is Loading es false
            getNotes { state = UiState(notes = it, isLoading = false) }
            logger.debug { "Notas cargadas" }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val notes: List<Note>? = null // emptyList() // Prefiero el emty list
    )
}