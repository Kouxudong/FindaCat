package edu.gwu.trivia

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import edu.gwu.findacat.R
import edu.gwu.trivia.model.generated.petfinder1.PetItem
import kotlinx.android.synthetic.main.activity_pet_details.view.*

class PetsAdapter(private val petItems: List<PetItem>,private val clickListener: OnPetClickListener):
        RecyclerView.Adapter<PetsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {


        val layoutInflater = LayoutInflater.from(viewGroup?.context)

        return ViewHolder(layoutInflater.inflate(R.layout.row_pets, viewGroup, false))
    }


    override fun getItemCount(): Int {
        return petItems.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val petItem = petItems.get(position)

        viewHolder.bind(petItem, clickListener)
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val PetItemTextView: TextView = view.findViewById(R.id.PetItem_textview)
        private val PetPhotoImageView: ImageView = view.findViewById(R.id.PetPhoto_imageview)


        fun bind(petItem: PetItem, clickListener: OnPetClickListener) = with(itemView) {
            PetItemTextView.text = petItem.name.t
            Picasso.get().load(petItem.media.photos?.photo?.get(0)?.t).into(PetPhotoImageView)

            setOnClickListener {
                clickListener.onPetClick(petItem)

            }

        }
    }

    interface OnPetClickListener {

        fun onPetClick(petItem: PetItem)
    }

}


