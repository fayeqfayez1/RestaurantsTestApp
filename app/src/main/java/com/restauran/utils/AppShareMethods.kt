package com.restauran.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ShareCompat
import com.google.android.material.snackbar.Snackbar
import com.restauran.R

object AppShareMethods {
    private var WHATS_APP_MARKET_URL = "market://details?id=com.whatsapp"
    private var FACEBOOK_MARKET_URL = "market://details?id=com.facebook.katana"
    private var SKYPE_MARKET_URL = "market://details?id=com.skype.raider"
    private var GOOGLE_APP_MARKET_URL = "market://details?id=com.google.android.apps.plus"
    private var MESSENGER_APP_MARKET_URL = "market://details?id=com.facebook.orca"
    private var TWITTER_APP_MARKET_URL = "market://details?id=com.twitter.android"

    fun isEmptyEditText(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.isEmpty()
    }

    fun isEmptyText(text: TextView): Boolean {
        return text.text.toString().trim { it <= ' ' }.isEmpty()
    }

    fun errorInput(editText: EditText, msg: String?) {
        editText.requestFocus()
        editText.error = msg
    }

    fun getTextForMobile(editText: EditText): String? {
        var text = ToolUtils.convertToEngNo(editText.text.toString()).toString()
        if (editText.text.toString().trim { it <= ' ' }.startsWith("05")) {
            text = text.replaceFirst("0".toRegex(), "")
        }
        return text
    }

    fun getText(editText: EditText): String{
        return ToolUtils.convertToEngNo(editText.text.toString().trim { it <= ' ' }).toString() + ""
    }

    fun getText(editText: TextView): String {
        return editText.text.toString().trim { it <= ' ' }
    }

    fun isValidEmailAddress(editText: EditText): Boolean {
        val email = editText.text.toString().trim()
        return email.takeIf { it.isNotEmpty() }?.let {
            Patterns.EMAIL_ADDRESS.matcher(it).matches()
        } ?: false
    }

    fun isValidPassword(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.length >= 6
    }

    fun isInValidPassword(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.length < 6
    }

    fun isValidMobile(editText: EditText): Boolean {
        return if (editText.text.toString()
                .trim { it <= ' ' }.length == 10 && editText.text.toString().trim { it <= ' ' }
                .startsWith("05")
        ) true else editText.text.toString()
            .trim { it <= ' ' }.length == 9 && editText.text.toString().trim { it <= ' ' }
            .startsWith("5")
    }

    fun isInValidMobile(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.length < 8 || editText.text.toString()
            .trim { it <= ' ' }.length > 9 /* || !editText.getText().toString().trim().startsWith("5")*/
    }

    fun isInValidMobileStartWithZero(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.startsWith("0")
    }

    fun isPasswordsMatch(editText1: EditText, editText2: EditText): Boolean {
        return editText1.text.toString().trim { it <= ' ' } == editText2.text.toString()
            .trim { it <= ' ' }
    }

    fun showToast(activity: Activity, message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackBar(activity: Activity, view: View, message: String) {
        val snackbar = Snackbar.make(
            view, message, Snackbar.LENGTH_LONG
        )
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        val font = Typeface.createFromAsset(activity.assets, "avenirltstd_medium.otf")
        textView.setTextColor(activity.resources.getColor(R.color.white))
        textView.typeface = font
        sbView.setBackgroundColor(activity.resources.getColor(R.color.colorAccent))
        textView.textDirection = View.TEXT_DIRECTION_LTR
        textView.gravity = Gravity.LEFT

        textView.textAlignment = View.TEXT_ALIGNMENT_GRAVITY
        snackbar.show()
    }

    fun setTransStatusBar(mActivity: Activity) {
        mActivity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }

    /**
     * Determine whether the App for Android Client is installed on this device.
     */
    fun isAppClientInstalledAndEnabled(mActivity: Activity, packageName: String?): Boolean {
        val myPackageMgr = mActivity.packageManager
        return try {
            myPackageMgr.getPackageInfo(packageName ?: "", PackageManager.GET_ACTIVITIES)
            myPackageMgr.getApplicationInfo(packageName ?: "", 0).enabled
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    /**
     * Install the App Client through the market: URI scheme.
     */
    fun goToMarket(mActivity: Activity, urlOnMarket: String?) {
        val marketUri = Uri.parse(urlOnMarket)
        val myIntent = Intent(Intent.ACTION_VIEW, marketUri)
        myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        mActivity.startActivity(myIntent)
        return
    }

    fun newWhatsAppIntent(mActivity: Activity, pm: PackageManager?, whatsapp: String) {
        if (!isAppClientInstalledAndEnabled(mActivity, "com.whatsapp")) {
            AppShareMethods.goToMarket(mActivity, "com.whatsapp")
            return
        }
        if (!TextUtils.isEmpty(whatsapp)) {
            val uri = Uri.parse("smsto:$whatsapp")
            val i = Intent(Intent.ACTION_SENDTO, uri)
            i.setPackage("com.whatsapp")
            mActivity.startActivity(Intent.createChooser(i, ""))
        }
    }

    fun newSkypeIntent(mActivity: Activity, pm: PackageManager?, appUrl: String?) {
        if (!TextUtils.isEmpty(appUrl)) {
            val intent = mActivity.packageManager.getLaunchIntentForPackage("com.skype.raider")
            intent?.action = Intent.ACTION_SEND
            intent?.putExtra(Intent.EXTRA_TEXT, appUrl)
            intent?.type = "text/plain"
            try {
                mActivity.startActivity(intent)
            } catch (ignored: ActivityNotFoundException) {
                mActivity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)))
            }
        }
    }

    fun newGoogleIntent(mActivity: Activity, pm: PackageManager?, appUrl: String?) {
        if (!TextUtils.isEmpty(appUrl)) {
            val shareIntent = ShareCompat.IntentBuilder.from(mActivity)
                .setText(appUrl).setType("text/plain")
                .setStream(Uri.parse("")).intent
                .setPackage("com.google.android.apps.plus")
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            try {
                mActivity.startActivity(shareIntent)
            } catch (ignored: ActivityNotFoundException) {
                mActivity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)))
            }
        }
    }

    fun newFacebookMessengerIntent(mActivity: Activity, pm: PackageManager?, appUrl: String?) {
        if (!TextUtils.isEmpty(appUrl)) {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, appUrl)
            sendIntent.type = "text/plain"
            sendIntent.setPackage("com.facebook.orca")
            try {
                mActivity.startActivity(sendIntent)
            } catch (ignored: ActivityNotFoundException) {
                mActivity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(appUrl)))
            }
        }
    }

    fun newInstagramIntent(mActivity: Activity, pm: PackageManager?, instagramUrl: String?) {
        val uri = Uri.parse(instagramUrl)
        val likeIng = Intent(Intent.ACTION_VIEW, uri)
        likeIng.setPackage("com.instagram.android")
        try {
            mActivity.startActivity(likeIng)
        } catch (e: ActivityNotFoundException) {
            mActivity.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(instagramUrl)
                )
            )
        }
    }

    fun newTwitterIntent(mActivity: Activity, pm: PackageManager?, twitterUrl: String?) {
        if (!TextUtils.isEmpty(twitterUrl)) {
            val uri = Uri.parse(twitterUrl)
            val likeIng = Intent(Intent.ACTION_VIEW, uri)
            likeIng.setPackage("com.twitter.android")
            try {
                mActivity.startActivity(likeIng)
            } catch (e: ActivityNotFoundException) {
                mActivity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl)))
            }
        }
    }

    // To animate view slide out from left to right
    fun slideToRight(view: View, visibility: Int, duration: Int) {
        val animate = TranslateAnimation(0F, view.width.toFloat(), 0F, 0F)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = visibility
    }

    // To animate view slide out from right to left
    fun slideToLeft(view: View, visibility: Int, duration: Int) {
        val animate = TranslateAnimation(0F, (-view.width).toFloat(), 0F, 0F)
        animate.duration = duration.toLong()
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = visibility
    }

    // To animate view slide out from top to bottom
    fun slideToBottom(view: View, visibility: Int, duration: Int) {
        val animate = TranslateAnimation(0F, 0F, 0F, view.height.toFloat())
        animate.duration = duration.toLong()
        //        animate.setFillAfter(true);
        view.startAnimation(animate)
        view.visibility = visibility
    }

    // To animate view slide out from bottom to top
    fun slideToTop(view: View, visibility: Int, duration: Int) {
        val animate = TranslateAnimation(0F, 0F, 0F, view.height.toFloat())
        animate.duration = duration.toLong()
        //        animate.setFillAfter(true);
        view.startAnimation(animate)
        view.visibility = visibility
    }

    fun replaceString(oldText: String?): String? {
        if (oldText == null) return ""
        if (oldText.isEmpty()) return ""
        var resultText: String = oldText
        if (oldText.contains("\\n")) {
            resultText = resultText.replace("\\n", "\n")
        }
        if (resultText.contains("\r")) {
            resultText = resultText.replace("\r", "")
        }
        if (resultText.contains("\'")) {
            resultText = resultText.replace("\\'", "\'")
        }
        if (resultText.contains("&amp;")) {
            resultText = resultText.replace("&amp;", "&")
        }
        return resultText
    }
}