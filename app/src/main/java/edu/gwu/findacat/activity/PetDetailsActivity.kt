package edu.gwu.findacat.activity

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v4.app.TaskStackBuilder
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import edu.gwu.findacat.R
import kotlinx.android.synthetic.main.activity_pet_details.*
import android.view.KeyEvent.KEYCODE_BACK
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toolbar
import com.squareup.picasso.Picasso
import edu.gwu.trivia.PetsAdapter
import edu.gwu.trivia.model.generated.petfinder1.PetItem
import kotlinx.android.synthetic.main.abc_activity_chooser_view.view.*
import kotlinx.android.synthetic.main.activity_pets.*
import kotlinx.android.synthetic.main.row_petdetail.*


class PetDetailsActivity : AppCompatActivity() {
    private lateinit var petItem: PetItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_details)
        setSupportActionBar(pet_details_toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);


        petItem = intent.getParcelableExtra(getString(R.string.pet_detail))
        populateDetails(petItem)

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.getItemId()==android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pets, menu)
        return true
    }

    fun shareButtonPressed(item: MenuItem?) {
        val sendIntent = Intent()

        sendIntent.action = Intent.ACTION_SEND
        val name = petItem.name.t
        val email = petItem.contact.email
        val photo = petItem.media.photos?.photo?.get(0)?.t
        val shareText = getString(R.string.share_Message, name,email,photo)
//        val shareText1 = getString(R.string.share_email,email)
//        val shareText2 = getString(R.string.share_email,photo)

        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText)
//        sendIntent.putExtra(Intent.EXTRA_TEXT, shareText1)
//        sendIntent.putExtra(Intent.EXTRA_STREAM, shareText2)

        sendIntent.type = "text/plain"

dvfrgrgregrgergregr
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.share)))
    }

    fun shareEmailButtonPressed(item: MenuItem) {

        val sendIntent: Intent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        val shareText ="I’m interested in your cat named: ${petItem.name.t} "
           sendIntent.putExtra(Intent.EXTRA_TEXT,shareText)
            sendIntent.type = "text/plain"


        //test teste
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.EmailShare)))

    }


    private fun populateDetails(petItem: PetItem) {

        Picasso.get().load(petItem.media.photos?.photo?.get(0)?.t).into(photo)
        name.text = "Name:${petItem.name.t}"
        breed.text = "Breed: ${petItem.breeds.breed[0].t}"
        gender.text = "Gender: ${petItem.sex.t}"
        zip.text = "Zipcode: ${petItem.contact.zip.t}"
        description.text = "Description: ${petItem.description?.t}"


    }
}
