package com.silvancodes.linksharefire

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

open class LinkShareAsyncTask(private val context: Context) : AsyncTask<String, Void, Boolean>() {
    override fun doInBackground(vararg param: String): Boolean {
        Log.d("DEBUG", "from async task: " + param[0])

        FirebaseAuth.getInstance().currentUser?.uid?.let {
            val db = FirebaseFirestore.getInstance()

            val newWebsite = HashMap<String, Any>()
            newWebsite["url"] = param[0]
            newWebsite["fresh"] = true
            newWebsite["uid"] = it

            db.collection("links").add(newWebsite)
        }

        return true
    }
}