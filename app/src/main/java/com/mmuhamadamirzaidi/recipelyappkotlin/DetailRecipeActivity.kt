package com.mmuhamadamirzaidi.recipelyappkotlin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.activity_detail_recipe.*
import java.util.*

class DetailRecipeActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null;

    private var dbHelper: MyDbHelper? = null;

    private var recipeId: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_recipe)

        actionBar = supportActionBar
        actionBar!!.title = "Recipe's Detail"
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)

        dbHelper = MyDbHelper(this)

        val intent = intent
        recipeId = intent.getStringExtra("RECIPE_ID")

        showRecipeDetail()
    }

    private fun showRecipeDetail() {
        val selectQuery = "SELECT * FROM ${Constants.TABLE_NAME} WHERE ${Constants.C_ID} =\"$recipeId\""

        val db = dbHelper!!.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID))
                val image = ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE))
                val type = ""+cursor.getString(cursor.getColumnIndex(Constants.C_TYPE))
                val name = ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME))
                val serves = ""+cursor.getString(cursor.getColumnIndex(Constants.C_SERVES))
                val ingredients = ""+cursor.getString(cursor.getColumnIndex(Constants.C_INGREDIENTS))
                val steps = ""+cursor.getString(cursor.getColumnIndex(Constants.C_STEPS))
                val addTime = ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIME))

                val dateTime = Calendar.getInstance(Locale.getDefault())
                dateTime.timeInMillis = addTime.toLong()
                val newDateTime = android.text.format.DateFormat.format("dd/MM/yyyy hh:mm aa", dateTime)

                detailRecipeType.text = type
                detailRecipeName.text = name
                detailRecipeAddTime.text = newDateTime
                detailRecipeServes.text = serves
                detailRecipeIngredients.text = ingredients
                detailRecipeSteps.text = steps

                if (image == "null") {
                    detailRecipeImage.setImageResource(R.drawable.ic_launcher_background)
                }else {
                    detailRecipeImage.setImageURI(Uri.parse(image))
                }

            }while (cursor.moveToNext())
        }
        db.close()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
