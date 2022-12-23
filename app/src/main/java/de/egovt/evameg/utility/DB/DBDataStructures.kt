package de.egovt.evameg.utility

import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

interface DataStructure {}

data class UserProfileData (var firstName:String = "", var lastName:String = "", var dateOfBirth: String = "", var wohnort: String = "", var postalCode: String = "", var street: String = "") : DataStructure {
    var id: Int=0
}

data class Office (val id:String, val name:String, val address:String, val type:String, val latitude:Double, val longitude:Double)  : DataStructure {

    constructor(id:String, name:String, address:String, type:String, latitude:Double, longitude:Double, map: MapView):this(id, name, address, type, latitude, longitude){

        marker = Marker(map)
        val location = GeoPoint(latitude, longitude)
        marker.position = location
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = name

    }

    lateinit var marker: Marker
}