package com.restauran.utils

import android.content.Context
import android.graphics.*
import android.media.ExifInterface
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.lang.ArithmeticException
import java.lang.Exception
import java.util.ArrayList

class ImageCompress(
    var mContext: Context? = null,
    var isMainImage: Boolean = false,
    var listListener: onFinishedCompressListListener? = null,
    var imageUriList: List<String?>? = ArrayList()
) {

    @Throws(ArithmeticException::class)
    private fun compressImage(imageUri: String): String? {
        val filePath: String? = null //getRealPathFromURI(imageUri)
        var scaledBitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(filePath, options)
        var actualHeight = options.outHeight
        var actualWidth = options.outWidth
        val maxHeight = 816.0f
        val maxWidth = 612.0f
        if (actualHeight == 0) actualHeight = maxHeight.toInt()
        if (actualWidth == 0) actualWidth = maxWidth.toInt()
        var imgRatio = (actualWidth / actualHeight).toFloat()
        val maxRatio = maxWidth / maxHeight
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }
        options.inSampleSize = ToolUtils.calculateInSampleSize(options, actualWidth, actualHeight)
        options.inJustDecodeBounds = false
        options.inDither = false
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)
        bmp = try {
            BitmapFactory.decodeFile(filePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
            return null
        }
        scaledBitmap = try {
            Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
            return null
        }
        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f
        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)
        val canvas = scaledBitmap?.let { Canvas(it) }
        canvas?.setMatrix(scaleMatrix)
        canvas?.drawBitmap(
            bmp,
            middleX - bmp.width / 2,
            middleY - bmp.height / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )
        val exif: ExifInterface
        try {
            exif = ExifInterface(filePath!!)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            Log.d("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 3) {
                matrix.postRotate(180f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 8) {
                matrix.postRotate(270f)
                Log.d("EXIF", "Exif: $orientation")
            }
            scaledBitmap = scaledBitmap?.let {
                Bitmap.createBitmap(
                    it, 0, 0,
                    scaledBitmap!!.width, scaledBitmap!!.height, matrix, true
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        var out: FileOutputStream? = null
        val filename: String = getFilename()
        try {
            out = FileOutputStream(filename)
            scaledBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, out)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }
        return filename
    }

    private fun getFilename(): String {
        val file =
            File(Environment.getExternalStorageDirectory().path, "CHRIS/ImagesBean")
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath + "/" + System.currentTimeMillis() + ".jpg"
    }

    fun deleteFolder(): Boolean {
        var isDeleted = false
        val dir = File(Environment.getExternalStorageDirectory().path, "CHRIS")
        try {
            isDeleted = deleteNon_EmptyDir(dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isDeleted
    }

    private fun deleteNon_EmptyDir(dir: File): Boolean {
        if (dir.isDirectory) {
            val children = dir.list()
            for (aChildren in children) {
                val success = deleteNon_EmptyDir(File(dir, aChildren))
                if (!success) {
                    return false
                }
            }
        }
        return dir.delete()
    }

    interface onFinishedCompressListListener {
        fun onError(error: String?)
        fun onSuccessMainImageCompress(photos: List<String?>?)
        fun onSuccessImageCompress(photos: List<String?>?)
    }
}