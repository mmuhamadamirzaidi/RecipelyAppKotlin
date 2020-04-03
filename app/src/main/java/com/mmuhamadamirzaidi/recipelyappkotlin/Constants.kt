package com.mmuhamadamirzaidi.recipelyappkotlin

object Constants {
    const val DB_NAME = "RECIPELY_KOTLIN_DB"
    const val DB_VERSION = 1
    const val TABLE_NAME = "RECIPELY_TABLE"

    const val C_ID = "ID"

    const val C_IMAGE = "IMAGE"
    const val C_TYPE = "TYPE"
    const val C_NAME = "NAME"
    const val C_SERVES = "SERVES"
    const val C_INGREDIENTS = "INGREDIENTS"
    const val C_STEPS = "STEPS"
    const val C_ADD_TIME = "ADD_TIME"

    const val CREATE_TABLE = (
            "CREATE TABLE " + TABLE_NAME + "("
                    + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + C_IMAGE + " TEXT,"
                    + C_TYPE + " TEXT,"
                    + C_NAME + " TEXT,"
                    + C_SERVES + " TEXT,"
                    + C_INGREDIENTS + " TEXT,"
                    + C_STEPS + " TEXT,"
                    + C_ADD_TIME + " TEXT"
            + ")"
            )
}