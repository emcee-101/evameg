package de.egovt.evameg


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import de.egovt.evameg.databinding.ActivityMapviewBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


class MapviewActivity  : AppCompatActivity() {

    lateinit var mMap : MapView

    fun checkForPermissions( code : Manifest.permission){

        if (ContextCompat.checkSelfPermission(MapviewActivity.class, code) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(MapviewActivity.class, arrayOf(code), 0L)
        } else {
            Toast.makeText(MapviewActivity.class, "Permission already granted", Toast.LENGTH_SHORT).show()
        }


        if ((checkSelfPermission(code) == PackageManager.PERMISSION_GRANTED)) {
            Log.v("TAG", "Permission there yessss")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO ask for permissions more gracefully
        // TODO read about databinding, even though it shouldnt be needed here
        // TODO make this thing wórk



        Configuration.getInstance().userAgentValue = packageName

        val binding = ActivityMapviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMap = binding.coolMap
        mMap.setTileSource(TileSourceFactory.MAPNIK)


        val longitude = intent.getDoubleExtra("longitude", 36.7783)
        val latitude = intent.getDoubleExtra("latitude", 119.4179)


        val controller = mMap.controller

        val mapPoint = GeoPoint(latitude, longitude)

        controller.setZoom(9.5)

        controller.animateTo(mapPoint)

    }


}