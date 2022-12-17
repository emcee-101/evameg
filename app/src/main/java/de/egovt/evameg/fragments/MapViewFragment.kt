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


data class MapPoint (val latitude:Double, val longitude:Double, val name:String, val id:String){

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

    // to manage State better
    lateinit var myMap : MapView
    var myMapPoints : Array<MapPoint> = arrayOf()

    // for Permissions
    private lateinit var thisView : View
    private lateinit var momentaryContext : Context

    // Call retFunk and destroy the fragment
    private lateinit var retFunk:(MapPoint?)-> MapPoint?
    private var funcThere:Boolean = false


    // For when the Mapview is used as a Location picker
    constructor(newMapPoints: Array<MapPoint>, returnFunction: (MapPoint?)-> MapPoint?) : this(){

        retFunk = returnFunction
        funcThere = true

        myMapPoints = newMapPoints
    }

    // STANDARD STUFF
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // save View for Permissions
        thisView = inflater.inflate(R.layout.activity_mapview, container, false)
        return thisView
    }

    // gather Context for Permissions
    override fun onAttach(context: Context) {
        super.onAttach(context)
        momentaryContext = context

    }

    // Set up Map
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkMapPermissions()

        // Map Configuration
        Configuration.getInstance().userAgentValue = activity?.packageName
        myMap = thisView.findViewById(R.id.coolMap)
        myMap.setTileSource(TileSourceFactory.MAPNIK)

        // Set Controls for Map
        val controller = myMap.controller
        controller.setZoom(18.5)
        val mapPointFHErfurt = GeoPoint(50.985167884281026, 11.041366689707237)
        controller.setCenter(mapPointFHErfurt)
        myMap.setMultiTouchControls(true)


        // todo CHANGE LOOK OF PIN FINALLY
        // change look startMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
        // https://osmdroid.github.io/osmdroid/Markers,-Lines-and-Polygons.html


        // Draw the Marker on the Map
        if(myMapPoints.isNotEmpty()){

            // add Point to Map Overlay
            for(point in myMapPoints){

                point.marker.setOnMarkerClickListener { marker, mapview -> onMarkerClickety(marker, mapview) }
                myMap.overlays.add(point.marker)

            }
        } else {
            // Draw Standard Marker
            myMapPoints = arrayOf(MapPoint(50.985167884281026, 11.041366689707237, "FH ERFURT", "1", myMap))
            myMapPoints[0].marker.setOnMarkerClickListener { marker, mapview -> onMarkerClickety(marker, mapview) }
            myMap.overlays.add(myMapPoints[0].marker)

        }


    }

    private fun identifyMapPoint(marker: Marker): MapPoint? {
        return myMapPoints.find { it.marker == marker }
    }

    private fun onMarkerClickety(marker:Marker, map: MapView):Boolean{

        val myMapPoint:MapPoint? = identifyMapPoint(marker)

        if (myMapPoint != null) {
            Log.i("a", "Point with ID of ${myMapPoint.id} was clicked")
        }

        // TODO add ALERT-BOX for confirmation

        // If a Function to Return a Value was given, return a MapPoint? with it
        if(funcThere)

            retFunk(myMapPoint)


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

        myMap.onResume()
    }

    override fun onPause() {
        super.onPause()

        myMap.onPause()
    }

}

