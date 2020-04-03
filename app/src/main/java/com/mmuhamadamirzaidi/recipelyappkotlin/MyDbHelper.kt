package com.mmuhamadamirzaidi.recipelyappkotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.nio.ByteOrder

class MyDbHelper (context: Context?):SQLiteOpenHelper (
    context,
    Constants.DB_NAME,
    null,
    Constants.DB_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Constants.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME)
        onCreate(db)
    }

    fun insertRecipe(
        image:String?,
        type:String?,
        name:String?,
        serves:String?,
        ingredients:String?,
        steps:String?,
        addTime:String?
    ):Long {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(Constants.C_IMAGE, image)
        values.put(Constants.C_TYPE, type)
        values.put(Constants.C_NAME, name)
        values.put(Constants.C_SERVES, serves)
        values.put(Constants.C_INGREDIENTS, ingredients)
        values.put(Constants.C_STEPS, steps)
        values.put(Constants.C_ADD_TIME, addTime)

        val id = db.insert(Constants.TABLE_NAME, null, values)

        db.close()
        return id
    }

    fun updateRecipe(
        id:String?,
        image:String?,
        type:String?,
        name:String?,
        serves:String?,
        ingredients:String?,
        steps:String?,
        addTime:String?
    ):Long {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(Constants.C_IMAGE, image)
        values.put(Constants.C_TYPE, type)
        values.put(Constants.C_NAME, name)
        values.put(Constants.C_SERVES, serves)
        values.put(Constants.C_INGREDIENTS, ingredients)
        values.put(Constants.C_STEPS, steps)
        values.put(Constants.C_ADD_TIME, addTime)

        return db.update(Constants.TABLE_NAME, values, "${Constants.C_ID}=?", arrayOf(id)).toLong()
    }

    fun getAllRecipe(orderBy: String):ArrayList<ModelRecipe> {
        val recipeList = ArrayList<ModelRecipe>()

        val selectQuery = "SELECT * FROM ${Constants.TABLE_NAME} ORDER BY $orderBy"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val modelRecipe = ModelRecipe(
                    ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_TYPE)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_SERVES)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_INGREDIENTS)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_STEPS)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIME))
                )
                recipeList.add(modelRecipe)
            }while (cursor.moveToNext())
        }
        db.close()
        return recipeList
    }

    fun searchRecipe(query: String):ArrayList<ModelRecipe> {
        val recipeList = ArrayList<ModelRecipe>()

        val selectQuery = "SELECT * FROM ${Constants.TABLE_NAME} WHERE ${Constants.C_NAME} LIKE '%$query%'"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val modelRecipe = ModelRecipe(
                    ""+cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_TYPE)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_SERVES)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_INGREDIENTS)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_STEPS)),
                    ""+cursor.getString(cursor.getColumnIndex(Constants.C_ADD_TIME))
                )
                recipeList.add(modelRecipe)
            }while (cursor.moveToNext())
        }
        db.close()
        return recipeList
    }

    fun deleteRecipe(id: String?) {
        val db = writableDatabase
        db.delete(Constants.TABLE_NAME, "${Constants.C_ID} = ?",
        arrayOf(id)
        )
    }
}