package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.data.MarsPhotosRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.marsphotos.MarsPhotosApplication


/**
 * UI state for the Home screen
 */
sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    data class Error(val message: String) : MarsUiState
    object Loading : MarsUiState
}

/**
 * ViewModel for managing Mars photos state and retrieving data using a repository.
 */
class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel(){

    /** The mutable state that stores the status of the most recent request */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    /**
     * Gets Mars photos information from the repository and updates the UI state.
     */
    fun getMarsPhotos() {

        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                val photos = marsPhotosRepository.getMarsPhotos()
                MarsUiState.Success(photos)
            } catch (e: IOException) {
                MarsUiState.Error("Network error: ${e.message}")
            } catch (e: HttpException) {
                MarsUiState.Error("Server error: ${e.message()}")
            }

        }

    }


companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
            val marsPhotosRepository = application.container.marsPhotosRepository
            MarsViewModel(marsPhotosRepository = marsPhotosRepository)
        }
    }
}}
