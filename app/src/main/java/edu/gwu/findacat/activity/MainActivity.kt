package edu.gwu.findacat.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import edu.gwu.findacat.R
import edu.gwu.trivia.CatFactFetcher
import edu.gwu.trivia.LocationDetector
import edu.gwu.trivia.PetFinderManager
import edu.gwu.trivia.model.generated.petfinder1.PetItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.activityUiThread

class MainActivity : AppCompatActivity(), LocationDetector.LocationListener,CatFactFetcher.CatFactFetherCompletionListener,PetFinderManager.PetSearchCompletionListener{
    private lateinit var Fectcher: CatFactFetcher
    private val LOCATION_PERMISSION_REQUEST_CODE = 7
    private lateinit var locationDetector :LocationDetector
    private lateinit var petFinderManager: PetFinderManager
    private var defaultZipCode = 22046

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        find_a_cat.setOnClickListener {
            val intent = Intent(this@MainActivity, PetsActivity::class.java)
            intent.putExtra("ZipCode","22202")
            startActivity(intent)


        }

        Fectcher = CatFactFetcher(this, catfact)
        Fectcher.catFactFetcherCompletionListener = this
        Fectcher.searchFacts()

        requestPermissionsIfNecessary()
    }

    fun loadPetsData() {
        doAsync {
            activityUiThread {

                locationDetector = LocationDetector(this@MainActivity)
                locationDetector.locationListener = this@MainActivity
                locationDetector.detectLocation()


            }
        }
    }
    override fun petsNotLoaded(){

    }

    override fun petsLoaded(petItems: List<PetItem>) {



//        val intent = Intent()
//        intent.action  = Intent.ACTION_SEND
//        intent.putExtra(Intent.EXTRA_TEXT,"zipcode")
    }
    override fun locationFound(location: Location) {
        val geocode = Geocoder(this, Locale.getDefault())
        val addressList =  geocode.getFromLocation(location.latitude,location.longitude, 1)
        defaultZipCode =   addressList.get(0).postalCode.toString().toInt()

        petFinderManager = PetFinderManager(this@MainActivity,"geocode")

        petFinderManager.searchPets(defaultZipCode.toString())
        petFinderManager.petSearchCompletionListener = this@MainActivity



    }

    override fun locationNotFound(reason: LocationDetector.FailureReason) {

        petFinderManager = PetFinderManager(this@MainActivity,"geocode")
        petFinderManager.searchPets("zip")
        petFinderManager.petSearchCompletionListener = this@MainActivity

    }

    override fun CatFactNotLoaded() {
    }

    override fun CatFactLoaded(fact: String) {
        catfact.text = fact
    }
    private fun requestPermissionsIfNecessary() {
        val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if(grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                toast(R.string.permissions_granted)
            }
            else {
                toast(R.string.permissions_denied)
            }
        }
    }
}



