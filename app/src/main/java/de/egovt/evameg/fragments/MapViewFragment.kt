package de.egovt.evameg.fragments


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.google.android.material.internal.ContextUtils.getActivity
import de.egovt.evameg.R
import de.egovt.evameg.databinding.ActivityMapviewBinding
import de.egovt.evameg.utility.activityIsPermissionGiven
import de.egovt.evameg.utility.cont
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MapViewFragment: Fragment(R.layout.activity_mapview) {

    private lateinit var myMap : MapView
    private lateinit var mapPoints : Array<GeoPoint>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkMapPermissions()

        Configuration.getInstance().userAgentValue = activity?.packageName

        val binding = ActivityMapviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myMap = binding.coolMap
        myMap.setTileSource(TileSourceFactory.MAPNIK)




        val controller = myMap.controller
        controller.setZoom(18.5)

        val mapPointFHErfurt = GeoPoint(50.985167884281026, 11.041366689707237)
        controller.setCenter(mapPointFHErfurt)

        // TODO Interactive Pins
        // "How do I place icons on the map with a click listener?"
        // https://osmdroid.github.io/osmdroid/How-to-use-the-osmdroid-library.html



    }

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

        //requestPermissions(getActivity(requireContext())!!, "1")
        return activityIsPermissionGiven(getActivity(requireContext(), neededPermissions, getActivity())

    }

}

