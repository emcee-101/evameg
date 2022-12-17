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


data class mapPoint (val latitude:Double, val longitude:Double, val name:String, val id:String){

    constructor(latitude:Double, longitude:Double, name:String, id:String, map:MapView) : this(latitude,  longitude,  name,  id) {
        marker = Marker(map)
        val location = GeoPoint(latitude, longitude)
        marker.position = location
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = name

    }

    lateinit var marker:Marker
}

class MapViewFragment(): Fragment() {

    lateinit var myMap : MapView
    lateinit var myMapPoints : Array<mapPoint>

    // todo ask Mo what to get here
    lateinit var myFunk:Function<mapPoint>

    private lateinit var thisView : View
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



        // todo look of pin
        // change look startMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
        // https://osmdroid.github.io/osmdroid/Markers,-Lines-and-Polygons.html

        myMap.setMultiTouchControls(true);

        if(myMapPoints.isNotEmpty()){

            // add Point to Map Overlay
            for(point in myMapPoints){

                point.marker.setOnMarkerClickListener { marker, mapview -> onMarkerClickety(marker, mapview) }
                myMap.overlays.add(point.marker)

            }
        } else {

            val startMarker = Marker(myMap)
            startMarker.position = mapPointFHErfurt
            startMarker.setOnMarkerClickListener { marker, mapview -> onMarkerClickety(marker, mapview) }
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            myMap.overlays.add(startMarker)

        }


    }

    private fun identifyMapPoint(marker: Marker): mapPoint? {
        val myMapPoint:mapPoint? = myMapPoints.find {it.marker == marker}
        return myMapPoint
    }

    private fun onMarkerClickety(marker:Marker, map: MapView):Boolean{

        val mapPoint:mapPoint? = identifyMapPoint(marker)

        if (mapPoint != null) {
            Log.i("a", "Point with ID of ${mapPoint.id} was clicked")
        }

        // todo myFunk(mapPoint)

        return true
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

    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        myMap.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        myMap.onPause() //needed for compass, my location overlays, v6.0.0 and up
    }

}

