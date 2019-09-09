package ha.ecz.com.subscriberpanel

import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import ha.ecz.com.agentportal.R
import ha.ecz.com.subscriberpanel.Utils.Parser

@Suppress("DEPRECATION")
class AmenityDynamicActivity : AppCompatActivity() {

    private var pd: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amenity_dynamic)
        val obj = AmenityDynamicTask()
        obj.execute()

    }

    inner class AmenityDynamicTask : AsyncTask<Void, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog(this@AmenityDynamicActivity)
            pd!!.isIndeterminate = false
            pd!!.setTitle("Please wait")
            pd!!.setMessage("Loading...")
            pd!!.show()
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            pd!!.hide()
            Toast.makeText(this@AmenityDynamicActivity, "Testing", Toast.LENGTH_LONG).show()
        }


        override fun doInBackground(vararg voids: Void): String? {

            val url = "http://rehajomobileapi.hundredalpha.com/api/Amenities/GETAmenityDynamicControls/?PropertyID=1046&CategID=4"
            return Parser.Get(url)
        }
    }
}
