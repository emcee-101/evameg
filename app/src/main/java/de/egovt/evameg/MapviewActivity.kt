package de.egovt.evameg


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import de.egovt.evameg.databinding.ActivityMapviewBinding

class MapviewActivity  : AppCompatActivity() {

    lateinit var mMap : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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