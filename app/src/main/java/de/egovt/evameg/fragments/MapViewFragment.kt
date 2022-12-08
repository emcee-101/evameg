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
import de.egovt.evameg.utility.MapPin
import de.egovt.evameg.utility.activityIsPermissionGiven
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.OverlayItem


class MapViewFragment(): Fragment() {

    constructor(newPins : Array<MapPin>) : this() {

        // todo add functionality to add them to map

    }

    private lateinit var thisView : View
    private lateinit var myMap : MapView
    private lateinit var mapPins : Array<MapPin>
    private lateinit var momentaryContext : Context
    private var overlayItems : ArrayList<OverlayItem> = arrayListOf( OverlayItem("a", "b", GeoPoint(0.0,0.0)))

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

        overlayItems.add(OverlayItem("aa", "a fish", mapPointFHErfurt))

        val mOverlay = ItemizedOverlayWithFocus(overlayItems,
            object : OnItemGestureListener<OverlayItem?> {
                override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                    //todo react appropriately

                    Log.i("aaa", "hello the icon was clicked on the map")


                    return true
                }

                override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                    return false
                }
            }, context
        )
        mOverlay.setFocusItemsOnTap(true)

       // myMap.getOverlays().add(mOverlay)

        val startMarker = Marker(myMap)
        startMarker.position = mapPointFHErfurt
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        myMap.overlays.add(startMarker)
        // todo look of pin
        // change look startMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher));
        // https://osmdroid.github.io/osmdroid/Markers,-Lines-and-Polygons.html
        startMarker.title = "Start point"
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

