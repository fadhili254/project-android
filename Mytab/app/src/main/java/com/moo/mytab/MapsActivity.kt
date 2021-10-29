package com.moo.mytab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.moo.mytab.databinding.ActivityMapsBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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


        val client = AsyncHttpClient(true, 80, 433)
        client.get("http://127.0.0.1:5000/locations",object: JsonHttpResponseHandler(){

            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                response: JSONArray
            ) {
                for(i in 0 until response.length()){
                    val jsonObject = response.getJSONObject(i)
                    val lat = jsonObject.optString("lat").toDouble()
                    val lon = jsonObject.optString("lon").toDouble()
                    val name = jsonObject.optString("name").toDouble()

                    val gr = LatLng(lat, lon)
                    mMap.addMarker(MarkerOptions()
                        .position(gr)
                        .title(name.toString())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)))



                }

            }//end success

            // we need internet permissions added to manifest
            //for android 10, 11 we need to allow cleartext
            // above are done in manifest file

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?,
                throwable: Throwable?
            ) {
                Toast.makeText(applicationContext, "Failed to load", Toast.LENGTH_LONG).show()
            }//end failure



        })


        // Add a marker in Sydney and move the camera



    }
}