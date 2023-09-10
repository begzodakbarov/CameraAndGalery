package com.example.cameraandgalery.db

import com.example.cameraandgalery.models.MyImage

interface MyDbInterface{
    fun getAllImages():ArrayList<MyImage>
    fun addImage(myImage: MyImage)
}