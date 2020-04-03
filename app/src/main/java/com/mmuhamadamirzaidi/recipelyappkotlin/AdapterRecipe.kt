package com.mmuhamadamirzaidi.recipelyappkotlin

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterRecipe() : RecyclerView.Adapter<AdapterRecipe.HolderRecipe>(){

    private var context: Context?=null
    private var recipeList:ArrayList<ModelRecipe>?=null

    lateinit var dbHelper: MyDbHelper

    constructor(context: Context?, recipeList: ArrayList<ModelRecipe>?) : this() {
        this.context = context
        this.recipeList = recipeList

        dbHelper = MyDbHelper(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderRecipe {
        return HolderRecipe(
            LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recipeList!!.size
    }

    override fun onBindViewHolder(holder: HolderRecipe, position: Int) {
        val model = recipeList!!.get(position)

        val id = model.id
        val image = model.image
        val type = model.type
        val name = model.name
        val serves = model.serves
        val ingredients = model.ingredients
        val steps = model.steps
        val addTime = model.addTime

        holder.itemType.text = type
        holder.itemName.text = name
        holder.itemServes.text = serves

        if (image == "null") {
            holder.itemImage.setImageResource(R.drawable.ic_launcher_background)
        }
        else {
            holder.itemImage.setImageURI(Uri.parse(image))
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailRecipeActivity::class.java)
            intent.putExtra("RECIPE_ID", id)
            context!!.startActivity(intent)
        }

        holder.itemMore.setOnClickListener {
            showMoreOption(position, id, image, type, name, serves, ingredients, steps, addTime)
        }
    }

    private fun showMoreOption(
        position: Int,
        id: String,
        image: String,
        type: String,
        name: String,
        serves: String,
        ingredients: String,
        steps: String,
        addTime: String
    ) {
        val options = arrayOf("Update", "Delete")

        val dialog:AlertDialog.Builder = AlertDialog.Builder(context)
        dialog.setTitle("Choose Action")
        dialog.setItems(options) { dialog, which ->
          if (which == 0) {
              val intent = Intent(context, ManageRecipeActivity::class.java)
              intent.putExtra("ID", id)
              intent.putExtra("IMAGE", image)
              intent.putExtra("TYPE", type)
              intent.putExtra("NAME", name)
              intent.putExtra("SERVES", serves)
              intent.putExtra("INGREDIENTS", ingredients)
              intent.putExtra("STEPS", steps)
              intent.putExtra("ADDTIME", addTime)
              intent.putExtra("isEditMode", true)
              context!!.startActivity(intent)
          }
          else {
              dbHelper.deleteRecipe(id)
              (context as MainActivity)!!.onResume();
          }
        }
        dialog.show()
    }

    inner class HolderRecipe(itemView: View): RecyclerView.ViewHolder(itemView) {

        var itemImage:ImageView = itemView.findViewById(R.id.itemImage)
        var itemType:TextView = itemView.findViewById(R.id.itemType)
        var itemName:TextView = itemView.findViewById(R.id.itemName)
        var itemServes:TextView = itemView.findViewById(R.id.itemServes)
        var itemMore:ImageView = itemView.findViewById(R.id.itemMore)
    }
}