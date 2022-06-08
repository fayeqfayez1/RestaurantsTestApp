package com.restauran.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.IOException
import java.lang.Exception

object FileUtils {
    @Throws(IOException::class)
    fun getNewImageFile(context: Context): File? {
        return File.createTempFile(
            System.currentTimeMillis().toString(), ".jpg",
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
    }

    fun getPathFromUri(mActivity: Activity, uri: Uri?): String? {
        return if (uri == null) {
            null
        } else {
            val scheme = uri.scheme
            if (scheme == "file") {
                uri.path
            } else if (scheme == "content") {
                getRealImagePathFromURI(mActivity.contentResolver, uri)
            } else {
                uri.toString()
            }
        }
    }

    private fun getRealImagePathFromURI(
        contentResolver: ContentResolver,
        contentURI: Uri
    ): String? {
        val cursor = contentResolver.query(
            contentURI, null, null, null,
            null
        )
        return if (cursor == null) contentURI.path else {
            cursor.moveToFirst()
            val idx = cursor
                .getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            try {
                cursor.getString(idx)
            } catch (exception: Exception) {
                null
            }
        }
    }


}