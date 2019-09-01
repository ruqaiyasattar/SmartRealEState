@file:Suppress("DEPRECATION", "PLUGIN_WARNING", "ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")

package ha.ecz.com.subscriberpanel

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.asksira.bsimagepicker.BSImagePicker
import com.bumptech.glide.Glide.*
import com.google.gson.Gson
import ha.ecz.com.subscriberpanel.Models.*
import ha.ecz.com.subscriberpanel.Models.Unit

import ha.ecz.com.subscriberpanel.R.id

import ha.ecz.com.subscriberpanel.Utils.Parser

import org.json.JSONArray
import org.json.JSONException
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.bumptech.glide.Glide
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter
import kotlinx.android.synthetic.main.activity_manage_property_master.*
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException

import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import ha.ecz.com.subscriberpanel.Models.Currency
import java.util.*
import java.io.File as File1


class ManagePropertyMasterActivity : AppCompatActivity(), BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener {

    private lateinit var pd: ProgressDialog
    private var obj: ManagerPropertyTask? = null
    private var model: ManageProperty? = null
    private var getData: GetData? = null
    private var spinner: Spinner? = null
    private var key: String? = null
    private var count = 1
    private var spPropertyType: Spinner? = null
    private var spCity: Spinner? = null
    private var spPurpose: Spinner? = null
    private var spCurrency: Spinner? = null
    private var spAreaUnit: Spinner? = null
    private var spStatus: Spinner? = null
    private var spPrivacy: Spinner? = null
    private var URL: String? = null
    lateinit var cities: ArrayList<City>
    lateinit var proprties: ArrayList<PropertyCategory>
    lateinit var curency: ArrayList<Currency>
    lateinit var purpose: ArrayList<Purpose>
    lateinit var unitA: ArrayList<Unit>
    lateinit var stat: ArrayList<StatusM>
    lateinit var privacy: ArrayList<PrivacyM>

    companion object {
        var encImage: String = " "
        var multiencImage = " "
    }

    override fun onSingleImageSelected(uri: Uri?, tag: String?) {
        Glide.with(this@ManagePropertyMasterActivity).load(uri).into(tv_single_selection)

        val imageFile = java.io.File(uri?.path)
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
        encImage = Base64.encodeToString(b, Base64.DEFAULT)
    }

    override fun onMultiImageSelected(uriList: MutableList<Uri>?, tag: String?) {

        for (i in 0 until uriList!!.size) {
            if (i >= 6) return
            val iv: ImageView = when (i) {
                0 -> iv_image1
                1 -> iv_image2
                2 -> iv_image3
                3 -> iv_image4
                4 -> iv_image5
                5 -> iv_image6
                else -> iv_image6
            }
            with(this).load(uriList[i]).into(iv)

            val imageFile = java.io.File(uriList[i].path)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_manage_property_master)

        val txtTitle = findViewById<View>(id.txtPropertyTitle) as TextView
        val txtLandArea = findViewById<View>(id.txtLandArea) as TextView
        val txtAddress = findViewById<TextView>(id.txtPropAddress)
        val txtDescription = findViewById<TextView>(id.txtDescription)

        cities = ArrayList()
        proprties = ArrayList()
        unitA = ArrayList()
        curency = ArrayList()
        purpose = ArrayList()
        stat = ArrayList()
        privacy = ArrayList()

        spStatus = findViewById(id.cmbActive)
        spPropertyType = findViewById(id.cmbPropertyType)
        spCurrency = findViewById(id.cmbCurrency)
        spCity = findViewById(id.cmbCity)
        spAreaUnit = findViewById(id.cmbAreaUnit)
        spPurpose = findViewById(id.cmbPurpose)
        spPrivacy = findViewById(id.cmbPrivacy)

        var cityId = 0
        var propertyId = 0
        var unitAreaId = 0
        var currncyId = 0
        var purpId = 0
        var privcyId = 0
        var statId = 0

        getAsync()

        submit_data.setOnClickListener {
            val img = "data:image/jpeg;base64,"
            model = ManageProperty()
            model!!.title = txtTitle.text.toString()
            model!!.propertyCategoryID = propertyId
            model!!.cityID = cityId
            model!!.streetRoadName = "nazimabad"
            model!!.address = txtAddress.text.toString()
            model!!.locationAlias = "LocationAlias"
            model!!.address_Component = "Address"
            model!!.subLocalityLevel = "SubLocalityLevel"
            model!!.latitude = 24.8604
            model!!.longitude = 67.8604
            model!!.purposeID = purpId
            model!!.currencyID = currncyId
            model!!.priceBudget = java.lang.Double.parseDouble(txtPrice.text.toString())
            model!!.landArea = java.lang.Double.parseDouble(txtLandArea.text.toString())
            model!!.landAreaUnitID = unitAreaId
            model!!.privacy = privcyId
            model!!.isActive = statId
            model!!.isHot = is_Hot.isChecked
            model!!.isFeatured = is_Feature.isChecked
            model!!.isPromo = is_Promo.isChecked
            model!!.isPopOut = is_Pop.isChecked
            model!!.subscriberID = intent.getStringExtra("subId").toInt()
            model!!.createdBy = intent.getStringExtra("userId").toInt()
            model!!.imageURL = img.plus(encImage)
           // model!!.additionalImages = img.plus(multiencImage)
            model!!.description = txtDescription.text.toString()
            obj = ManagerPropertyTask()
            obj!!.execute()

            val intent = Intent(this@ManagePropertyMasterActivity, PropertiesAdapter::class.java)
            intent.putExtra("base64String", encImage)
            startActivity(intent)
        }

        tv_single_selection.setOnClickListener {

            val pickerDialog = BSImagePicker.Builder("com.asksira.imagepickersheetdemo.fileprovider")
                    .build()
            pickerDialog.show(supportFragmentManager, "picker")

        }

        tv_multi_selection.setOnClickListener {

            //  Toast.makeText(this, imageString.toString(), Toast.LENGTH_LONG).show()
            val pickerDialog = BSImagePicker.Builder("com.asksira.imagepickersheetdemo.fileprovider")
                    .setMaximumDisplayingImages(Integer.MAX_VALUE)
                    .isMultiSelect
                    .setMinimumMultiSelectCount(3)
                    .setMaximumMultiSelectCount(30)
                    .build()
            pickerDialog.show(supportFragmentManager, "picker")
        }

        val autocompleteFragment = fragmentManager.findFragmentById(id.place_autocomplete_fragment) as PlaceAutocompleteFragment?

        val filter = AutocompleteFilter.Builder()
                .setCountry("PK")

                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build()

        autocompleteFragment?.setFilter(filter)

        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {

                placeName.text = p0.latLng.toString()
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
                statId = stat[position].statusID
            }

        }

    }


    private fun getAsync() {
        when (count) {

            1 -> {
                URL = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETCityLookup"
                key = "CityName"
                getData = spCity?.let { GetData(URL!!, it) }
                getData!!.execute()
            }
            2 -> {
                URL = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETPropCategoriesLookup"
                key = "PropertyCategName"
                getData = spPropertyType?.let { GetData(URL!!, it) }
                getData!!.execute()
            }
            3 -> {
                URL = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETUnitLookup"
                key = "UnitName"
                getData = spAreaUnit?.let { GetData(URL!!, it) }
                getData!!.execute()
            }
            4 -> {
                URL = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETCurrencyLookup"
                key = "CurrencyName"
                getData = spCurrency?.let { GetData(URL!!, it) }
                getData!!.execute()
            }
            5 -> {
                URL = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETPurposeLookup"
                key = "PurposeName"
                getData = spPurpose?.let { GetData(URL!!, it) }
                getData!!.execute()
            }
            6 -> {

                URL = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETPropertyPrivacyLookUp"
                key = "Privacy"
                getData = spPrivacy?.let { GetData(URL!!, it) }
                getData!!.execute()
            }
            7 -> {

                URL = "http://rehajomobileapi.hundredalpha.com/api/Properties/GETPropertyStatusLookUp"
                key = "Status"
                getData = spStatus?.let { GetData(URL!!, it) }
                getData!!.execute()
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    inner class ManagerPropertyTask : AsyncTask<Void, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            pd = ProgressDialog(this@ManagePropertyMasterActivity)
            pd.isIndeterminate = false
            pd.setTitle("Please wait")
            pd.setMessage("Loading...")
            pd.show()
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            pd.hide()
            Toast.makeText(this@ManagePropertyMasterActivity, s, Toast.LENGTH_LONG).show()
        }

        override fun doInBackground(vararg voids: Void): String {

            val map = HashMap<String, Any>()
            map["Title"] = model!!.title
            map["PropertyCategoryID"] = model!!.propertyCategoryID
            map["CityID"] = model!!.cityID
            map["streetName"] = model!!.streetRoadName
            map["Address"] = model!!.address
            map["LocationAlias"] = model!!.locationAlias
            map["Address_Component"] = model!!.address_Component
            map["SubLocalityLevel"] = model!!.subLocalityLevel
            map["Latitude"] = model!!.latitude
            map["Longitude"] = model!!.longitude
            map["PurposeID"] = model!!.purposeID
            map["CurrencyID"] = model!!.currencyID
            map["PriceBudget"] = model!!.priceBudget
            map["LandArea"] = model!!.landArea
            map["UnitID"] = model!!.landAreaUnitID
            map["Privacy"] = model!!.privacy
            map["Active"] = model!!.isActive
            map["IsHot"] = model!!.isHot
            map["IsFeatured"] = model!!.isFeatured
            map["IsPromo"] = model!!.isPromo
            map["IsPopOut"] = model!!.isPromo
            map["SubscriberID"] = model!!.subscriberID
            map["CreatedBy"] = model!!.createdBy
            map["FrontImage"] = model!!.imageURL
            map["Description"] = model!!.description

            val url = "http://rehajomobileapi.hundredalpha.com/api/ManageProperty/POSTaddpropertyintodatabase"
            return Parser.Post(url, map)

        }

    }

    @SuppressLint("StaticFieldLeak")

    inner class GetData(private val url: String, private val sp: Spinner) : AsyncTask<Void, Void, String>() {

        override fun onPostExecute(response: String) {
            super.onPostExecute(response)
            when (count) {
                1 -> {
                    cities.addAll(Gson().fromJson(response, Array<City>::class.javaObjectType))
                    populateSpinner(cities.map { it.CityName ?: "" }, sp)
                }

                2 -> {
                    proprties.addAll(Gson().fromJson(response, Array<PropertyCategory>::class.javaObjectType))
                    populateSpinner(proprties.map { it.PropertyCategName }, sp)
                }
                3 -> {
                    unitA.addAll(Gson().fromJson(response, Array<Unit>::class.javaObjectType))
                    populateSpinner(unitA.map { it.UnitName ?: "" }, sp)
                }

                4 -> {
                    curency.addAll(Gson().fromJson(response, Array<Currency>::class.javaObjectType))
                    populateSpinner(curency.map { it.CurrencyName ?: "" }, sp)
                }

                5 -> {
                    purpose.addAll(Gson().fromJson(response, Array<Purpose>::class.javaObjectType))
                    populateSpinner(purpose.map { it.PurposeName ?: "" }, sp)
                }
                6 -> {
                    privacy.addAll(Gson().fromJson(response, Array<PrivacyM>::class.javaObjectType))
                    populateSpinner(privacy.map { it.Privacy ?: "" }, sp)
                }
                7 -> {
                    stat.addAll(Gson().fromJson(response, Array<StatusM>::class.javaObjectType))
                    populateSpinner(stat.map { it.Status ?: "" }, sp)
                }

                else -> fillDropDownData(response, key, sp)
            }
        }

        override fun doInBackground(vararg voids: Void): String? {
            return Parser.Get(url)
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

    private fun populateSpinner(content: List<String>, sp: Spinner) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, content)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp.adapter = adapter
        count += 1
        getAsync()
    }

}



