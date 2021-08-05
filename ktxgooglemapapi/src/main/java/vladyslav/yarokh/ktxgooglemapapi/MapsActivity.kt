package vladyslav.yarokh.ktxgooglemapapi

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.*
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import vladyslav.yarokh.ktxgooglemapapi.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var markerOptions: MarkerOptions
    private val finishLatLng: LatLng = LatLng(50.6441, 24.3503)
    private var isMoved: Boolean = false

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    if (location != null) {
                        mMap.clear()
                        markerOptions = MarkerOptions().position(
                            LatLng(
                                location.latitude,
                                location.longitude
                            )
                        )

                        mMap.addMarker(
                            markerOptions
                        )

                        if(!isMoved) {
                            mMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    ), DEFAULT_ZOOM.toFloat()
                                )
                            )

                            isMoved = true
                        }

                        mMap.addPolyline(PolylineOptions().add(markerOptions.position, finishLatLng))
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private val gainPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it) {
                Toast.makeText(this, "Permission not granted, map not loaded", Toast.LENGTH_LONG).show()
            } else {
                showEnableLocationSetting()
            }
        }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            createLocationRequest(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()

        gainPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val DEFAULT_ZOOM = 15
    }

    private fun showEnableLocationSetting() {
        googleApiClient = GoogleApiClient.Builder(this@MapsActivity)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                override fun onConnected(bundle: Bundle?) {}
                override fun onConnectionSuspended(i: Int) {
                    googleApiClient.connect()
                }
            }).build()

        googleApiClient.connect()

        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder().addLocationRequest(createLocationRequest()).apply {
            setAlwaysShow(true)
        }

        SettingsClient(this).checkLocationSettings(builder.build()).addOnCompleteListener {
            try { it.getResult(ApiException::class.java) }
            catch(ex: ApiException){
                when(ex.statusCode){
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        val resolvableApiException = ex as ResolvableApiException
                        resolvableApiException.startResolutionForResult(this@MapsActivity, 199)
                    }

                    LocationSettingsStatusCodes.SUCCESS -> startLocationUpdates()
                }
            }
            startLocationUpdates()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode){
            Activity.RESULT_OK -> {
                startLocationUpdates()
            }
            else -> Toast.makeText(this, "Location not granted", Toast.LENGTH_LONG).show()
        }
    }
}