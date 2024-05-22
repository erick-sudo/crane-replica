package com.compose.samples.replicas.crane.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.samples.replicas.crane.data.DestinationsRepository
import com.compose.samples.replicas.crane.data.ExploreModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.util.Random

const val MAX_PEOPLE = 4

class MainViewModel: ViewModel() {

    private val destinationsRepository: DestinationsRepository = DestinationsRepository()

    val shownSplash = mutableStateOf(SplashState.Shown)

    val hotels: List<ExploreModel> = destinationsRepository.hotels
    val restaurants: List<ExploreModel> = destinationsRepository.restaurants

    private val _suggestedDestinations = MutableLiveData<List<ExploreModel>>()

    val suggestedDestinations: LiveData<List<ExploreModel>>
        get() = _suggestedDestinations

    init {
        _suggestedDestinations.value = destinationsRepository.destinations
    }

    fun onDaySelected(daySelected: LocalDate) {
        viewModelScope.launch {  }
    }

    fun updatePeople(people: Int) {
        viewModelScope.launch {
            if(people > MAX_PEOPLE) {
                _suggestedDestinations.value = emptyList()
            } else {
                val newDestinations = runBlocking {
                    destinationsRepository.destinations.shuffled(
                        Random(people.toLong() * (1..100).shuffled().first())
                    )
                }

                _suggestedDestinations.value = newDestinations
            }
        }
    }

    fun toDestinationChanged(newDestination: String) {
        viewModelScope.launch {
            val newDestinations = runBlocking {
                destinationsRepository.destinations
                    .filter { it.city.nameToDisplay.contains(newDestination) }
            }
            _suggestedDestinations.value = newDestinations
        }
    }
}