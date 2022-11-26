package de.egovt.evameg

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView


class MapviewActivity  : AppCompatActivity() {
    private var map: MapView? = null
    private var mapController: IMapController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 23) {
            if ((checkSelfPermission(android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                Log.v("TAG", "Permission there yessss")
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE as String), 1);
            }
        }


        //Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
        Configuration.getInstance().userAgentValue = packageName



        //inflate and create the map
        setContentView(R.layout.activity_mapview)
        map = findViewById<MapView>(R.id.coolMap)

        map?.setTileSource(TileSourceFactory.MAPNIK)

        map?.setMultiTouchControls(true)
        mapController = map?.controller
    }


    override fun onResume() {
        super.onResume()

        map?.onResume()
    }

    override fun onPause() {
        super.onPause()

        map?.onPause()
    }

}