package com.karunesh.imageloaderx.core

import java.io.File

interface Decoder {

    fun probe(file: File): Boolean

    fun decode(file: File, width: Int, height: Int): Result?

}