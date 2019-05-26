package com.silvancodes.linksharefire

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class LinkShareActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.auth = FirebaseAuth.getInstance()

        val currentUser = this.auth.currentUser

        if (currentUser != null) {
            this.intent.extras?.getString("android.intent.extra.TEXT")?.let {
                Log.d("myAppMain", it)
                startLinkShareJob(it)
            }
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        finish()
    }

    private fun startLinkShareJob(website: String) {
        val jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val builder = JobInfo.Builder(1, ComponentName(this, LinkShareService::class.java))

        val bundle = PersistableBundle()
        bundle.putString("WEBSITE", website)

        builder.run {
            setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            setExtras(bundle)
        }

        jobScheduler.schedule(builder.build())
    }

}