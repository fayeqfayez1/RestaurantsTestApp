package com.restauran.network.firebase.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseReferances {
    companion object {
        private const val OS = "os"
        const val IS_PRIVATE_ADDRESS = "isPrivateAddress"
        const val IS_PRIVATE_EMAIL = "isPrivateEmail"
        const val IS_PRIVATE_MOBILE = "isPrivateMobile"
        const val IS_PRIVATE_GENDER = "isPrivateGender"
        const val IS_PRIVATE_DOB = "isPrivateDOB"
        const val IS_ACTIVE = "isActive"
        const val USERS_REF = "users"
        const val TOKEN_REF = "deviceToken"
        const val BLOCKED_USERS_REF = "blockedUsers"
        const val REPORTED_USERS_REF = "reportedUsers"
        const val CONVERSATIONS_USERS_REF = "conversations"
        const val GROUP_CONVERSATIONS_REF = "groupsConversations"
        const val NOTIFICATIONS_REF = "notifications"
        private var instance: FirebaseReferances? = null

        fun getInstance(): FirebaseReferances? {
            if (instance == null) instance = FirebaseReferances()
            return instance
        }

        fun getAuthReference(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        fun getAuthCurrentUserReference(): FirebaseUser? {
            return FirebaseAuth.getInstance().currentUser
        }

        fun getDatabaseReference(): DatabaseReference {
            val scoresRef = FirebaseDatabase.getInstance().reference
            scoresRef.keepSynced(true)
            return scoresRef
        }

        fun getUsersListReference(): DatabaseReference {
            return getDatabaseReference()
                .child(USERS_REF)
        }


        fun getUserReference(userId: String?): DatabaseReference {
            return getUsersListReference().child(userId!!)
        }

        fun getUserId(): String? {
            return if (getAuthReference().currentUser != null) getAuthReference().currentUser!!.uid else ""
        }

        fun saveUserDeviceOs(userId: String?): DatabaseReference? {
            return getUserReference(userId).child(OS)
        }

        fun saveUserFcmTokenReference(userId: String?): DatabaseReference? {
            return getUserReference(userId).child(TOKEN_REF)
        }

        fun getBlockedUsersReference(): DatabaseReference? {
            return getDatabaseReference().child(BLOCKED_USERS_REF)
        }

        fun getReportedUsersReference(): DatabaseReference? {
            return getDatabaseReference().child(REPORTED_USERS_REF)
        }

        /*firestore*/
        private fun getStorageReference(): StorageReference {
            return FirebaseStorage.getInstance().reference
        }
    }

}