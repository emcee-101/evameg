package de.egovt.evameg.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import de.egovt.evameg.databinding.ActivityMapviewBinding
import de.egovt.evameg.utility.activityIsPermissionGiven
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


class MapviewActivity  : AppCompatActivity() {

    private lateinit var myMap : MapView

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

        return activityIsPermissionGiven(this, neededPermissions, this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkMapPermissions()

        Configuration.getInstance().userAgentValue = packageName

        val binding = ActivityMapviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myMap = binding.coolMap
        myMap.setTileSource(TileSourceFactory.MAPNIK)




        val controller = myMap.controller
        controller.setZoom(18.5)

        val mapPointFHErfurt = GeoPoint(50.985167884281026, 11.041366689707237)
        controller.setCenter(mapPointFHErfurt)


    }


}