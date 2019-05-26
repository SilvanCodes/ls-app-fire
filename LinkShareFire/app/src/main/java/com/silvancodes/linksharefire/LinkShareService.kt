package com.silvancodes.linksharefire

import android.app.job.JobParameters
import android.app.job.JobService
import android.widget.Toast

class LinkShareService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        params?.extras?.getString("WEBSITE")?.let {
            Toast.makeText(this, "Sharing $it...", Toast.LENGTH_LONG).show()
            val task =  object: LinkShareAsyncTask(this) {
                override fun onPostExecute(result: Boolean) {
                    super.onPostExecute(result)
                    jobFinished(params, !result)
                }
            }

            task.execute(it);
            return true
        }
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        TODO("not implemented, put cleanup logic here") //To change body of created functions use File | Settings | File Templates.
    }
}