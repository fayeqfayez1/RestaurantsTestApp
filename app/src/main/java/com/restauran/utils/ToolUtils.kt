package com.restauran.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.restauran.R
import com.restauran.utils.ConstantApp.ANOTHER_EXCEPTION_CODE
import com.restauran.utils.ConstantApp.HTTP_EXCEPTION_CODE
import com.restauran.utils.ConstantApp.IO_EXCEPTION_CODE
import com.restauran.utils.ConstantApp.TIME_OUT_CODE
import com.wang.avi.AVLoadingIndicatorView
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.math.RoundingMode
import java.net.MalformedURLException
import java.net.SocketTimeoutException
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object ToolUtils {
    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            RestaurantsApp.getInstance()
                ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun hideKeyboard(activity: Activity) {
        try {
            val imm =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
        }
    }

    fun getHash(mActivity: Activity) {
        val info: PackageInfo
        try {
            RestaurantsApp.getInstance()?.packageName?.let { packageName ->
                info = mActivity.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                )
                for (signature in info.signatures) {
                    var md: MessageDigest = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val something = String(Base64.encode(md.digest(), 0))
                    //String something = new String(Base64.encodeBytes(md.digest()));
                    Log.d("hashKey", something)
                    Log.e("packageName", "${RestaurantsApp.getInstance()?.packageName}")
                }
            }

        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("no such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("exception", e.toString())
        }
    }

    fun showSnackBarLengthLong(activity: Activity?, view: View?, msg: String) {
        if (activity != null && view != null) {
            val mSnackBar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
            val mainTextView =
                mSnackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            mainTextView.setTextColor(activity.resources.getColor(R.color.white))
            mainTextView.textDirection = View.TEXT_DIRECTION_RTL
            mSnackBar.show()
        }
    }

    fun dp2px(context: Context, dp: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun setRoundedImgWithProgress(
        context: Context,
        url: String,
        imageView: ImageView,
        progress: AVLoadingIndicatorView,
    ) {
        progress.visibility = View.VISIBLE
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(R.drawable.circle_place_holder).into(imageView)
            progress.visibility = View.GONE
        } else {
            Glide.with(context).load(url).apply(
                RequestOptions.circleCropTransform()
                    .placeholder(R.drawable.circle_place_holder)
            )
                .listener(object : RequestListener<Drawable?> {
                    @SuppressLint("CheckResult")
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean,
                    ): Boolean {
                        Glide.with(context).load(R.drawable.circle_place_holder)
                        progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean,
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }
                }).into(imageView)
        }
    }

    fun setRoundedImgWithoutProgress(context: Context, url: String, imageView: ImageView) {
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(R.drawable.circle_place_holder).into(imageView)
        } else {
            Glide.with(context).load(url).apply(
                RequestOptions.circleCropTransform()
                    .placeholder(R.drawable.circle_place_holder)
            )
                .listener(object : RequestListener<Drawable?> {
                    @SuppressLint("CheckResult")
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean,
                    ): Boolean {
                        Glide.with(context).load(R.drawable.circle_place_holder)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }
                }).into(imageView)
        }
    }

    fun setImgWithProgress(
        context: Context,
        url: String,
        imageView: ImageView,
        progress: AVLoadingIndicatorView,
    ) {
        progress.visibility = View.VISIBLE
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(R.drawable.image_placeholder).centerCrop().into(imageView)
            progress.visibility = View.GONE
        } else {
            Glide.with(context).load(url).placeholder(R.drawable.image_placeholder).centerCrop()
                .listener(object : RequestListener<Drawable?> {
                    @SuppressLint("CheckResult")
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean,
                    ): Boolean {
                        Glide.with(context).load(R.drawable.image_placeholder).centerCrop()
                        progress.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean,
                    ): Boolean {
                        progress.visibility = View.GONE
                        return false
                    }
                }).into(imageView)
        }
    }

    fun setImg(
        context: Context,
        url: String,
        imageView: ImageView,
    ) {
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(R.drawable.image_placeholder).into(imageView)
        } else {
            Glide.with(context).load(url).placeholder(R.drawable.image_placeholder)
                .listener(object : RequestListener<Drawable?> {
                    @SuppressLint("CheckResult")
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean,
                    ): Boolean {
                        Glide.with(context).load(R.drawable.image_placeholder).centerCrop()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean,
                    ): Boolean {
                        return false
                    }
                }).into(imageView)
        }
    }

    fun setSmallRoundedImgWithProgress(
        context: Context,
        url: String,
        imageView: ImageView,
        progress: AVLoadingIndicatorView,
    ) {
        progress.visibility = View.VISIBLE
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(url).apply(
                RequestOptions()
                    .transforms(
                        CenterCrop(),
                        RoundedCorners(dp2px(context, 10f))
                    )
                    .placeholder(R.color.gray_60)
            )
                .into(imageView)
            progress.visibility = View.GONE
        } else {
            Glide.with(context).load(url).apply(
                RequestOptions().transforms(
                    CenterCrop(),
                    RoundedCorners(dp2px(context, 10f))
                ).placeholder(R.color.gray_60)
            ).listener(object : RequestListener<Drawable?> {
                @SuppressLint("CheckResult")
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean,
                ): Boolean {
                    Glide.with(context).load(R.color.gray_60).apply(
                        RequestOptions().transforms(
                            CenterCrop(),
                            RoundedCorners(dp2px(context, 10f))
                        ).placeholder(R.color.gray_60)
                    )
                    progress.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean,
                ): Boolean {
                    progress.visibility = View.GONE
                    return false
                }
            }).into(imageView)
        }
    }

    fun checkIfEqualDate(milliseconds: Long): Boolean {
        val format = SimpleDateFormat("yyyy-MM-dd")
        var inputDate: Date? = Date(milliseconds * 1000)
        var currentDate = Calendar.getInstance().time
        try {
            format.timeZone = TimeZone.getDefault()
            val today = format.format(currentDate)
            currentDate = format.parse(today)
            format.timeZone = TimeZone.getTimeZone("GMT")
            val dateToCheck = format.format(inputDate)
            format.timeZone = TimeZone.getDefault()
            inputDate = format.parse(dateToCheck)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return if (inputDate != null && currentDate != null) {
            inputDate == currentDate
        } else {
            false
        }
    }

    fun convertDate(dateInMilliseconds: Long, dateFormat: String) =
        DateFormat.format(dateFormat, dateInMilliseconds * 1000).toString()

    fun convertDateToMillisecond(dateS: String): Long {
        var timeInMilliseconds: Long = 0
        val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm:ss a", Locale.US)
        try {
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            var date = formatter.parse(dateS)
            formatter.timeZone = TimeZone.getDefault()
            val ourDate = formatter.format(date)
            date = formatter.parse(ourDate)
            timeInMilliseconds = date.time
            println("Date in milli :: $timeInMilliseconds")
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }

    fun convertMillisecondToDate(milliseconds: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        return formatter.format(Date(milliseconds * 1000))
    }

    private fun getCurrentDateTime(): Date? {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss a", Locale.US)
        var currentDate = Calendar.getInstance().time
        val date = formatter.format(currentDate)
        try {
            currentDate = formatter.parse(date)
        } catch (e: ParseException) {
        }
        return currentDate
    }

    private fun parseCurrentDateTime(currentDate: Date): Date? {
        var currentDate: Date? = currentDate
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val date = formatter.format(currentDate)
        try {
            currentDate = formatter.parse(date)
        } catch (e: ParseException) {
        }
        return currentDate
    }

    fun getLocalTimeString(milliseconds: Long): String? {
        val formatter = SimpleDateFormat("dd MMM yyyy", Locale.US)
        val dateServer = Date(milliseconds)
        return formatter.format(dateServer)
    }

    fun getThrowableErrorMsg(throwable: Throwable): String? {
        var message = ""
        when (throwable) {
            is HttpException -> {
                Log.e("error", "HttpException" + "")
                val responseBody = throwable.response()?.errorBody()
                responseBody?.let { readErrorMessage(it) }
            }
            is SocketTimeoutException -> {
                RestaurantsApp.getInstance()?.let {
                    message = it.getString(R.string.time_out_error)
                }
                Log.e("error", "SocketTimeoutException" + "")
            }
            is IOException -> {
                Log.e("error", "IOException" + "")
                RestaurantsApp.getInstance()?.let {
                    message = it.getString(R.string.error)
                }
            }
            else -> {
                Log.e("error", throwable.message + "")
                RestaurantsApp.getInstance()?.let {
                    message = it.getString(R.string.error)
                }
            }
        }
        return message
    }

    private fun readErrorMessage(responseBody: ResponseBody): String? {
        return try {
            val jsonObject = JSONObject(responseBody.string())
            jsonObject.optString("message")
        } catch (e: Exception) {
            Log.e("error", e.message + "")
            RestaurantsApp.getInstance()?.let {
                it.getString(R.string.error)
            }
        }
    }

    fun getErrorCode(throwable: Throwable): Int {
        val errorCode: Int
        when (throwable) {
            is com.jakewharton.retrofit2.adapter.rxjava2.HttpException -> {
                Log.e("error", "HttpException" + "")
                errorCode = HTTP_EXCEPTION_CODE
            }
            is SocketTimeoutException -> {
                errorCode = TIME_OUT_CODE
                Log.e("error", "SocketTimeoutException" + "")
            }
            is IOException -> {
                Log.e("error", "IOException" + "")
                errorCode = IO_EXCEPTION_CODE
            }
            else -> {
                Log.e("error", throwable.message + "")
                errorCode = ANOTHER_EXCEPTION_CODE
            }
        }
        return errorCode
    }

    fun convertToEngNo(format: String): CharSequence? {
        var result = format
        result = format.replace("١", "1").replace("٢", "2").replace("٣", "3")
            .replace("٤", "4").replace("٥", "5").replace("٦", "6")
            .replace("٧", "7").replace("٨", "8").replace("٩", "9")
            .replace("٠", "0")
            .replace("٫", ".")
        return result
    }

    fun getDate(createdDate: String, format: String): String {
        if (TextUtils.isEmpty(createdDate)) return ""
        val formatter = SimpleDateFormat(format, Locale.US)
        val localDayAndMonthFormatter = SimpleDateFormat("yyyy.MM.dd hh:mm a", Locale.US)
        formatter.timeZone = TimeZone.getTimeZone("GMT")
        var strLocalDate: String = ""
        try {
            formatter.timeZone = TimeZone.getTimeZone("GMT")
            val date = formatter.parse(createdDate)
            localDayAndMonthFormatter.timeZone = TimeZone.getDefault()
            strLocalDate = localDayAndMonthFormatter.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            return strLocalDate
        }
        return strLocalDate
    }

    fun getOrderDate(createdDate: String?, format: String?): String? {
        val formatter = SimpleDateFormat(format, Locale.US)
        val localDayAndMonthFormatter = SimpleDateFormat("yyyy.MM.dd hh:mm a", Locale.US)
        var strLocalDate: String? = null
        try {
            val date = formatter.parse(createdDate)
            strLocalDate = localDayAndMonthFormatter.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return strLocalDate
    }

    fun getCompleteAddressString(
        activity: Activity,
        LATITUDE: Double,
        LONGITUDE: Double,
    ): String {
        var strAdd = "$LATITUDE,$LONGITUDE"
        val geocoder = Geocoder(activity, Locale.getDefault())
        strAdd = try {
            val addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
            if (addresses != null) {
                val returnedAddress = addresses[0]
                val strReturnedAddress = StringBuilder("")
                for (i in 0..returnedAddress.maxAddressLineIndex) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
                }
                strReturnedAddress.toString()
            } else {
                return strAdd
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return strAdd
        }
        return strAdd
    }

    fun getMyCountry(latitude: Double, longitude: Double): String {
        val addresses: List<Address>
        val geocoder: Geocoder = Geocoder(RestaurantsApp.getInstance(), Locale.getDefault())
        return try {
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses == null || addresses.isEmpty()) {
                return "Unknown Address"
            }
            if (addresses[0] == null) {
                return "Unknown Address"
            }
            val address =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName // Only if available else return NULL
            if (address != null) {
                return address
            }
            if (city != null) {
                return city
            }
            if (state != null) {
                return state
            }
            return "Unknown Address"
        } catch (e: IOException) {
            e.printStackTrace()
            return "Unknown Address"
        }
    }

    fun getDateByString(dateStr: String?, format: String?): Date? {
        var date: Date? = null
        val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        try {
            simpleDateFormat.timeZone = TimeZone.getDefault()
            date = simpleDateFormat.parse(dateStr)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    fun convertStringDateToUtc(dateStr: String?, format: String?): String? {
        var date: Date? = null
        var result = ""
        val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        try {
            simpleDateFormat.timeZone = TimeZone.getDefault()
            date = simpleDateFormat.parse(dateStr)
            val formatter = SimpleDateFormat(format, Locale.US)
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            result = formatter.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return result
        }
        return result
    }

    fun compareDateAfter(selectedDate: Date?, currentDate: Date): Boolean {
        Log.e("date", "compareDateAfter: selectedDate$selectedDate")
        Log.e("date", "compareDateAfter: currentDate$currentDate")
        return selectedDate != null && selectedDate.after(currentDate)
    }

    fun getFileNameFromURL(url: String?): String? {
        if (url == null) {
            return ""
        }
        try {
            val resource = URL(url)
            val host = resource.host
            if (host.isNotEmpty() && url.endsWith(host)) {
                // handle ...example.com
                return ""
            }
        } catch (e: MalformedURLException) {
            return ""
        }
        val startIndex = url.lastIndexOf('/') + 1
        val length = url.length

        // find end index for ?
        var lastQMPos = url.lastIndexOf('?')
        if (lastQMPos == -1) {
            lastQMPos = length
        }

        // find end index for #
        var lastHashPos = url.lastIndexOf('#')
        if (lastHashPos == -1) {
            lastHashPos = length
        }

        // calculate the end index
        val endIndex = Math.min(lastQMPos, lastHashPos)
        return url.substring(startIndex, endIndex)
    }

    fun convertToFormatTwoDigit(num: Double): String? {
        val symbols = DecimalFormatSymbols(Locale.US)
        val formatter = DecimalFormat("#0.00", symbols)
        formatter.roundingMode = RoundingMode.FLOOR
        return formatter.format(num)
    }

    fun getBitmapFromView(view: View, height: Int, width: Int): Bitmap? {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas) else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return bitmap
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }
        return inSampleSize
    }


    fun refreshFCMToken() {
//        if (!AppSharedData.isUserLogin()!! || AppSharedData.getUserData() == null) {
//            return
//        }
        if (isNetworkConnected()) {
/*            val params: ArrayMap<String, String> = ArrayMap()
            FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener { task ->
                    if (!task.isSuccessful()) {
                        return@addOnCompleteListener
                    }
                    val token: String = task.getResult().getToken()
                    AppSharedData.setFcmToken(token)
                    params.put("fcm_token", token)
                    params.put("device_id", AppSharedData.getDeviceId())
                    NetworkShared.getAsp().getGeneral()
                        .refreshFcmToken(params, object : RequestListener<String?>() {
                            fun onSuccess(data: String?) {}
                            fun onFail(message: String?, code: Int) {}
                        })
                }*/
        }
    }
}