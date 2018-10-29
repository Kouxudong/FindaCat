package edu.gwu.findacat.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.Toast
import edu.gwu.findacat.R
import edu.gwu.trivia.PetFinderManager
import edu.gwu.trivia.PetsAdapter
import edu.gwu.trivia.model.generated.petfinder1.PetItem
import edu.gwu.trivia.model.generated.petfinder1.Petfinder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pet_details.*
import kotlinx.android.synthetic.main.activity_pets.*
import kotlinx.android.synthetic.main.dialog.view.*
import android.support.v7.widget.GridLayoutManager



class PetsActivity : AppCompatActivity(), PetFinderManager.PetSearchCompletionListener,PetsAdapter.OnPetClickListener{
    private val TAG = "PetsActivity"
    private lateinit var petsadapter: PetsAdapter
    private lateinit var petFinder: PetFinderManager
    private lateinit var petItem: PetItem


    companion object {
         val PETS_DATA_KEY = "petItem"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pets)
        setSupportActionBar(pet_toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        var zipCodeGet = intent.getStringExtra("ZipCode")

        petFinder = PetFinderManager(this,zipCodeGet)
        petFinder.petSearchCompletionListener = this


        petFinder.searchPets("zipcode")

        Zipcode.setOnClickListener {
            val mydialogview = LayoutInflater.from(this).inflate(R.layout.dialog,null)
            val mBuilder = AlertDialog.Builder(this)
                    .setView(mydialogview)
                    .setTitle("Enter")

            val mAlertDialog = mBuilder.show()
            mydialogview.button.setOnClickListener {
                mAlertDialog.dismiss()

                val zipCodeEntered = mydialogview.name.text.toString()
                val intent = Intent(this,PetsActivity::class.java)
                intent.putExtra("ZipCode",zipCodeEntered)
                finish()
                startActivity(intent)
            }
        }


    }

    override fun onPetClick(petItem: PetItem) {
        val detail = Intent(this,PetDetailsActivity::class.java)
        detail.putExtra(getString(R.string.pet_detail),petItem)
        startActivity(detail)
    }
    override fun petsNotLoaded(){

    }

    override fun petsLoaded(petItems: List<PetItem>) {
        petsadapter = PetsAdapter(petItems,this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setLayoutManager(GridLayoutManager(this, 2))

        recycler_view.adapter = petsadapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.getItemId()==android.R.id.home){
            finish()
        }
    return super.onOptionsItemSelected(item)
    }




}
