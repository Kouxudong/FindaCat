//package edu.gwu.trivia
//
//import android.content.ContentValues
//import android.content.Context
//import android.content.SharedPreferences
//import android.preference.PreferenceManager
//import android.util.Log
//import com.squareup.moshi.Moshi
//import com.squareup.moshi.Types
//import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
//import edu.gwu.trivia.model.generated.petfinder1.PetItem
//import java.io.IOException
//import java.util.*
//
//class PersistenceManager(private val context: Context) {
//    private val sharedPreferences: SharedPreferences
//
//    init {
//        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//    }
//
//    fun saveScore(petItem: PetItem) {
//
//        val petItems = fetchScores().toMutableList()
//        petItems.add(petItem)
//
//        val editor = sharedPreferences.edit()
//
//        //convert a list of scores into a JSON string
//        val moshi = Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()
//        val listType = Types.newParameterizedType(List::class.java, PetItem::class.java)
//        val jsonAdapter = moshi.adapter<List<PetItem>>(listType)
//        val jsonString = jsonAdapter.toJson(petItems)
//
//        editor.putString()
//
//        editor.apply()
//
//    }
//
//    fun fetchScores(): List<PetItem> {
//
//        val jsonString = sharedPreferences.getString(Constants.SCORES_PREF_KEY, null)
//
//        //if null, this means no previous scores, so create an empty array list
//        if(jsonString == null) {
//            return arrayListOf<PetItem>()
//        }
//        else {
//            //existing scores, so convert the scores JSON string into Score objects, using Moshi
//            val listType = Types.newParameterizedType(List::class.java, PetItem::class.java)
//            val moshi = Moshi.Builder()
//                    .add(Date::class.java, Rfc3339DateJsonAdapter())
//                    .build()
//            val jsonAdapter = moshi.adapter<List<PetItem>>(listType)
//
//            var petItem:List<PetItem>? = emptyList<PetItem>()
//            try {
//                petItem = jsonAdapter.fromJson(jsonString)
//            } catch (e: IOException) {
//                Log.e(ContentValues.TAG, e.message)
//            }
//
//            if(petItem != null) {
//                return petItem.sortedByDescending {  }
//            }
//            else {
//                return emptyList<PetItem>()
//            }
//        }
//    }
//
//    fun highScore(): PetItem? {
//        val scores = fetchScores()
//
//        return scores.firstOrNull()
//    }
//}
//
