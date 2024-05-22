package com.compose.samples.replicas.crane.data

class DestinationsRepository {

    private val destinationsLocalDataSource: DestinationsLocalDataSource = DestinationsLocalDataSource()

    val destinations: List<ExploreModel> = destinationsLocalDataSource.craneDestinations
    val hotels: List<ExploreModel> = destinationsLocalDataSource.craneHotels
    val restaurants: List<ExploreModel> = destinationsLocalDataSource.craneRestaurants
    val cities: List<City> = listCities

    fun getDestination(cityName: String): City? {
        return cities.firstOrNull { it.name == cityName }
    }

}