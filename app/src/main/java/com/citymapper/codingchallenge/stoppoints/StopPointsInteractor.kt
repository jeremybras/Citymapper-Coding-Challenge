package com.citymapper.codingchallenge.stoppoints

import android.util.Log

interface StopPointsInteractor {
    fun loadStopPoints(altitude: Double, longitude: Double)
    fun loadArrivalTimes()
}

interface ArrivalTimesListener {
    fun onArrivalTimesLoaded(stopPointId: String, arrivalTimes: List<Arrival>)
}

class StopPointsInteractorImpl(
    private val presenter: StopPointsPresenter,
    private val repository: StopPointsRepository
) : StopPointsInteractor, ArrivalTimesListener {

    private var stopPoints: List<StopPoint> = emptyList()

    override fun loadStopPoints(altitude: Double, longitude: Double) {
        Log.d("GPS", "Loading gps coordinates $altitude:$longitude")
        stopPoints = repository.loadStopPoints(altitude, longitude)
        presenter.presentStopPoints(stopPoints)
    }

    override fun loadArrivalTimes() {
        repository.loadArrivalTimes(stopPoints, this)
    }

    override fun onArrivalTimesLoaded(stopPointId: String, arrivalTimes: List<Arrival>) {
        println("Altering stopPoint #$stopPointId with arrival times $arrivalTimes")
        stopPoints = stopPoints.map {
            if (it.id == stopPointId) {
                StopPoint(
                    id = it.id,
                    name = it.name,
                    arrivalTimes = arrivalTimes
                )
            } else {
                it
            }
        }
        presenter.presentStopPoints(stopPoints)
    }
}
