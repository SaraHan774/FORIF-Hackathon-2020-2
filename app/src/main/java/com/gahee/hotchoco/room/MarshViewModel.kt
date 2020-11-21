package com.gahee.hotchoco.room

import androidx.lifecycle.*
import com.gahee.hotchoco.model.MarshMallow
import kotlinx.coroutines.launch

class MarshViewModel(private val repository: MarshRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allMarshMallows: LiveData<List<MarshMallow>> = repository.allMarshMallows.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(marshMallow: MarshMallow) = viewModelScope.launch {
        repository.insert(marshMallow)
    }
}

class MarshViewModelFactory(private val repository: MarshRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MarshViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MarshViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
