package com.splendo.components.location

interface LocationListener {
    fun onLocationUpdate(location: Location)
    fun onAvailabilityUpdate(availability: Availability)
}