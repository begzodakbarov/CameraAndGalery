package com.example.cameraandgalery.models

class MyImage {
    var id :Int? = null
    var name :String? = null
    var imageUri :String? = null

    constructor(id: Int?, name: String?, imageUri: String?) {
        this.id = id
        this.name = name
        this.imageUri = imageUri
    }

    constructor(name: String?, imageUri: String?) {
        this.name = name
        this.imageUri = imageUri
    }
}