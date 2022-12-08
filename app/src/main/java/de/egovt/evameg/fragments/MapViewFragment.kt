package de.egovt.evameg.fragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import de.egovt.evameg.R
import de.egovt.evameg.databinding.ActivityMapviewBinding
import de.egovt.evameg.utility.MapPin
import de.egovt.evameg.utility.activityIsPermissionGiven
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MapViewFragment: Fragment(R.layout.activity_mapview) {

    private lateinit var myMap : MapView
    private lateinit var mapPins : Array<MapPin>
    private lateinit var momentaryContext : Context

    override fun onAttach(context: Context) {
        super.onAttach(context)

        momentaryContext = context

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkMapPermissions()

        Configuration.getInstance().userAgentValue = activity?.packageName

        val binding = ActivityMapviewBinding.inflate(layoutInflater)

        myMap = requireView().findViewById(R.id.mapview)
        myMap.setTileSource(TileSourceFactory.MAPNIK)


        val controller = myMap.controller
        controller.setZoom(18.5)

        val mapPointFHErfurt = GeoPoint(50.985167884281026, 11.041366689707237)
        controller.setCenter(mapPointFHErfurt)

        // "How do I place icons on the map with a click listener?"
        // https://osmdroid.github.io/osmdroid/How-to-use-the-osmdroid-library.html



    }

    fun addPins(points : Array<MapPin>){

        // todo logic for adding pin  + callback

    }

    private fun checkMapPermissions(): Boolean {

        val neededPermissions = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_WIFI_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE)

        return activityIsPermissionGiven(momentaryContext, neededPermissions, momentaryContext as Activity)

    }

}

