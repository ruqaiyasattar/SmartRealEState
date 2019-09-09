package ha.ecz.com.subscriberpanel

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.view.animation.OvershootInterpolator
import android.widget.Switch
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.google.gson.Gson
import ha.ecz.com.agentportal.R
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter
import ha.ecz.com.subscriberpanel.Models.ApplicationUser
import ha.ecz.com.subscriberpanel.Models.Property
import ha.ecz.com.subscriberpanel.RESTService.CVStringResp
import ha.ecz.com.subscriberpanel.RESTService.VolleyStrCallback
import ha.ecz.com.subscriberpanel.Utils.Utilities
import kotlinx.android.synthetic.main.activity_properties.*
import ha.ecz.com.subscriberpanel.Models.MyBounceInterpolator
import androidx.recyclerview.widget.DividerItemDecoration
import com.daimajia.androidanimations.library.sliders.SlideInUpAnimator


@Suppress("DEPRECATION", "DEPRECATED_IDENTITY_EQUALS")
class PropertiesActivity : AppCompatActivity() {

    private var _objContext: Context? = null
    private val _objGSON = Gson()
    private var _objApp: ApplicationUser? = null
    lateinit var objPA: PropertiesAdapter

    companion object {

        var REQ_CODE: Int = 100
    }

    var x: Array<Property> = arrayOf()
        set(value) {
            y = convertArrayIntoArrayList(value)
            field = value
        }
        get() = y.toTypedArray()

    private lateinit var mPropertyArrayList: ArrayList<Property>
    var y: ArrayList<Property> = arrayListOf()

    private fun convertArrayIntoArrayList(_propsData: Array<Property>): ArrayList<Property> {
        mPropertyArrayList = ArrayList()
        _propsData.forEach {
            mPropertyArrayList.add(it)
        }

        return mPropertyArrayList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_properties)

        setSupportActionBar(toobar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        _objContext = applicationContext

        _objApp = _objGSON.fromJson(Utilities.getPreference(_objContext, "adminObj", ""), ApplicationUser::class.java)

        rvProperties.layoutManager = LinearLayoutManager(this@PropertiesActivity)
        rvProperties.addItemDecoration(DividerItemDecoration(rvProperties.context, LinearLayoutManager.VERTICAL))
        // rvProperties.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        btnAddProperty.startAnimation(myAnim)
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = MyBounceInterpolator()
        myAnim.interpolator = interpolator


        btnAddProperty.setOnClickListener {

            val intent = Intent(this@PropertiesActivity, ManagePropertyMasterActivity::class.java)

            val userId = _objApp!!.UserId.toString()
            val subId = _objApp!!.SubscriberId.toString()

            intent.putExtra("userId", userId)
            intent.putExtra("subId", subId)
            startActivityForResult(intent, REQ_CODE)

        }

    }

    override fun onResume() {
        super.onResume()
        val sbParams = StringBuilder()
        sbParams.append("UserID=" + _objApp!!.UserId.toString())
        sbParams.append("&SubsID=" + _objApp!!.SubscriberId.toString())
        sbParams.append("&AuthToken=" + _objApp!!.AuthToken)

        val objRR = CVStringResp()
        objRR.stringReqResp(this@PropertiesActivity, Request.Method.GET,
                "Properties/GETPropertiesSearch/?$sbParams", object : VolleyStrCallback {
            @Throws(Exception::class)
            override fun onSuccess(result: String?) {
                if (result != null) {
                    if (result.isNotEmpty() && result !== "[]") {

                        x = _objGSON.fromJson(result, Array<Property>::class.java)
                        objPA = PropertiesAdapter(y) {
                            y.removeAt(it)
                        }
                        rvProperties.adapter = objPA


                    } else {
                        Toast.makeText(_objContext, "No record found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(_objContext, "No record found", Toast.LENGTH_SHORT).show()
                }
            }

            @Throws(Exception::class)
            override fun onError(result: String) {
                Log.e("Err From Out Call", result)
                Toast.makeText(_objContext, result, Toast.LENGTH_SHORT).show()
            }
        }, null)


    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        objPA = PropertiesAdapter(y) {
            y.removeAt(it)
        }
        rvProperties.adapter = objPA

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true
    }

    override fun onBackPressed() {
        ActivityCompat.finishAffinity(this)
    }


}
