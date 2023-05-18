package dev.cisnux.mygooglemaps

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import dev.cisnux.mygooglemaps.databinding.ActivityMapsBinding
import java.io.IOException
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getMyLocation()
            }
        }
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.map_options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.normal_type -> {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }

        R.id.satellite_type -> {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }

        R.id.terrain_type -> {
            mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }

        R.id.hybrid_type -> {
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        val dicodingSpace = LatLng(-6.8957643, 107.6338462)
        mMap.addMarker(
            MarkerOptions()
                .position(dicodingSpace)
                .title("Dicoding Space")
                .snippet("Batik Kumeli No.50")
        )
        mMap.animateCamera(
            CameraUpdateFactory
                .newLatLngZoom(dicodingSpace, 15F)
        )
        mMap.setOnMapLongClickListener { latLng ->
            mMap.addMarker(
                MarkerOptions().apply {
                    position(latLng)
                    title("New Marker")
                    snippet("Lat ${latLng.latitude} Long: ${latLng.longitude}")
                    icon(vectorToBitmap(R.drawable.ic_android_48dp, Color.parseColor("#3DDC84")))
                }
            )
        }
        mMap.setOnPoiClickListener { pointOfInterest ->
            val poiMarker = mMap.addMarker(
                MarkerOptions().apply {
                    position(pointOfInterest.latLng)
                    title(pointOfInterest.name)
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                }
            )
            poiMarker?.showInfoWindow()
        }
        getMyLocation()
        setMapStyle()
        addManyMarkers()
    }

    private fun addManyMarkers() {
        val tourismPlaces = listOf(
            TourismPlace("Floating Market Lembang", -6.8168954, 107.6151046),
            TourismPlace("The Great Asia Africa", -6.8331128, 107.6048483),
            TourismPlace("Rabbit Town", -6.8668408, 107.608081),
            TourismPlace("Alun-Alun Kota Bandung", -6.9218518, 107.6025294),
            TourismPlace("Orchid Forest Cikole", -6.780725, 107.637409),
        )
        tourismPlaces.forEach { tourismPlace ->
            val latLng = LatLng(tourismPlace.latitude, tourismPlace.longitude)
            val addressName = getAddressName(tourismPlace.latitude, tourismPlace.longitude)
            mMap.addMarker(MarkerOptions().apply {
                position(latLng)
                title(tourismPlace.name)
                snippet(addressName)
            })
            boundsBuilder.include(latLng)
        }

        val bounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    private fun getAddressName(latitude: Double, longitude: Double): String? {
        var addressName: String? = null
        val geoCoder = Geocoder(this, Locale.getDefault())
        return try {
            @Suppress("DEPRECATION")
            val geoList = geoCoder.getFromLocation(latitude, longitude, 1)
            if (geoList != null && geoList.size > 0)
                addressName = geoList.first().getAddressLine(0)
            addressName
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getAddressName(latitude: Double, longitude: Double, onLocation: (String) -> Unit): String? {
        val geoCoder = Geocoder(this, Locale.getDefault())
        return try {
            var addressName: String? = null
            geoCoder.getFromLocation(latitude, longitude, 1) { geoList ->
                if (geoList.size > 0) {
                    addressName = geoList.first().getAddressLine(0)
                }
            }
            addressName
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)

        vectorDrawable?.let {
            val bitmap = Bitmap.createBitmap(
                vectorDrawable.intrinsicWidth,
                vectorDrawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
            DrawableCompat.setTint(vectorDrawable, color)
            vectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
        Log.e("BitmapHelper", "Resource not found")
        return BitmapDescriptorFactory.defaultMarker()
    }

    companion object {
        private val TAG = MapsActivity::class.simpleName
    }
}


