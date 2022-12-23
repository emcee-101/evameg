package de.egovt.evameg.fragments


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import de.egovt.evameg.R
import de.egovt.evameg.utility.MapPoint
import de.egovt.evameg.utility.Office
import de.egovt.evameg.utility.activityIsPermissionGiven
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker




class MapViewFragment(): Fragment() {

    // to manage State better
    lateinit var myMap : MapView
    lateinit var MapIDs : Array<String>
    var myMapPoints : Array<Office> = arrayOf()

    // for Permissions
    private lateinit var thisView : View
    private lateinit var momentaryContext : Context

    // Call retFunk and destroy the fragment (FORM COMPONENT)
    private lateinit var retFunk:(String?)-> String?
    private var funcThere:Boolean = false


    // Gets called like this from the Form Component
    constructor(newMapIDs:Array<String>, returnFunction: (String?)-> String?) : this(){

        MapIDs = newMapIDs
        retFunk = returnFunction
        funcThere = true

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
        myMap.setMultiTouchControls(true)

        // Start Point
        val mapPointFHErfurt = GeoPoint(50.985167884281026, 11.041366689707237)
        controller.setCenter(mapPointFHErfurt)


        // todo CHANGE LOOK OF PIN FINALLY
        // change look startMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
        // https://osmdroid.github.io/osmdroid/Markers,-Lines-and-Polygons.html


        // Draw the Markers on the Map
        if(myMapPoints.isNotEmpty()){

            drawMapPoints()

        } else {

            if(MapIDs.isNotEmpty()){

                // Read Data from DB and add Markers
                myMapPoints = readMarkerData(MapIDs)

            } else {

                // Add Standard Marker
                myMapPoints = arrayOf(Office(50.985167884281026, 11.041366689707237, "FH ERFURT", "1", myMap))

            }

            drawMapPoints()

        }


    }

    private fun drawMapPoints():Boolean{

        // add Point to Map Overlay
        for(point in myMapPoints){

            point.marker.setOnMarkerClickListener { marker, mapview -> onMarkerClickety(marker, mapview) }
            myMap.overlays.add(point.marker)

        }
        return true
    }

    private fun readMarkerData(IDs : Array<String>):Array<Office>{

        // Example return
        return arrayOf(Office(0.0,0.0,"Buxtehude","aaa"))
    }

    private fun identifyMapPoint(marker: Marker): Office? {
        return myMapPoints.find { it.marker == marker }
    }

    private fun onMarkerClickety(marker:Marker, map: MapView):Boolean{

        val myMapPoint:Office? = identifyMapPoint(marker)

        if (myMapPoint != null) {
            Log.i("a", "Point with ID of ${myMapPoint.id} was clicked")


            val builder = AlertDialog.Builder(context)
            builder
                .setCancelable(true)
                .setTitle(myMapPoint.name)

                // TODO add further information to Dialog
                .setMessage("OMG ES FUNCITONIERT OMGOMGOMG")
                .setPositiveButton(R.string.yes) { dialogInterface: DialogInterface, i: Int ->
                    run {

                        if(funcThere){

                            // If a Function to Return a Value was given, return the ID
                            retFunk(myMapPoint.id)
                            Log.i("a", "The passed Function was called")
                        } else Log.i("a", "The passed Function was not called since there isnt one")

                    }
                }
                .create()
                .show()
        }

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

