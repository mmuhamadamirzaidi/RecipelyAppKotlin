package com.mmuhamadamirzaidi.recipelyappkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var dbHelper: MyDbHelper

    private val LATEST = "${Constants.C_ADD_TIME} DESC"

    private var recentOrder = LATEST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        dbHelper = MyDbHelper(this)

        loadRecipe(LATEST)

        mainAddButton.setOnClickListener {
            val intent = Intent(this, ManageRecipeActivity::class.java)
            intent.putExtra("isEditMode", false)
            startActivity(intent)
        }

    }

    private fun loadRecipe(order: String) {
        recentOrder = order
        val adapterRecipe = AdapterRecipe(this, dbHelper.getAllRecipe(LATEST))

        mainRecyclerRecipe.adapter = adapterRecipe
    }

    private fun searchRecipe(query: String) {
        val adapterRecipe = AdapterRecipe(this, dbHelper.searchRecipe(query))

        mainRecyclerRecipe.adapter = adapterRecipe
    }

    public override fun onResume() {
        super.onResume()
        loadRecipe(recentOrder)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val itemSearch = menu.findItem(R.id.mainMenuSearch)
        val searchView = itemSearch.actionView as SearchView

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchRecipe(newText)
                }
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchRecipe(query)
                }
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        return super.onContextItemSelected(item)
    }
}
