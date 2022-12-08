package de.egovt.evameg.utility

import org.osmdroid.util.GeoPoint


// pass all the data for the point
data class MapPin(var position : GeoPoint, var name : String)