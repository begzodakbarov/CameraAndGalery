package com.example.cameraandgalery.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cameraandgalery.models.MyImage

class MyDbHelper(context: Context):SQLiteOpenHelper(context, DB_NAME,null, VERSION),MyDbInterface{

    companion object{
        const val DB_NAME ="my_images_db"
        const val TABLE_NAME ="images_table"
        const val VERSION = 1
        const val ID ="id"
        const val NAME ="name"
        const val IMAGE ="image_uri"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table $TABLE_NAME ($ID integer not null primary key autoincrement unique, $NAME text not null,$IMAGE text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun getAllImages(): ArrayList<MyImage> {
        val list = ArrayList<MyImage>()
        val database = readableDatabase
        val cursor = database.rawQuery("select * from $TABLE_NAME", null)
        if (cursor.moveToFirst()){
        do {
            list.add(
                MyImage(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                )
            )
            }while (cursor.moveToNext())
        }


        return list
    }

    override fun addImage(myImage: MyImage) {
        val database = readableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME,myImage.name)
        contentValues.put(IMAGE,myImage.imageUri)
        database.insert(TABLE_NAME,null,contentValues)
        database.close()
    }
}