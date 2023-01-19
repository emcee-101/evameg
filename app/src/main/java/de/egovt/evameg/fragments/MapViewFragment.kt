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
import de.egovt.evameg.utility.DB.DbHelper
import de.egovt.evameg.utility.Office
import de.egovt.evameg.utility.activityIsPermissionGiven
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker



/**
 * A Fragment that displays a Map with Markers.
 *
 * Is the map of the App. Implemented with OSMDroid. Also queries the Offices when passed IDs (constrcutor 2).
 *
 * @constructor Creates a Map with 1 Office.
 */
class MapViewFragment(): Fragment() {

    // to manage State better
    private lateinit var myMap : MapView
    private var mapIDs : Array<String> = arrayOf()
    var type : String = ""
    var myMapPoints : List<Office> = listOf()

    // for Permissions
    private lateinit var thisView : View
    private lateinit var momentaryContext : Context

    // Call retFunk and destroy the fragment (FORM COMPONENT)
    private lateinit var retFunk:(Int)-> Int
    private var funcThere:Boolean = false



    /**
     * Secondary Constructor for usage of Fragment as Picker for Locations.
     *
     * Calls the primary Constructor, but also adds some IDs and a return Function to the Fragment, so that a selection process can happen.
     */
    constructor(newMapIDs:Array<String>, returnFunction: (Int)-> Int) : this(){

        mapIDs = newMapIDs
        retFunk = returnFunction
        funcThere = true

    }

    /**
     * Tertiary Constructor for usage of Fragment as Picker for Locations.
     *
     * Calls the primary Constructor, but also adds a type for queriying the DB for fitting Offices
     */
    constructor(type:String, returnFunction: (Int)-> Int) : this(){

        this.type = type
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

    /**
     * Sets up Main Configuration for the Map and checks if IDs were given in the constructor.
     */
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




        // request Data from db
        if(type != ""){

            myMapPoints = readMarkerData(type)


        } else if(mapIDs.isNotEmpty()){

            myMapPoints = readMarkerData(mapIDs)


        } else {

            myMapPoints = readMarkerData()


        }

        // add markers to data
        processOfficesForDisplay(myMapPoints)

        // draw em
        drawMapPoints()

        // Start Point
        controller.setCenter(myMapPoints[0].marker.position)

    }

    /**
     * Adds necessary information to Office, so that they can be displayed by OSMDroid as Markers
     *
     * @param mapPoints List of Offices without the marker attribute declared
     * @return List of Processed offices
     */
    private fun processOfficesForDisplay(mapPoints : List<Office>):List<Office>{

        for(mapPoint : Office in mapPoints) {

            mapPoint.marker = Marker(myMap)
            val location = GeoPoint(mapPoint.latitude, mapPoint.longitude)
            mapPoint.marker.position = location
            mapPoint.marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapPoint.marker.title = mapPoint.name
            mapPoint.marker.icon = context?.getDrawable(R.drawable.marker_graphics)

        }
        return mapPoints
    }

    /**
     * Draws the GeoPoints of the Offices in "myMapPoints" and adds Clicklisteners
     *
     * @return bool true if ran sucessfully
     */
    private fun drawMapPoints():Boolean{

        // add Point to Map Overlay
        for(point in myMapPoints){

            point.marker.setOnMarkerClickListener { marker, mapview -> onMarkerClickety(marker, mapview) }
            myMap.overlays.add(point.marker)

        }
        return true
    }

    /**
     * Calls Database to read all Offices
     *
     * @return list of offices to later be added to the map
     */
    private fun readMarkerData():List<Office>{

        val db = DbHelper(context)
        return db.readOfficeData()

    }

    /**
     * Calls Database to read the Data for if the given IDs, if they are provided
     *
     * @return list of offices to later be added to the map
     */
    private fun readMarkerData(IDs : Array<String>):List<Office>{

        val db = DbHelper(context)
        return db.readOfficeData(IDs)

    }

    /**
     * Calls Database to read Offices of given type
     *
     * @return list of offices to later be added to the map
     */
    private fun readMarkerData(type : String):List<Office>{

        val db = DbHelper(context)
        return db.readOfficeData(type)

    }

    private fun identifyMapPoint(marker: Marker): Office? {
        return myMapPoints.find { it.marker == marker }
    }

    /**
     * Click Listeners for markers refer to this function
     *
     * @return bool with true if ran sucessfully
     */
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

    /**
     * Checks if Permissions necessary for the Map are given and requests them if needed
     *
     * @return bool value with true being the value returned if all ran sucessfully
     */
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

