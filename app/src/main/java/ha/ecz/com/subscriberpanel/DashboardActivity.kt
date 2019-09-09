package ha.ecz.com.subscriberpanel

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View

import android.widget.LinearLayout
import com.shashank.sony.fancytoastlib.FancyToast
import ha.ecz.com.agentportal.R
import kotlinx.android.synthetic.main.activity_dashboard.*



@Suppress("DEPRECATION")
class DashboardActivity : AppCompatActivity(), View.OnClickListener  {

    private var _btnManageProperty: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)

        _btnManageProperty = findViewById(R.id.btnManageProperty)
        _btnManageProperty!!.setOnClickListener(this)

        layout_lead.setOnClickListener {
            FancyToast.makeText(this@DashboardActivity,"This Feature will be available soon",FancyToast.LENGTH_SHORT,FancyToast.INFO,R.mipmap.ic_launcher,true).show()

        }
        layout_rev.setOnClickListener {
            FancyToast.makeText(this@DashboardActivity,"This Feature will be available soon",FancyToast.LENGTH_SHORT,FancyToast.INFO,R.mipmap.ic_launcher,true).show()

        }
        layout_todo.setOnClickListener {
            FancyToast.makeText(this@DashboardActivity,"This Feature will be available soon",FancyToast.LENGTH_SHORT,FancyToast.INFO,R.mipmap.ic_launcher,true).show()

        }
        layout_exp.setOnClickListener {
            FancyToast.makeText(this@DashboardActivity,"This Feature will be available soon",FancyToast.LENGTH_SHORT,FancyToast.INFO,R.mipmap.ic_launcher,true).show()

        }
        layout_set.setOnClickListener {
            FancyToast.makeText(this@DashboardActivity,"This Feature will be available soon",FancyToast.LENGTH_SHORT,FancyToast.INFO,R.mipmap.ic_launcher,true).show()

        }

    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btnManageProperty -> {
            //    dots.start();
                val sa = Intent(this@DashboardActivity, PropertiesActivity::class.java)
                startActivity(sa)
            }
            R.id.layout_medicine -> {
                val ma = Intent(this@DashboardActivity, PropertiesActivity::class.java)
                startActivity(ma)
            }
            R.id.layout_disease -> {
                val da = Intent(this@DashboardActivity, PropertiesActivity::class.java)
                startActivity(da)
            }
            R.id.layout_device_issued -> {
                val di = Intent(this@DashboardActivity, PropertiesActivity::class.java)
                startActivity(di)
            }
            R.id.layout_prescription -> {
                val pi = Intent(this@DashboardActivity, PropertiesActivity::class.java)
                startActivity(pi)
            }
            R.id.layout_test -> {
                val ti = Intent(this@DashboardActivity, PropertiesActivity::class.java)
                startActivity(ti)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            logoout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logoout(){

        val i = Intent(this@DashboardActivity, LoginActivity::class.java)
        startActivity(i)
        val settings = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = settings.edit()
        editor.remove("adminObj")
        editor.apply()
        finish()
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAndRemoveTask()
        }
    }
}

