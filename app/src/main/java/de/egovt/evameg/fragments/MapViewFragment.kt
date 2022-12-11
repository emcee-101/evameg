package de.egovt.evameg.fragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.egovt.evameg.R
import de.egovt.evameg.utility.activityIsPermissionGiven
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


class MapViewFragment(): Fragment() {

    constructor(newMarkers : Array<Marker>) : this() {

        // todo add functionality to add them to map, correctly configured
        myMarkers = newMarkers

    }

    private lateinit var thisView : View
    private lateinit var myMap : MapView
    private lateinit var myMarkers : Array<Marker>
    private lateinit var momentaryContext : Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        thisView = inflater.inflate(R.layout.activity_mapview, container, false)
        return thisView
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        momentaryContext = context

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkMapPermissions()

        Configuration.getInstance().userAgentValue = activity?.packageName


        myMap = thisView.findViewById(R.id.coolMap)
        myMap.setTileSource(TileSourceFactory.MAPNIK)


        val controller = myMap.controller
        controller.setZoom(18.5)

        val mapPointFHErfurt = GeoPoint(50.985167884281026, 11.041366689707237)
        controller.setCenter(mapPointFHErfurt)

        // "How do I place icons on the map with a click listener?"
        // https://osmdroid.github.io/osmdroid/How-to-use-the-osmdroid-library.html


        val startMarker = Marker(myMap)

        startMarker.setOnMarkerClickListener { marker, mapview -> onMarkerClickety(marker, mapview) }
        startMarker.position = mapPointFHErfurt
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)



        myMap.overlays.add(startMarker)
        // todo look of pin
        // change look startMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
        // https://osmdroid.github.io/osmdroid/Markers,-Lines-and-Polygons.html
        startMarker.title = "Start point"
    }

    private fun onMarkerClickety(marker:Marker, map: MapView):Boolean{

        Log.i("aaaaaaaaaaaaaa", "hallo marker 1111111111111111111")

        // todo do something useful

        return true
    }

    // todo call onPause and OnResume

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

