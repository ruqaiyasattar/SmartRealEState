@file:Suppress("DEPR ECATION", "PLUGIN_WARNING", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "DEPRECATION")

package ha.ecz.com.subscriberpanel

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.asksira.bsimagepicker.BSImagePicker
import com.google.gson.Gson
import ha.ecz.com.subscriberpanel.Models.*
import ha.ecz.com.subscriberpanel.Models.Unit
import org.json.JSONArray
import org.json.JSONException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils.isEmpty
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_manage_property_master.*
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.Place.*
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import ha.ecz.com.agentportal.R
import ha.ecz.com.subscriberpanel.Adapter.AddMultipleImagesadapter
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.AC
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.Maid
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.Owner
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.dinroom
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.drawroom
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.furn
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.gymm
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.jucizi
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mCol
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mLawn
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mScho
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mUni
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.maop
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mbath
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mbed
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mboard
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.melectric
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.minter
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mkit
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mlaundry
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mloung
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mmsjid
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mpark
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mserv
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mstrom
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mstudy
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mtv
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mval
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.mview
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.parking
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.pool
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.prayrom
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.singleimg
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.storage
import ha.ecz.com.subscriberpanel.ManagePropertyMasterActivity.Companion.updatePropertyId
import ha.ecz.com.subscriberpanel.Models.Currency
import ha.ecz.com.subscriberpanel.PropertiesActivity.Companion.REQ_CODE
import ha.ecz.com.subscriberpanel.Utils.Utilities
import ha.ecz.com.subscriberpanel.model.StringwithTag
import ha.ecz.com.subscriberpanel.retrofit.APIService
import ha.ecz.com.subscriberpanel.retrofit.ApiUtils
import kotlinx.android.synthetic.main.amaliyity_details.*
import kotlinx.android.synthetic.main.dialog_custom.view.*
import okhttp3.ResponseBody
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import org.xml.sax.Parser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Query
import java.lang.Double.*
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.roundToInt


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATED_IDENTITY_EQUALS")
open class ManagePropertyMasterActivity : AppCompatActivity(), BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener {


    private var abc: String = ""
    private var model: ManageProperty? = null
    private var imgModel: MultiImgModel? = null
    private var getData: GetData? = null
    private var key: String? = null
    private var count = 1
    private var spPropertyType: Spinner? = null
    private var spCity: Spinner? = null
    private var spPurpose: Spinner? = null
    private var spCurrency: Spinner? = null
    private var spAreaUnit: Spinner? = null
    private var spStatus: Spinner? = null
    private var spPrivacy: Spinner? = null
    private var _url: String? = null
    lateinit var cities: ArrayList<City>
    lateinit var proprties: ArrayList<PropertyCategory>
    lateinit var curency: ArrayList<Currency>
    lateinit var purpose: ArrayList<Purpose>
    lateinit var unitA: ArrayList<Unit>
    lateinit var stat: ArrayList<StatusM>
    lateinit var privacy: ArrayList<PrivacyM>

    var encImage: String = ""
    var mimgs: String? = ""
    var creatID: Int = 0
    var subsID: Int = 0

    private var _objContext: Context? = null
    private val _objGSON = Gson()
    private var _objApp: ApplicationUser? = null
    private var mAPIService: APIService? = null

    val mapimg = HashMap<String, Any>()
    val img = "data:image/jpeg;base64,"

    val imgstrt = "data:image/jpeg;base64,"
    val mapupdate = HashMap<String, Any>()

    companion object {
        var cityName: String? = ""
        val imgsList = arrayListOf<String>()
        var TAG_ID: Any = 0
        var updatePropertyId: Int = 0
        var multiencImage: String = " "
        var areaName = ""
        var addres = " "
        var lat: Double = 0.0
        var long: Double = 0.0
        var subloc = 0
        var uriList: MutableList<Uri>? = null
        var sub: Int = 0
        var userpre: Int = 0
        var imgs: ArrayList<Uri>? = null
        var imgs_pref: String? = null
        var ImgUrl: ArrayList<String>? = null

        var ImgUrlall: ArrayList<String>? = null
    }

    override fun onSingleImageSelected(uri: Uri?, tag: String?) {
        doAsync {

            val imageFile = java.io.File(uri?.path)
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(imageFile)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            val bm: Bitmap = BitmapFactory.decodeStream(fis)
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            encImage = Base64.encodeToString(b, Base64.DEFAULT)

            uiThread {

                Glide.with(this@ManagePropertyMasterActivity).load(uri).into(frontimg)


            }

        }
    }

    @SuppressLint("ClickableViewAccessibility", "UseSparseArrays")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_manage_property_master)
        setSupportActionBar(toolbf)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAPIService = ApiUtils.apiService

        val list = ArrayList<StringwithTag>()

        list.add(StringwithTag("-Select-", 0))
        list.add(StringwithTag("Generator", 1))
        list.add(StringwithTag("UPS", 2))
        list.add(StringwithTag("Solar", 3))
        val adap = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
        ebackup.adapter = adap

        setSupportActionBar(toolbf)

        var cityId = 0
        var propertyId = 0
        var unitAreaId = 0
        var currncyId = 0
        var purpId = 0
        var privcyId = 1
        var statId = 2

        //data retrieve from property adapter
        updatePropertyId = intent.getIntExtra("proid", 0)

        //To save idPr0perty
        val settings = this.getSharedPreferences("YOUR_PRE_ID", 0)
        val editor = settings.edit()
        editor.putInt("SHOW_ID", updatePropertyId)
        editor.apply()

        val mAmen: Int = intent.getIntExtra("mAmen", 1)
        val spin_priv: Int = intent.getIntExtra("privacyid", 1)
        val cityid: Int = intent.getIntExtra("cityid", 1)
        val pty_id: Int = intent.getIntExtra("propertytypeid", 1)
        val purp_id: Int = intent.getIntExtra("purposid", 1)
        val curr_id: Int = intent.getIntExtra("curncyid", 1)
        val area_id: Int = intent.getIntExtra("areaunitid", 1)
        val active_id: Int = intent.getIntExtra("Activeid", 1)
        val title: String? = intent.getStringExtra("title")
        val street: String? = intent.getStringExtra("street")
        val price: String = intent.getDoubleExtra("price", 10.0).toString()
        val landarea: String = intent.getDoubleExtra("landarea", 10.0).toString()
        val _address: String? = intent.getStringExtra("address")
        val description: String? = intent.getStringExtra("description")
        val ispromo: Boolean = intent.getBooleanExtra("ispromo", false)
        val isfeature: Boolean = intent.getBooleanExtra("isfeature", false)
        val ishot: Boolean = intent.getBooleanExtra("ishot", false)
        val ispopout: Boolean = intent.getBooleanExtra("ispopout", false)
        // val fimg = intent.getStringExtra("imgfront")
        val user: Int = intent.getIntExtra("userId", 1)
        sub = intent.getIntExtra("subId", 1)
        val onpropertyid = intent.getIntExtra("proid", 1)

        //To save CREAT_ID
        val settings_creatID = this.getSharedPreferences("CREAT_ID", 0)
        val editor_creatID = settings_creatID.edit()
        editor_creatID.putInt("SNOW_CREAT_ID", user)
        editor_creatID.apply()

        //To retrieve CREAT_ID
        val gettings_creatID: SharedPreferences = this.getSharedPreferences("CREAT_ID", 0)
        creatID = gettings_creatID.getInt("SNOW_CREAT_ID", 0) //0 is the default value

        //To save SUb_ID
        val settings_SUB_ID = this.getSharedPreferences("SUB_ID", 0)
        val editor_SUB_ID = settings_SUB_ID.edit()
        editor_SUB_ID.putInt("SNOW_SUB_ID", sub)
        editor_SUB_ID.apply()

        //To retrieve Sub_ID
        val gettings_Sub_ID: SharedPreferences = this.getSharedPreferences("SUB_ID", 0)
        subsID = gettings_Sub_ID.getInt("SNOW_SUB_ID", 0) //0 is the default value

        abc = onpropertyid.toString()

        val txtTitle = findViewById<View>(R.id.txtPropertyTitle) as TextView
        val txtLandArea = findViewById<View>(R.id.txtLandArea) as TextView
        val txtAddress = findViewById<View>(R.id.txtPropAddress) as TextView
        val txtDescription = findViewById<TextView>(R.id.txtDescription)
        val txtPrice = findViewById<View>(R.id.txtPrice) as TextView
        val textstreet = findViewById<View>(R.id.placeName) as TextView
        val ishott = findViewById<View>(R.id.isHot) as Switch
        val isfeat = findViewById<Switch>(R.id.isFeature)
        val isprom = findViewById<Switch>(R.id.isPromo)
        val ispop = findViewById<Switch>(R.id.isPop)
        val cid = findViewById<Spinner>(R.id.cmbCity)
        val ptyid = findViewById<Spinner>(R.id.cmbPropertyType)
        val purid = findViewById<Spinner>(R.id.cmbPurpose)
        val curid = findViewById<Spinner>(R.id.cmbCurrency)
        val areaid = findViewById<Spinner>(R.id.cmbAreaUnit)
        val privid = findViewById<Spinner>(R.id.cmbPrivacy)
        val activid = findViewById<Spinner>(R.id.cmbActive)

        //amentity
        val amenview = findViewById<View>(R.id.view_main) as TextView
        val amenaop = findViewById<View>(R.id.aop) as TextView
        val amenbackup = findViewById<Spinner>(R.id.ebackup)
        val amenparking = findViewById<CheckBox>(R.id.park)
        val amenac = findViewById<CheckBox>(R.id.ac)
        val amenfurnished = findViewById<CheckBox>(R.id.furnished)
        val amenstorage = findViewById<CheckBox>(R.id.storg)
        val amenown = findViewById<CheckBox>(R.id.own)
        val amenuni = findViewById<CheckBox>(R.id.uni)
        val amencol = findViewById<CheckBox>(R.id.col)
        val amenschol = findViewById<CheckBox>(R.id.schol)
        val amengarden = findViewById<CheckBox>(R.id.garden)
        val amenmasjid = findViewById<CheckBox>(R.id.msjid)
        val amenservent = findViewById<View>(R.id.servant_quart) as TextView
        val amenkitchen = findViewById<View>(R.id.kitchecn) as TextView
        val amenbed = findViewById<View>(R.id.bed_room) as TextView
        val amenstorom = findViewById<View>(R.id.stor_room) as TextView
        val amenbathrom = findViewById<View>(R.id.bath_room) as TextView
        val amenmaid = findViewById<CheckBox>(R.id.maid)
        val amenstudy = findViewById<CheckBox>(R.id.stdy)
        val amenlundryy = findViewById<CheckBox>(R.id.lundry)
        val amengym = findViewById<CheckBox>(R.id.gym)
        val amenpryer = findViewById<CheckBox>(R.id.prayer)
        val amendin = findViewById<CheckBox>(R.id.dinning)
        val amendraw = findViewById<CheckBox>(R.id.draw)
        val amenlonge = findViewById<CheckBox>(R.id.launge)
        val ameninter = findViewById<CheckBox>(R.id.intercm)
        val amentv = findViewById<CheckBox>(R.id.cable)
        val amenbroad = findViewById<CheckBox>(R.id.broad)
        val amenlawn = findViewById<CheckBox>(R.id.lagard)
        val amenswimm = findViewById<CheckBox>(R.id.swimm)
        val amenjac = findViewById<CheckBox>(R.id.jac)
        val amensuana = findViewById<CheckBox>(R.id.suana)

        model = ManageProperty()
        imgModel = MultiImgModel()

        _objContext = applicationContext
        _objApp = _objGSON.fromJson(Utilities.getPreference(_objContext, "adminObj", ""), ApplicationUser::class.java)

        if (updatePropertyId != 0) {
            val settingsmelectric = this.getSharedPreferences("SUBmelectric", 0)
            val editormelectric = settingsmelectric.edit()
            editormelectric.putInt("SNOWmelectric", mval!!)
            editormelectric.apply()

            //To retrieve Sub_ID
            val gettingsmelectric: SharedPreferences = this.getSharedPreferences("SUBmelectric", 0)
            val gotvalue = gettingsmelectric.getInt("SNOWmelectric", 0) //0 is the default value
           gotvalue.let { ebackup.setSelection(it) }


            ApiUtils.apiService.getImagesPhotos(updatePropertyId).enqueue(object : Callback<GetAdditionalImage> {
                override fun onResponse(call: Call<GetAdditionalImage>, response: retrofit2.Response<GetAdditionalImage>) {

                    rv_multiimgs.layoutManager = LinearLayoutManager(this@ManagePropertyMasterActivity, LinearLayoutManager.HORIZONTAL, false)

                    val objPA = AddMultipleImagesadapter(response.body()?.GetAdditionalImage)
                    rv_multiimgs.adapter = objPA

                    objPA.notifyDataSetChanged()

                }

                override fun onFailure(call: Call<GetAdditionalImage>, t: Throwable) {
                    Toast.makeText(this@ManagePropertyMasterActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
                }

            })
            amenities.visibility = View.VISIBLE
            save.visibility = View.GONE
            submit_data.visibility = View.GONE

            txtTitle.text = title.toString()
            txtAddress.text = _address.toString()
            txtDescription.text = description.toString()
            txtLandArea.text = landarea

            txtPrice.text = price
            textstreet.text = street

            ishott.isChecked = ishot
            isfeat.isChecked = isfeature
            isprom.isChecked = ispromo
            ispop.isChecked = ispopout

            cid.setSelection(cityid)
            ptyid.setSelection(pty_id)
            purid.setSelection(purp_id)
            curid.setSelection(curr_id)
            areaid.setSelection(area_id)
            privid.setSelection(spin_priv)
            activid.setSelection(active_id)

            amenstorage.isChecked =  storage?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenview.text = mview.toString()
            amendraw.isChecked = drawroom?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenown.isChecked = Owner?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenmaid.isChecked = Maid?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenparking.isChecked = parking?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenac.isChecked = AC?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenfurnished.isChecked = furn?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenuni.isChecked = mUni?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amengarden.isChecked = mpark?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amencol.isChecked = mCol?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenmasjid.isChecked = mmsjid?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenschol.isChecked = mScho?.toDoubleOrNull()?.let { doubtobool(it) }!!
            ameninter.isChecked = minter?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenbroad.isChecked = mboard?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amentv.isChecked = mtv?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenlawn.isChecked = mLawn?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenswimm.isChecked = pool?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amensuana.isChecked = PropertiesAdapter.suana?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenjac.isChecked = jucizi?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenstudy.isChecked = mstudy?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenlundryy.isChecked = mlaundry?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amengym.isChecked = gymm?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenbathrom.text = stringtoint(mbath?.toString())?.toString()
            amenservent.text = stringtoint(mserv?.toString())?.toString()
            amenpryer.isChecked = prayrom?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenkitchen.text = stringtoint(mkit?.toString())?.toString()

            val df = DecimalFormat("#.###")
            df.roundingMode = RoundingMode.CEILING

            amenaop.text = stringtoint(maop.toString())?.toString()
            amenlonge.isChecked = mloung?.toDoubleOrNull()?.let { doubtobool(it) }!!
            amenbed.text = stringtoint(mbed.toString())?.toString()
            amenstorom.text = stringtoint(mstrom.toString())?.toString()
            amendin.isChecked = dinroom?.toDoubleOrNull()?.let { doubtobool(it) }!!

            val myOptions = RequestOptions()
                    .override(100, 100).centerCrop()
            Glide.with(this)
                    .asBitmap()
                    .apply(myOptions)
                    .load(singleimg)
                    .centerCrop().fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(frontimg)
        }

        textstreet.text = street
        cities = ArrayList()
        proprties = ArrayList()
        unitA = ArrayList()
        curency = ArrayList()
        purpose = ArrayList()
        stat = ArrayList()    //active
        privacy = ArrayList() //privacy
        spStatus = findViewById(R.id.cmbActive)
        spPropertyType = findViewById(R.id.cmbPropertyType)
        spCurrency = findViewById(R.id.cmbCurrency)
        spCity = findViewById(R.id.cmbCity)
        spAreaUnit = findViewById(R.id.cmbAreaUnit)
        spPurpose = findViewById(R.id.cmbPurpose)
        spPrivacy = findViewById(R.id.cmbPrivacy)

        getAsync()

        property_info.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Property Title")
            builder.setMessage("Type Title That Also Contain A Unique Feature Of Your Property (Only 70 Characters Allowed")

            val alert = builder.create()
            alert.show()

            builder.setNegativeButton("OK") { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }
        }

        txtDescription.setOnTouchListener { view, event ->
            if (view.id == R.id.txtDescription) {
                view.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }

        aop_info.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Age Of Property")
            builder.setMessage("Only Numeric Characters Allowed")

            val alert = builder.create()
            alert.show()

            builder.setNegativeButton("OK") { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }
        }

        servnt_info.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Servant Quarters")
            builder.setMessage("Only Numeric Characters Allowed")

            val alert = builder.create()
            alert.show()

            builder.setNegativeButton("OK") { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }
        }

        kitchen_info.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No. of Kitchen")
            builder.setMessage("Only Numeric Characters Allowed")

            val alert = builder.create()
            alert.show()

            builder.setNegativeButton("OK") { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }
        }

        bed_info.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No. of Bed Room")
            builder.setMessage("Only Numeric Characters Allowed")

            val alert = builder.create()
            alert.show()

            builder.setNegativeButton("OK") { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }
        }

        store_info.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No. of Store Room")
            builder.setMessage("Only Numeric Characters Allowed")

            val alert = builder.create()
            alert.show()

            builder.setNegativeButton("OK") { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }
        }

        bath_info.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No. of Bath Room")
            builder.setMessage("Only Numeric Characters Allowed")

            val alert = builder.create()
            alert.show()

            builder.setNegativeButton("OK") { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }
        }

        save.setOnClickListener {

            if (txtPropertyTitle.text.toString().trim().isEmpty() && placeName.text.toString().trim().isEmpty() &&
                    txtPrice.text.toString().trim().isEmpty() && txtLandArea.text.toString().trim().isEmpty() &&
                    txtPropAddress.text.toString().trim().isEmpty() && txtDescription.text.toString().trim().isEmpty()) {

                Snackbar.make(it, "Fill All Required Fields", Snackbar.LENGTH_LONG).show()

            } else {
                amenities.visibility = View.VISIBLE
                save.visibility = View.GONE

                update_submit.visibility = View.GONE

                scroll.post { scroll.fullScroll(ScrollView.FOCUS_DOWN) }

            }

        }

        tv_single_selection.setOnClickListener {

            val pickerDialog = com.asksira.bsimagepicker.BSImagePicker.Builder("com.asksira.imagepickersheetdemo.fileprovider")
                    .build()
            pickerDialog.show(supportFragmentManager, "picker")

        }

        tv_multi_selection.setOnClickListener {

            val pickerDialog = com.asksira.bsimagepicker.BSImagePicker.Builder("com.asksira.imagepickersheetdemo.fileprovider")
                    .setMaximumDisplayingImages(Integer.MAX_VALUE)
                    .isMultiSelect
                    .setGridSpacing(7)
                    .setMinimumMultiSelectCount(1)
                    .setMaximumMultiSelectCount(30)
                    .build()
            pickerDialog.show(supportFragmentManager, "picker")

        }

        ebackup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View,
                                        pos: Int, id: Long) {
                val mSelected = parent.getItemAtPosition(pos) as StringwithTag

                val settings = applicationContext.getSharedPreferences("YOUR_ElEC_NAME", 0)
                val editor = settings.edit()
                editor.putInt("SNOW_TAG", mSelected.tag)
                editor.apply()

                //To retrieve idtag
                val getgs: SharedPreferences = applicationContext.getSharedPreferences("YOUR_ElEC_NAME", 0)
                TAG_ID = getgs.getInt("SNOW_TAG", 0) //0 is the default value

            }

            override fun onNothingSelected(arg0: AdapterView<*>) {
                // TODO Auto-generated method stub
                Log.i("Message", "Nothing is selected")
            }

        }

        if (mAmen == 31) {

            amenities.visibility = View.VISIBLE
            save.visibility = View.GONE

        }

        /*
        submit_data.setOnClickListener {

            doAsync {

                val pairKeyValueList = myData(

                        "46" to bath_room?.text?.toString(),
                        "47" to bed_room.text?.toString(),
                        "53" to if (isEmpty(launge.isChecked.toInt().toString())) " " else launge.isChecked.toInt().toString(),
                        "54" to if (isEmpty(lundry.isChecked.toInt().toString())) " " else lundry.isChecked.toInt().toString(),
                        "64" to if (isEmpty(draw.isChecked.toInt().toString())) " " else draw.isChecked.toInt().toString(),
                        "49" to if (isEmpty(TAG_ID.toString())) " " else TAG_ID.toString(),
                        "65" to if (isEmpty(dinning.isChecked.toInt().toString())) " " else dinning.isChecked.toInt().toString(),
                        "51" to if (isEmpty(prayer.isChecked.toInt().toString())) " " else prayer.isChecked.toInt().toString(),
                        "37" to if (isEmpty(maid.isChecked.toInt().toString())) " " else maid.isChecked.toInt().toString(),
                        "36" to if (isEmpty(stdy.isChecked.toInt().toString())) " " else stdy.isChecked.toInt().toString(),
                        "21" to if (isEmpty(msjid.isChecked.toInt().toString())) " " else msjid.isChecked.toInt().toString(),
                        "22" to if (isEmpty(garden.isChecked.toInt().toString())) " " else garden.isChecked.toInt().toString(),
                        "40" to if (isEmpty(schol.isChecked.toInt().toString())) " " else schol.isChecked.toInt().toString(),
                        "41" to if (isEmpty(col.isChecked.toInt().toString())) " " else col.isChecked.toInt().toString(),
                        "42" to if (isEmpty(uni.isChecked.toInt().toString())) " " else uni.isChecked.toInt().toString(),
                        "24" to if (isEmpty(own.isChecked.toInt().toString())) " " else own.isChecked.toInt().toString(),
                        "26" to if (isEmpty(furnished.isChecked.toInt().toString())) " " else furnished.isChecked.toInt().toString(),
                        "35" to if (isEmpty(storg.isChecked.toInt().toString())) " " else storg.isChecked.toInt().toString(),
                        "32" to if (isEmpty(view_main.text?.toString())) " " else view_main.text?.toString(),
                        "55" to if (isEmpty(lagard.isChecked.toInt().toString())) " " else lagard.isChecked.toInt().toString(),
                        "56" to if (isEmpty(swimm.isChecked.toInt().toString())) " " else swimm.isChecked.toInt().toString(),
                        "57" to if (isEmpty(gym.isChecked.toInt().toString())) " " else gym.isChecked.toInt().toString(),
                        "58" to if (isEmpty(suana.isChecked.toInt().toString())) " " else suana.isChecked.toInt().toString(),
                        "59" to if (isEmpty(jac.isChecked.toInt().toString())) " " else jac.isChecked.toInt().toString(),
                        "60" to if (isEmpty(broad.isChecked.toInt().toString())) " " else broad.isChecked.toInt().toString(),
                        "61" to if (isEmpty(cable.isChecked.toInt().toString())) " " else cable.isChecked.toInt().toString(),
                        "62" to if (isEmpty(intercm.isChecked.toInt().toString())) " " else intercm.isChecked.toInt().toString(),
                        "38" to if (isEmpty(park.isChecked.toInt().toString())) " " else park.isChecked.toInt().toString(),
                        "48" to if (isEmpty(ac.isChecked.toInt().toString())) " " else ac.isChecked.toInt().toString(),
                        "45" to if (isEmpty(aop.text?.toString())) " " else aop.text?.toString(),
                        "50" to servant_quart.text?.toString(),
                        "52" to kitchecn.text?.toString(),
                        "63" to stor_room.text?.toString())

                map["title"] = if (isEmpty(txtTitle.text.toString())) " " else txtTitle.text.toString()
                map["PropertyCategoryID"] = if (isEmpty(propertyId.toString())) " " else propertyId
                map["CityID"] = if (isEmpty(cityId.toString())) " " else cityId
                map["streetName"] = if (isEmpty(areaName)) " " else areaName
                map["Address"] = if (isEmpty(txtAddress.text.toString())) " " else txtAddress.text.toString()
                map["LocationAlias"] = if (isEmpty(areaName)) " " else areaName
                map["Address_Component"] = if (isEmpty(addres)) " " else addres
                map["SubLocalityLevel"] = if (isEmpty(subloc.toString())) " " else subloc.toString()
                map["Latitude"] = if (isEmpty(lat.toString())) " " else lat
                map["Longitude"] = if (isEmpty(long.toString())) " " else long
                map["PurposeID"] = if (isEmpty(purpId.toString())) " " else purpId
                map["CurrencyID"] = if (isEmpty(currncyId.toString())) " " else currncyId
                map["PriceBudget"] = if (isEmpty(parseDouble(txtPrice.text.toString()).toString())) " " else parseDouble(txtPrice.text.toString())
                map["LandArea"] = if (isEmpty(parseDouble(txtLandArea.text.toString()).toString())) " " else parseDouble(txtLandArea.text.toString())
                map["UnitID"] = if (isEmpty(unitAreaId.toString())) " " else unitAreaId
                map["Privacy"] = if (isEmpty(privcyId.toString())) " " else privcyId
                map["Active"] = if (isEmpty(statId.toString())) " " else statId
                map["IsHot"] = if (isEmpty(isHot.isChecked.toString())) " " else isHot.isChecked
                map["IsFeatured"] = if (isEmpty(isFeature.isChecked.toString())) " " else isFeature.isChecked
                map["IsPromo"] = if (isEmpty(isPromo.isChecked.toString())) " " else isPromo.isChecked
                map["IsPopOut"] = if (isEmpty(isPop.isChecked.toString())) " " else isPop.isChecked
                map["SubscriberID"] = if (isEmpty(_objApp!!.SubscriberId.toString())) " " else _objApp!!.SubscriberId
                map["CreatedBy"] = if (isEmpty(_objApp!!.UserId.toString())) " " else _objApp!!.UserId
                map["FrontImage"] = img.plus(this@ManagePropertyMasterActivity.encImage)
                map["Description"] = if (isEmpty(txtDescription.text.toString())) " " else txtDescription.text
                map["Amenities"] = if (isEmpty(pairKeyValueList.toString())) " " else pairKeyValueList

                val foos = Response(Parser.Post(url, map))
                val imgstrt = "data:image/jpeg;base64,"

                ImgUrl!!.forEach {

                    val myurl = Uri.parse(it)
                    val imageFile = java.io.File(myurl?.path)

                    var fis: FileInputStream? = null
                    try {
                        fis = FileInputStream(imageFile)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                    val bm: Bitmap = BitmapFactory.decodeStream(fis)
                    val baos = ByteArrayOutputStream()
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val b = baos.toByteArray()

                    val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
                    imgModel!!.additionalImages = imgstrt.plus(encodedImage)
                    imgModel!!.propertyID = Integer.parseInt(foos.get("PropertyID").toString())

                    mapimg["PropertyID"] = foos.get("PropertyID").toString()
                    mapimg["AdditionalImage"] = imgstrt.plus(encodedImage)
                    Parser.Post(addimgs, mapimg)

                }

                uiThread {
                    Log.d("foos", foos.toString())
                    Log.d("map", map.toString())
                }

            }

            setResult(REQ_CODE, intent)
            val intent = Intent(this, PropertiesActivity::class.java)
            startActivity(intent)
            finish()
        }
*/

        submit_data.setOnClickListener {

            val pairKeyValueList = myData(

                    "46" to bath_room?.text?.toString(),
                    "47" to bed_room?.text?.toString(),
                    "53" to if (isEmpty(launge.isChecked.toInt().toString())) " " else launge.isChecked.toInt().toString(),
                    "54" to if (isEmpty(lundry.isChecked.toInt().toString())) " " else lundry.isChecked.toInt().toString(),
                    "64" to if (isEmpty(draw.isChecked.toInt().toString())) " " else draw.isChecked.toInt().toString(),
                    "49" to if (isEmpty(TAG_ID.toString())) " " else TAG_ID.toString(),
                    "65" to if (isEmpty(dinning.isChecked.toInt().toString())) " " else dinning.isChecked.toInt().toString(),
                    "51" to if (isEmpty(prayer.isChecked.toInt().toString())) " " else prayer.isChecked.toInt().toString(),
                    "37" to if (isEmpty(maid.isChecked.toInt().toString())) " " else maid.isChecked.toInt().toString(),
                    "36" to if (isEmpty(stdy.isChecked.toInt().toString())) " " else stdy.isChecked.toInt().toString(),
                    "21" to if (isEmpty(msjid.isChecked.toInt().toString())) " " else msjid.isChecked.toInt().toString(),
                    "22" to if (isEmpty(garden.isChecked.toInt().toString())) " " else garden.isChecked.toInt().toString(),
                    "40" to if (isEmpty(schol.isChecked.toInt().toString())) " " else schol.isChecked.toInt().toString(),
                    "41" to if (isEmpty(col.isChecked.toInt().toString())) " " else col.isChecked.toInt().toString(),
                    "42" to if (isEmpty(uni.isChecked.toInt().toString())) " " else uni.isChecked.toInt().toString(),
                    "24" to if (isEmpty(own.isChecked.toInt().toString())) " " else own.isChecked.toInt().toString(),
                    "26" to if (isEmpty(furnished.isChecked.toInt().toString())) " " else furnished.isChecked.toInt().toString(),
                    "35" to if (isEmpty(storg.isChecked.toInt().toString())) " " else storg.isChecked.toInt().toString(),
                    "32" to if (isEmpty(view_main.text?.toString())) " " else view_main.text?.toString(),
                    "55" to if (isEmpty(lagard.isChecked.toInt().toString())) " " else lagard.isChecked.toInt().toString(),
                    "56" to if (isEmpty(swimm.isChecked.toInt().toString())) " " else swimm.isChecked.toInt().toString(),
                    "57" to if (isEmpty(gym.isChecked.toInt().toString())) " " else gym.isChecked.toInt().toString(),
                    "58" to if (isEmpty(suana.isChecked.toInt().toString())) " " else suana.isChecked.toInt().toString(),
                    "59" to if (isEmpty(jac.isChecked.toInt().toString())) " " else jac.isChecked.toInt().toString(),
                    "60" to if (isEmpty(broad.isChecked.toInt().toString())) " " else broad.isChecked.toInt().toString(),
                    "61" to if (isEmpty(cable.isChecked.toInt().toString())) " " else cable.isChecked.toInt().toString(),
                    "62" to if (isEmpty(intercm.isChecked.toInt().toString())) " " else intercm.isChecked.toInt().toString(),
                    "38" to if (isEmpty(park.isChecked.toInt().toString())) " " else park.isChecked.toInt().toString(),
                    "48" to if (isEmpty(ac.isChecked.toInt().toString())) " " else ac.isChecked.toInt().toString(),
                    "45" to if (isEmpty(aop.text?.toString())) " " else aop.text?.toString(),
                    "50" to servant_quart?.text?.toString(),
                    "52" to kitchecn?.text?.toString(),
                    "63" to stor_room?.text?.toString())

            if (encImage != "") {

                sendPost(txtTitle.text.toString(), areaName, txtAddress.text.toString(), areaName, subloc.toString(), parseDouble(lat.toString()), parseDouble(long.toString())
                        , purpId.toInt(), currncyId.toInt(), parseDouble(txtPrice.text.toString()), parseDouble(txtLandArea.text.toString()), unitAreaId.toInt(), propertyId.toInt(), privcyId.toInt(),
                        isHot.isChecked, isFeature.isChecked, isPromo.isChecked, isPop.isChecked, _objApp!!.SubscriberId, _objApp!!.UserId,
                        img.plus(this@ManagePropertyMasterActivity.encImage), txtDescription.text.toString(), pairKeyValueList.toString(), cityId.toInt())

                setResult(REQ_CODE, intent)
                val intent = Intent(this, PropertiesActivity::class.java)
                startActivity(intent)
                finish()
            } else {

                sendPost(txtTitle.text.toString(), areaName, txtAddress.text.toString(), areaName, subloc.toString(), parseDouble(lat.toString()), parseDouble(long.toString())
                        , purpId, currncyId, parseDouble(txtPrice.text.toString()), parseDouble(txtLandArea.text.toString()), unitAreaId.toInt(), propertyId.toInt(), privcyId.toInt(),
                        isHot.isChecked, isFeature.isChecked, isPromo.isChecked, isPop.isChecked, _objApp!!.SubscriberId, _objApp!!.UserId,
                        encImage, txtDescription.text.toString(), pairKeyValueList.toString(), cityId.toInt())

                setResult(REQ_CODE, intent)
                val intent = Intent(this, PropertiesActivity::class.java)
                startActivity(intent)
            }
        }

        /*

        update_submit.setOnClickListener {

            val pairKeyValueList = myData(

                    "46" to stringtoint(bath_room.text?.toString()).toString(),
                    "47" to stringtoint(bed_room.text?.toString()).toString(),
                    "53" to launge.isChecked.toInt().toString(),
                    "54" to lundry.isChecked.toInt().toString(),
                    "64" to draw.isChecked.toInt().toString(),
                    "49" to ebackup.selectedItemPosition.toString(),
                    "65" to dinning.isChecked.toInt().toString(),
                    "51" to prayer.isChecked.toInt().toString(),
                    "37" to maid.isChecked.toInt().toString(),
                    "36" to stdy.isChecked.toInt().toString(),
                    "21" to msjid.isChecked.toInt().toString(),
                    "22" to garden.isChecked.toInt().toString(),
                    "40" to schol.isChecked.toInt().toString(),
                    "41" to col.isChecked.toInt().toString(),
                    "42" to uni.isChecked.toInt().toString(),
                    "24" to own.isChecked.toInt().toString(),
                    "26" to furnished.isChecked.toInt().toString(),
                    "35" to storg.isChecked.toInt().toString(),
                    "32" to view_main.text?.toString(),
                    "55" to lagard.isChecked.toInt().toString(),
                    "56" to swimm.isChecked.toInt().toString(),
                    "57" to gym.isChecked.toInt().toString(),
                    "58" to suana.isChecked.toInt().toString(),
                    "59" to jac.isChecked.toInt().toString(),
                    "60" to broad.isChecked.toInt().toString(),
                    "61" to cable.isChecked.toInt().toString(),
                    "62" to intercm.isChecked.toInt().toString(),
                    "38" to park.isChecked.toInt().toString(),
                    "48" to ac.isChecked.toInt().toString(),
                    "45" to aop.text?.toString(),
                    "50" to stringtoint(servant_quart.text?.toString()).toString(),
                    "52" to stringtoint(kitchecn.text?.toString()).toString(),
                    "63" to stringtoint(stor_room.text?.toString()).toString()).toString()

            doAsync {

                model!!.latitude = lat
                model!!.longitude = long
                model!!.purposeID = purpId
                model!!.currencyID = currncyId
                model!!.priceBudget = parseDouble(txtPrice.text.toString())
                model!!.landArea = parseDouble(txtLandArea.text.toString())
                model!!.landAreaUnitID = unitAreaId
                model!!.privacy = privcyId
                model!!.isActive = statId
                model!!.isHot = isHot.isChecked
                model!!.isFeatured = isFeature.isChecked
                model!!.isPromo = isPromo.isChecked
                model!!.isPopOut = isPop.isChecked
                model!!.subscriberID = _objApp!!.SubscriberId
                model!!.createdBy = _objApp!!.UserId
                model!!.imageURL = img.plus(encImage)
                model!!.description = txtDescription.text.toString()
                model!!.amenity = pairKeyValueList
                model!!.propertyID = updatePropertyId


                mapupdate["title"] = txtTitle.text.toString()
                mapupdate["PropertyCategoryID"] = propertyId
                mapupdate["CityID"] = cityId
                mapupdate["streetName"] = areaName
                mapupdate["Address"] = txtAddress.text.toString()
                mapupdate["LocationAlias"] = areaName
                mapupdate["Address_Component"] = addres
                mapupdate["SubLocalityLevel"] = subloc.toString()
                mapupdate["Latitude"] = model!!.latitude
                mapupdate["Longitude"] = model!!.longitude
                mapupdate["PurposeID"] = model!!.purposeID
                mapupdate["CurrencyID"] = model!!.currencyID
                mapupdate["PriceBudget"] = model!!.priceBudget
                mapupdate["LandArea"] = model!!.landArea
                mapupdate["UnitID"] = model!!.landAreaUnitID
                mapupdate["Privacy"] = model!!.privacy
                mapupdate["Active"] = model!!.isActive
                mapupdate["IsHot"] = model!!.isHot
                mapupdate["IsFeatured"] = model!!.isFeatured
                mapupdate["IsPromo"] = model!!.isPromo
                mapupdate["IsPopOut"] = model!!.isPopOut
                mapupdate["SubscriberID"] = model!!.subscriberID
                mapupdate["CreatedBy"] = model!!.createdBy
                mapupdate["FrontImage"] = img.plus(encImage)
                mapupdate["Description"] = model!!.description
                mapupdate["PropertyID"] = updatePropertyId
                mapupdate["Amenities"] = pairKeyValueList

                val jsonString = Parser.Post(update, mapupdate)
                val foos = Response(jsonString)

                val imgstrt = "data:image/jpeg;base64,"

                ImgUrl!!.forEach {
                    val myurl = Uri.parse(it)
                    val imageFile = java.io.File(myurl?.path)

                    var fis: FileInputStream? = null
                    try {
                        fis = FileInputStream(imageFile)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                    val bm: Bitmap = BitmapFactory.decodeStream(fis)
                    val baos = ByteArrayOutputStream()
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                    val b = baos.toByteArray()

                    val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
                    imgModel!!.additionalImages = imgstrt.plus(encodedImage)
                    mapimg["PropertyID"] = foos.get("PropertyID").toString()
                    mapimg["AdditionalImage"] = imgstrt.plus(encodedImage)

                    Parser.Post(addimgs, mapimg)

                }

                uiThread { }
            }

            setResult(REQ_CODE, intent)
            //  Log.e("mapupdate", mapupdate.toString())

            val intent = Intent(this, PropertiesActivity::class.java)
            startActivity(intent)
            finish()
        }
*/

        update_submit.setOnClickListener {

            val pairKeyValueList = myData(

                    "46" to bath_room.text?.toString(),
                    "47" to bed_room.text?.toString(),
                    "53" to launge.isChecked.toInt().toString(),
                    "54" to lundry.isChecked.toInt().toString(),
                    "64" to draw.isChecked.toInt().toString(),
                    "49" to ebackup.selectedItemPosition.toString(),
                    "65" to dinning.isChecked.toInt().toString(),
                    "51" to prayer.isChecked.toInt().toString(),
                    "37" to maid.isChecked.toInt().toString(),
                    "36" to stdy.isChecked.toInt().toString(),
                    "21" to msjid.isChecked.toInt().toString(),
                    "22" to garden.isChecked.toInt().toString(),
                    "40" to schol.isChecked.toInt().toString(),
                    "41" to col.isChecked.toInt().toString(),
                    "42" to uni.isChecked.toInt().toString(),
                    "24" to own.isChecked.toInt().toString(),
                    "26" to furnished.isChecked.toInt().toString(),
                    "35" to storg.isChecked.toInt().toString(),
                    "32" to view_main.text?.toString(),
                    "55" to lagard.isChecked.toInt().toString(),
                    "56" to swimm.isChecked.toInt().toString(),
                    "57" to gym.isChecked.toInt().toString(),
                    "58" to suana.isChecked.toInt().toString(),
                    "59" to jac.isChecked.toInt().toString(),
                    "60" to broad.isChecked.toInt().toString(),
                    "61" to cable.isChecked.toInt().toString(),
                    "62" to intercm.isChecked.toInt().toString(),
                    "38" to park.isChecked.toInt().toString(),
                    "48" to ac.isChecked.toInt().toString(),
                    "45" to aop.text?.toString(),
                    "50" to servant_quart.text?.toString(),
                    "52" to kitchecn.text?.toString(),
                    "63" to stor_room.text?.toString()).toString()

            if (encImage != "") {

                updatePost(updatePropertyId, txtTitle.text.toString(), areaName, txtAddress.text.toString(), areaName, subloc.toString(), parseDouble(lat.toString()), parseDouble(long.toString())
                        , purpId.toInt(), currncyId.toInt(), parseDouble(txtPrice.text.toString()), parseDouble(txtLandArea.text.toString()), unitAreaId.toInt(), propertyId.toInt(), privcyId.toInt(),
                        isHot.isChecked, isFeature.isChecked, isPromo.isChecked, isPop.isChecked, _objApp!!.SubscriberId, _objApp!!.UserId,
                        img.plus(this@ManagePropertyMasterActivity.encImage), txtDescription.text.toString(), pairKeyValueList.toString(), cityId.toInt())
                setResult(REQ_CODE, intent)

                val intent = Intent(this, PropertiesActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                updatePost(updatePropertyId, txtTitle.text.toString(), areaName, txtAddress.text.toString(), areaName, subloc.toString(), parseDouble(lat.toString()), parseDouble(long.toString())
                        , purpId.toInt(), currncyId.toInt(), parseDouble(txtPrice.text.toString()), parseDouble(txtLandArea.text.toString()), unitAreaId.toInt(), propertyId.toInt(), privcyId.toInt(),
                        isHot.isChecked, isFeature.isChecked, isPromo.isChecked, isPop.isChecked, _objApp!!.SubscriberId, _objApp!!.UserId,
                        encImage, txtDescription.text.toString(), pairKeyValueList.toString(), cityId.toInt())
                setResult(REQ_CODE, intent)

                val intent = Intent(this, PropertiesActivity::class.java)
                startActivity(intent)
                finish()

            }


        }

        val autocompleteFragment = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment?

        val filter = AutocompleteFilter.Builder()

                .setCountry("PK")
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE)
                .setTypeFilter(TYPE_ADMINISTRATIVE_AREA_LEVEL_1)
                .setTypeFilter(TYPE_ADMINISTRATIVE_AREA_LEVEL_2)
                .setTypeFilter(TYPE_ADMINISTRATIVE_AREA_LEVEL_3)
                .setTypeFilter(TYPE_SUBLOCALITY)
                .setTypeFilter(TYPE_SUBLOCALITY_LEVEL_1)
                .setTypeFilter(TYPE_SUBLOCALITY_LEVEL_2)
                .setTypeFilter(TYPE_SUBLOCALITY_LEVEL_3)
                .setTypeFilter(TYPE_SUBLOCALITY_LEVEL_4)
                .setTypeFilter(TYPE_SUBLOCALITY_LEVEL_5)
                .setTypeFilter(TYPE_STREET_ADDRESS)
                .build()
        subloc = TYPE_SUBLOCALITY
        autocompleteFragment?.setFilter(filter)

        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {

                areaName = p0.name.toString()
                addres = p0.address.toString()
                placeName.text = p0.address.toString()

                val queriedLocation: LatLng = p0.latLng
                lat = queriedLocation.latitude
                long = queriedLocation.longitude


            }

            override fun onError(status: Status) {
                placeName.text = status.toString()
            }
        })

        spCity?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cityId = cities[position].CityID

            }
        }

        spPropertyType?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                propertyId = proprties[position].PropertyCategoryID
                cityName = proprties[position].PropertyCategName
            }

        }

        spAreaUnit?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                unitAreaId = unitA[position].UnitID
            }

        }

        spCurrency?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currncyId = curency[position].CurrencyID
            }

        }

        spPurpose?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                purpId = purpose[position].PurposeID
            }

        }

        spPrivacy?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                privcyId = privacy[position].PrivacyID
            }

        }

        spStatus?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                statId = stat[position].StatusID
            }
        }
    }

    fun sendPost(title: String, streetName: String, Address: String, LocationAlias: String, SubLocalityLevel: String, Latitude: Double, Longitude: Double, PurposeID: Int, CurrencyID: Int, PriceBudget: Double, LandArea: Double, UnitID: Int, CategoryID: Int, Privacy: Int, IsHot: Boolean, IsFeatured: Boolean, IsPromo: Boolean, IsPopOut: Boolean, SubscriberID: Int, CreatedBy: Int, FrontImage: String, Description: String, Amenities: String, CityID: Int) {

        mAPIService!!.savePost(
                title, streetName, Address, LocationAlias, SubLocalityLevel, Latitude, Longitude, PurposeID,
                CurrencyID, PriceBudget, LandArea, UnitID, CategoryID, Privacy, IsHot, IsFeatured, IsPromo,
                IsPopOut, SubscriberID, CreatedBy, FrontImage, Description, Amenities, CityID

        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                if (response.isSuccessful) {

                    val jsonArray = JSONObject(response.body()?.string().toString())

                    ImgUrl!!.forEach {

                        val myurl = Uri.parse(it)
                        val imageFile = java.io.File(myurl?.path)

                        var fis: FileInputStream? = null
                        try {
                            fis = FileInputStream(imageFile)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        Log.e("to api img", myurl.toString())
                        val bm: Bitmap = BitmapFactory.decodeStream(fis)
                        val baos = ByteArrayOutputStream()
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        val b = baos.toByteArray()
                        val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
                        imgModel!!.additionalImages = "data:image/jpeg;base64,$encodedImage"
                        //  imgModel!!.propertyID = jsonArray.getString("PropertyID").toString()

                        mapimg["PropertyID"] = jsonArray.getString("PropertyID").toString()
                        mapimg["AdditionalImage"] = "data:image/jpeg;base64,$encodedImage"
//                        ImgUrlall!!.add("data:image/jpeg;base64,$encodedImage")

                        Log.e("ch", mapimg["AdditionalImage"].toString())
                        ImgPost(mapimg["PropertyID"], mapimg["AdditionalImage"])
                    }

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                Log.e("response", "Unable to submit post to API.")
            }


        })


    }

    fun updatePost(propertyid: Int, title: String, streetName: String, Address: String, LocationAlias: String, SubLocalityLevel: String, Latitude: Double, Longitude: Double, PurposeID: Int, CurrencyID: Int, PriceBudget: Double, LandArea: Double, UnitID: Int, CategoryID: Int, Privacy: Int, IsHot: Boolean, IsFeatured: Boolean, IsPromo: Boolean, IsPopOut: Boolean, SubscriberID: Int, CreatedBy: Int, FrontImage: String, Description: String, Amenities: String, CityID: Int) {

        mAPIService!!.updatePost(
                propertyid, title, streetName, Address, LocationAlias, SubLocalityLevel, Latitude, Longitude,
                PurposeID, CurrencyID, PriceBudget, LandArea, UnitID, CategoryID, Privacy, IsHot,
                IsFeatured, IsPromo, IsPopOut, SubscriberID, CreatedBy, FrontImage, Description,
                Amenities, CityID

        ).enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>?, response: retrofit2.Response<ResponseBody>?) {

                // val jsonArray = JSONObject(response!!.body()?.string().toString())
                Log.e("jsonArray", response?.body()?.string().toString())
                if (response!!.isSuccessful) {
                    val imgstrt = "data:image/jpeg;base64,"

                    ImgUrl!!.forEach {
                        val myurl = Uri.parse(it)
                        val imageFile = java.io.File(myurl?.path)

                        var fis: FileInputStream? = null
                        try {
                            fis = FileInputStream(imageFile)
                        } catch (e: FileNotFoundException) {
                            e.printStackTrace()
                        }
                        val bm: Bitmap = BitmapFactory.decodeStream(fis)
                        val baos = ByteArrayOutputStream()
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                        val b = baos.toByteArray()

                        val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
                        //  imgModel!!.additionalImages = "data:image/jpeg;base64,"+(encodedImage)
                        mapimg["PropertyID"] = updatePropertyId
                        mapimg["AdditionalImage"] = "data:image/jpeg;base64,$encodedImage"


                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("response", "Unable to submit post to API.")
            }
        })


    }

    fun ImgPost(propertyID: Any?, MultiImage: Any?) {

        mAPIService!!.imgPost(
                propertyID, MultiImage

        ).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }

        })


    }

    private fun stringtoint(data: String?): Int? {

        val d: Double? = valueOf(data)

        return d?.roundToInt()
    }

    private fun myData(vararg values: Pair<String?, String?>): JsonArray {
        val arr = JsonArray()
        values.forEach { pair ->
            arr.add(JsonObject().apply {
                addProperty("Amenity", pair.first)
                addProperty("AmenityValue", pair.second)
            })
        }
        return arr
    }

    private fun Boolean.toInt() = if (this) 1 else 0

    override fun onMultiImageSelected(uriLists: MutableList<Uri>, tag: String?) {

        uriList = uriLists

        imgs = convertListIntoArrayList(uriList)

        ImgUrl = ArrayList()
        imgs?.forEach {
            ImgUrl!!.add(it.toString())
            Log.e("each", it.toString())
        }

        //To save
        val settings = this.getSharedPreferences("images", 0)
        val editor = settings.edit()
        editor.putString("myimgs", ImgUrl.toString())
        editor.apply()

        //To retrieve
        val gettings: SharedPreferences = this.getSharedPreferences("images", 0)
        imgs_pref = gettings.getString("myimgs", "")
        rv_multiimgs.layoutManager = LinearLayoutManager(this@ManagePropertyMasterActivity, LinearLayoutManager.HORIZONTAL, false)
        val objPA = AddMultipleImagesadapter(ImgUrl!!)
        rv_multiimgs.adapter = objPA

        for (i in 0 until uriList!!.size) {

            val imageFile = java.io.File(uriList!![i].path)
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(imageFile)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            val bm: Bitmap = BitmapFactory.decodeStream(fis)
            val baos = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            multiencImage = Base64.encodeToString(b, Base64.DEFAULT)

        }


    }

    private fun convertListIntoArrayList(_uriData: MutableList<Uri>?): ArrayList<Uri> {
        val mImagesArrayList: ArrayList<Uri> = ArrayList()
        _uriData?.forEach {
            mImagesArrayList.add(it)
        }

        return mImagesArrayList
    }

    private fun getAsync() {
        when (count) {

            1 -> {
                _url = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETCityLookup"
                key = "CityName"
                getData = spCity?.let { GetData(_url!!, it) }
                getData!!.execute()
            }

            2 -> {
                //type
                _url = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETPropCategoriesLookup"
                key = "PropertyCategName"
                getData = spPropertyType?.let { GetData(_url!!, it) }
                getData!!.execute()
            }

            3 -> {
                _url = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETUnitLookup"
                key = "UnitName"
                getData = spAreaUnit?.let { GetData(_url!!, it) }
                getData!!.execute()
            }

            4 -> {
                _url = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETCurrencyLookup"
                key = "CurrencyName"
                getData = spCurrency?.let { GetData(_url!!, it) }
                getData!!.execute()
            }

            5 -> {
                _url = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETPurposeLookup"
                key = "PurposeName"
                getData = spPurpose?.let { GetData(_url!!, it) }
                getData!!.execute()
            }

            6 -> {
                _url = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETPropertyPrivacyLookUp"
                key = "Privacy"
                getData = spPrivacy?.let { GetData(_url!!, it) }
                getData!!.execute()
            }

            7 -> {

                _url = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETPropertyStatusLookUp"
                key = "Status"
                getData = spStatus?.let { GetData(_url!!, it) }

                getData!!.execute()
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class GetData(private val url: String, private val sp: Spinner) : AsyncTask<Void, Void, String>() {

        override fun onPostExecute(response: String) {
            super.onPostExecute(response)
            when (count) {
                1 -> {
                    cities.addAll(Gson().fromJson(response, Array<City>::class.javaObjectType))
                    populateSpinner(cities.map { it.CityName ?: "" }, sp
                            , cities.map { it.CityID }.indexOf(intent.getIntExtra("cityid", 1)))
                }

                2 -> {
                    proprties.addAll(Gson().fromJson(response, Array<PropertyCategory>::class.javaObjectType))
                    populateSpinner(proprties.map { it.PropertyCategName }, sp
                            , proprties.map { it.PropertyCategoryID }.indexOf(intent.getIntExtra("propertytypeid", 1)))
                }

                3 -> {
                    unitA.addAll(Gson().fromJson(response, Array<Unit>::class.javaObjectType))
                    populateSpinner(unitA.map { it.UnitName ?: "" }, sp
                            , unitA.map { it.UnitID }.indexOf(intent.getIntExtra("areaunitid", 1)))
                }

                4 -> {
                    curency.addAll(Gson().fromJson(response, Array<Currency>::class.javaObjectType))
                    populateSpinner(curency.map { it.CurrencyName ?: "" }, sp
                            , curency.map { it.CurrencyID }.indexOf(intent.getIntExtra("curncyid", 1)))
                }

                5 -> {
                    purpose.addAll(Gson().fromJson(response, Array<Purpose>::class.javaObjectType))
                    populateSpinner(purpose.map { it.PurposeName ?: "" }, sp
                            , purpose.map { it.PurposeID }.indexOf(intent.getIntExtra("purposid", 1)))
                }
                6 -> {

                    privacy.addAll(Gson().fromJson(response, Array<PrivacyM>::class.javaObjectType))
                    populateSpinner(privacy.map { it.Privacy ?: "" }, sp
                            , if (updatePropertyId != 0) {
                        privacy.map { it.PrivacyID }.indexOf(intent.getIntExtra("privacyid", 1))
                    } else 1)

                }
                7 -> {
                    stat.addAll(Gson().fromJson(response, Array<StatusM>::class.javaObjectType))
                    populateSpinner(stat.map { it.Status ?: "" }, sp
                            , if (updatePropertyId != 0) {
                        stat.map { it.StatusID }.indexOf(intent.getIntExtra("Activeid", 2))
                    } else 2)
                }

                else -> fillDropDownData(response, key, sp)
            }
        }

        override fun doInBackground(vararg voids: Void): String? {
            return ha.ecz.com.subscriberpanel.Utils.Parser.Get(url)
        }
    }

    fun fillDropDownData(response: String, key: String?, sp: Spinner?) {

        val list = ArrayList<String>()

        try {
            val jsonArray = JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val `object` = jsonArray.getJSONObject(i)
                list.add(`object`.getString(key))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        populateSpinner(list, sp!!)
    }

    private fun populateSpinner(content: List<String>, sp: Spinner, defaultValue: Int = 0) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, content)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp.adapter = adapter
        if (defaultValue >= 0) {
            sp.setSelection(defaultValue)
        }
        count += 1
        getAsync()
    }

    private fun doubtobool(value: Double?): Boolean {
        if (value == 1.0) {
            return true
        } else if (isEmpty(value.toString())) {
            return false
        }
        return false
    }

    override fun onBackPressed() {
        //Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
        //show dialog
        val mAlertDialog = mBuilder.show()
        //login button click of custom layout
        mDialogView.ys.setOnClickListener {
            //dismiss dialog
            val intent = Intent(this@ManagePropertyMasterActivity, PropertiesActivity::class.java)
            startActivity(intent)
            finish()
            mAlertDialog.dismiss()
            //   FancyToast.makeText(this@ManagePropertyMasterActivity, "Your Property has been successfully saved!", FancyToast.LENGTH_SHORT, FancyToast.INFO, R.mipmap.ic_launcher, true).show()

        }
        //cancel button click of custom layout
        mDialogView.No.setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
    }

}

