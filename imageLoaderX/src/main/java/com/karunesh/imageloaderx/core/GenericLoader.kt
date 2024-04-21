package com.karunesh.imageloaderx.core

import java.io.File

interface GenericLoader {

    val schemes: List<String>

    fun load(uriString: String, file: File): Boolean

}