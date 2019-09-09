@file:Suppress("DEPRECATION")

package ha.ecz.com.subscriberpanel.Adapter


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ha.ecz.com.agentportal.R
import ha.ecz.com.subscriberpanel.ManagePropertyMasterActivity
import ha.ecz.com.subscriberpanel.Models.Property
import ha.ecz.com.subscriberpanel.Utils.Parser
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import android.content.SharedPreferences
import com.shashank.sony.fancytoastlib.FancyToast
import kotlin.collections.ArrayList
import android.app.AlertDialog
import android.net.Uri
import android.text.TextUtils.isEmpty
import android.util.Log
import android.widget.*
import ha.ecz.com.subscriberpanel.Models.Response
import kotlinx.android.synthetic.main.property_listitem.view.*
import com.google.gson.Gson
import ha.ecz.com.subscriberpanel.model.ACPValues
import ha.ecz.com.subscriberpanel.model.Amenity
import java.text.DecimalFormat
import kotlin.math.pow
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import ha.ecz.com.subscriberpanel.Models.PropertyCategory
import ha.ecz.com.subscriberpanel.ViewPropertyActivity


class PropertiesAdapter(val _propsData: ArrayList<Property>, val onDel: (Int) -> Unit) : RecyclerView.Adapter<PropertiesAdapter.PropertiesViewHolder>() {

    companion object {

        lateinit var singleimg: Uri

        var mval:Int? =0
        var PROPERTY_ID: Int = 0
        var landAre: String = ""
        var Check_ID: Int = 0
        var storage: String? = ""
        var Owner: String? = ""
        var drawroom: String? = ""
        var Maid: String? = ""
        var parking: String? = ""
        var AC: String? = ""
        var furn: String? = ""
        var mUni: String? = ""
        var mCol: String? = ""
        var bedvalue: String? = ""
        var bathvalue: String? = ""
        var mpark: String? = ""
        var mmsjid: String? = ""
        var mScho: String? = ""
        var mtv: String? = ""
        var mboard: String? = ""
        var minter: String? = ""
        var mLawn: String? = ""
        var pool: String? = ""
        var suana: String? = ""
        var jucizi: String? = ""
        var gymm: String? = ""
        var mview: String? = ""
        var mbath: String? = ""
        var mstudy: String? = ""
        var mlaundry: String? = ""
        var mserv: String? = ""
        var melectric: Int? = 0
        var prayrom: String? = ""
        var mkit: String? = ""
        var maop: String? = ""
        var dinroom: String? = ""
        var mloung: String? = ""
        var mbed: String? = ""
        var mstrom: String? = ""
        var vID: Int = 0

    }

    private var CAT_ID: Int = 0

    private lateinit var context1: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_listitem, parent, false)
        return PropertiesViewHolder(view)
    }

    private val c = arrayOf("K", "Lac", "Crore")

    private fun coolFormat(n: Int): String {
        val size = n.toString().length
        if (size in 4..5) {
            val value = 10.0.pow(1).toInt()
            val d = (n / 1000 * value) / value
            return ((n / 1000 * value) / value).toString() + " " + c[0]
        } else if (size in 6..7) {
            val value = 10.0.pow(1).toInt()
            return ((n / 100000 * value) / value).toString() + " " + c[1]
        } else if (size >= 8) {
            val value = 10.0.pow(1).toInt()
            return ((n / 10000000 * value) / value).toString() + " " + c[2]
        } else {
            return n.toString() + ""
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {

        val objProp = _propsData[position]

        //streetname
        holder._txtLocation.text = objProp.streetName

        val amount = java.lang.Double.parseDouble(objProp.PriceBudget.toString())
        //val formatter = DecimalFormat("#,###")

        //pricebudget
        holder._txtPrice.text = coolFormat(amount.toInt())

        holder._typProp.text = objProp.PropertyCategory

        val landar = java.lang.Double.parseDouble(objProp.LandArea.toString())
        val formater = DecimalFormat("#,###")
        val formated = formater.format(landar)
        val dqft = "Sq ft"
        //landarea
        landAre = "$formated $dqft"
        holder._txtArea.text = landAre

        context1 = holder.itemView.context
        CAT_ID = objProp.PropertyCategoryID

        val edit = 0

        val intent = Intent(holder.itemView.context, ManagePropertyMasterActivity::class.java)
        vID = objProp.PropertyID

        val amen = "http://rehajomobileapi.hundredalpha.com/api/Amenities/GETAmenityDynamicControls/?PropertyID=$vID&CategID=$CAT_ID"

        //imgFront
        Glide.with(holder.itemView.context)
                .load(objProp.FrontImage)
                .centerCrop().fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(holder._imgProp)

        doAsync {

            val o = Parser.Get(amen)

            uiThread {
                val gson = Gson()
                val ameinityList: MutableList<Amenity>? = arrayListOf()
                val acpValues: MutableList<ACPValues>? = arrayListOf()

                for (amenity in 0..32) {
                    val amenityJSON = Response(o).getJSONArray("DynamicAmenities").getString(amenity)
                    val amenityPojo = gson.fromJson(amenityJSON.toString(), Amenity::class.java)
                    ameinityList?.add(amenityPojo)
                    // Log.e("ameinityList", ameinityList!![amenity].amenityName.toString()+"   :values -> "+ameinityList[amenity].amenityNumericValue.toString())
                }

                for (values_ac in 0..2) {
                    val amenityJSON = Response(o).getJSONArray("ACPValues").getString(values_ac)
                    val amenityPojo = gson.fromJson(amenityJSON.toString(), ACPValues::class.java)
                    acpValues?.add(amenityPojo)

                    melectric = when {
                        (acpValues?.get(values_ac)?.ACPID_FK) as Int > 0 -> {
                            intent.putExtra("Electricity Backup", acpValues[values_ac].ACPID_FK.toString())
                            acpValues[values_ac].ACPID_FK

                        }
                        (acpValues[values_ac].ACPID_FK) as Int == 0 -> 0
                        else -> {
                            intent.putExtra("Electricity Backup", acpValues[values_ac].ACPID_FK.toString())
                            acpValues[values_ac].ACPID_FK
                        }
                    }

                }

                bedvalue = if (isEmpty(ameinityList!![25].amenityNumericValue?.toString())) 0.toString() else ameinityList[25].amenityNumericValue?.toString()
                bathvalue = if (isEmpty(ameinityList[26].amenityNumericValue?.toString())) 0.toString() else ameinityList[26].amenityNumericValue?.toString()

                val landar3 = java.lang.Double.parseDouble(bedvalue.toString())
                val formater3 = DecimalFormat("#")
                val formated3 = formater3.format(landar3)

                holder._txtbed.text = formated3

                val landar4 = java.lang.Double.parseDouble(bathvalue.toString())
                val formater4 = DecimalFormat("#")
                val formated4 = formater4.format(landar4)

                holder._txtbath.text = formated4
            }

        }

        //editing item
        holder._editPro.setOnClickListener {
            //amenities
            singleimg = objProp.FrontImage!!.run { return@run toUri() }

            doAsync {

                val o = Parser.Get(amen)

                uiThread {
                    val gson = Gson()
                    val ameinityList: MutableList<Amenity>? = arrayListOf()
                    val acpValues: MutableList<ACPValues>? = arrayListOf()

                    for (amenity in 0..32) {
                        val amenityJSON = Response(o).getJSONArray("DynamicAmenities").getString(amenity)
                        val amenityPojo = gson.fromJson(amenityJSON.toString(), Amenity::class.java)
                        ameinityList?.add(amenityPojo)
                    }

                    for (values_ac in 0..2) {
                        val amenityJSON = Response(o).getJSONArray("ACPValues").getString(values_ac)
                        val amenityPojo = gson.fromJson(amenityJSON.toString(), ACPValues::class.java)
                        acpValues?.add(amenityPojo)

                        if ((acpValues?.get(values_ac)?.ACPID_FK) as Int > 0) {
                            intent.putExtra("Electricity Backup", acpValues[values_ac].ACPID_FK.toString())
                           // melectric = (acpValues[values_ac].AmenityID_FK)
                            mval= acpValues[values_ac].ACPID_FK
                        }else if((acpValues[values_ac].ACPID_FK) as Int == 0){
                            mval = 0
                        }

                    }

                    storage = if (isEmpty(ameinityList!![0].amenityNumericValue?.toString())) 0.toString() else ameinityList[0].amenityNumericValue?.toString()
                    Owner = if (isEmpty(ameinityList[1].amenityNumericValue?.toString())) 0.toString() else ameinityList[1].amenityNumericValue?.toString()
                    Maid = if (isEmpty(ameinityList[2].amenityNumericValue?.toString())) 0.toString() else ameinityList[2].amenityNumericValue?.toString()
                    mUni = if (isEmpty(ameinityList[3].amenityNumericValue?.toString())) 0.toString() else ameinityList[3].amenityNumericValue?.toString()
                    mCol = if (isEmpty(ameinityList[4].amenityNumericValue?.toString())) 0.toString() else ameinityList[4].amenityNumericValue?.toString()
                    mScho = if (isEmpty(ameinityList[5].amenityNumericValue?.toString())) 0.toString() else ameinityList[5].amenityNumericValue?.toString()
                    mstudy = if (isEmpty(ameinityList[6].amenityNumericValue?.toString())) 0.toString() else ameinityList[6].amenityNumericValue.toString()
/*
                    //To save SUb_ID
                    val settingsmelectric = holder.itemView.context.getSharedPreferences("SUBmelectric", 0)
                    val editormelectric = settingsmelectric.edit()
                    editormelectric.putInt("SNOWmelectric", melectric!!)
                    editormelectric.apply()

                    //To retrieve Sub_ID
                    val gettingsmelectric: SharedPreferences = holder.itemView.context.getSharedPreferences("SUBmelectric", 0)
                    // mval= gettingsmelectric.getInt("SNOWmelectric", 0) //0 is the default value

                     Log.e("v", mval.toString())
*/
                    mserv = if (isEmpty(ameinityList[8].amenityNumericValue?.toString())) 0.toString() else ameinityList[8].amenityNumericValue?.toString()
                    prayrom = if (isEmpty(ameinityList[9].amenityNumericValue?.toString())) 0.toString() else ameinityList[9].amenityNumericValue?.toString()
                    mkit = if (isEmpty(ameinityList[10].amenityNumericValue?.toString())) 0.toString() else ameinityList[10].amenityNumericValue?.toString()
                    mlaundry = if (isEmpty(ameinityList[11].amenityNumericValue?.toString())) 0.toString() else ameinityList[11].amenityNumericValue?.toString()
                    mLawn = if (isEmpty(ameinityList[12].amenityNumericValue?.toString())) 0.toString() else ameinityList[12].amenityNumericValue?.toString()
                    pool = if (isEmpty(ameinityList[13].amenityNumericValue?.toString())) 0.toString() else ameinityList[13].amenityNumericValue?.toString()
                    maop = if (isEmpty(ameinityList[14].amenityNumericValue?.toString())) 0.toString() else ameinityList[14].amenityNumericValue?.toString()
                    dinroom = if (isEmpty(ameinityList[15].amenityNumericValue?.toString())) 0.toString() else ameinityList[15].amenityNumericValue?.toString()
                    drawroom = if (isEmpty(ameinityList[16].amenityNumericValue?.toString())) 0.toString() else ameinityList[16].amenityNumericValue?.toString()
                    mstrom = if (isEmpty(ameinityList[17].amenityNumericValue?.toString())) 0.toString() else ameinityList[17].amenityNumericValue?.toString()
                    minter = if (isEmpty(ameinityList[18].amenityNumericValue?.toString())) 0.toString() else ameinityList[18].amenityNumericValue?.toString()
                    mtv = if (isEmpty(ameinityList[19].amenityNumericValue?.toString())) 0.toString() else ameinityList[19].amenityNumericValue?.toString()
                    mboard = if (isEmpty(ameinityList[20].amenityNumericValue?.toString())) 0.toString() else ameinityList[20].amenityNumericValue?.toString()
                    jucizi = if (isEmpty(ameinityList[21].amenityNumericValue?.toString())) 0.toString() else ameinityList[21].amenityNumericValue?.toString()
                    suana = if (isEmpty(ameinityList[22].amenityNumericValue?.toString())) 0.toString() else ameinityList[22].amenityNumericValue?.toString()
                    mloung = if (isEmpty(ameinityList[23].amenityNumericValue?.toString())) 0.toString() else ameinityList[23].amenityNumericValue?.toString()
                    gymm = if (isEmpty(ameinityList[24].amenityNumericValue?.toString())) 0.toString() else ameinityList[24].amenityNumericValue?.toString()
                    mbed = if (isEmpty(ameinityList[25].amenityNumericValue?.toString())) 0.toString() else ameinityList[25].amenityNumericValue?.toString()
                    mbath = if (isEmpty(ameinityList[26].amenityNumericValue?.toString())) 0.toString() else ameinityList[26].amenityNumericValue?.toString()
                    parking = if (isEmpty(ameinityList[27].amenityNumericValue?.toString())) 0.toString() else ameinityList[27].amenityNumericValue?.toString()
                    mpark = if (isEmpty(ameinityList[28].amenityNumericValue?.toString())) 0.toString() else ameinityList[28].amenityNumericValue?.toString()
                    mmsjid = if (isEmpty(ameinityList[29].amenityNumericValue?.toString())) 0.toString() else ameinityList[29].amenityNumericValue?.toString()
                    mview = if (isEmpty(ameinityList[30].amenityTextValue?.toString())) 0.toString() else ameinityList[30].amenityTextValue?.toString()
                    AC = if (isEmpty(ameinityList[31].amenityNumericValue?.toString())) 0.toString() else ameinityList[31].amenityNumericValue?.toString()
                    furn = if (isEmpty(ameinityList[32].amenityNumericValue?.toString())) 0.toString() else ameinityList[32].amenityNumericValue?.toString()

                    val landar3 = java.lang.Double.parseDouble(mbed.toString())
                    val formater3 = DecimalFormat("#")
                    val formated3 = formater3.format(landar3)

                    holder._txtbed.text = formated3

                    val landar4 = java.lang.Double.parseDouble(mbath.toString())
                    val formater4 = DecimalFormat("#")
                    val formated4 = formater4.format(landar4)

                    holder._txtbath.text = formated4
                }

            }
            //


            intent.putExtra("_edit", edit)
            intent.putExtra("title", objProp.Title)
            intent.putExtra("street", objProp.streetName)
            intent.putExtra("price", objProp.PriceBudget)
            intent.putExtra("landarea", objProp.LandArea)
            intent.putExtra("address", objProp.Address)
            intent.putExtra("description", objProp.Description)
            intent.putExtra("isfeature", objProp.IsFeatured)
            intent.putExtra("ishot", objProp.IsHot)
            intent.putExtra("ispromo", objProp.IsPromo)
            intent.putExtra("ispopout", objProp.IsPopOut)
            intent.putExtra("cityid", objProp.CityID)
            intent.putExtra("propertytypeid", objProp.PropertyCategoryID)
            intent.putExtra("purposid", objProp.PurposeID)
            intent.putExtra("curncyid", objProp.CurrencyID)
            intent.putExtra("areaunitid", objProp.LandAreaUnitID)
            intent.putExtra("privacyid", objProp.Privacy)
            intent.putExtra("Activeid", objProp.Active)
            intent.putExtra("proid", objProp.PropertyID)
            intent.putExtra("subbid", objProp.SubscriberID)
            intent.putExtra("creatid", objProp.CreatedBy)

            holder.itemView.context.startActivity(intent)
        }

        //click item
        holder.itemView.setOnClickListener {
            vID = objProp.PropertyID

            CAT_ID = objProp.PropertyCategoryID
            val intent = Intent(holder.itemView.context, ViewPropertyActivity::class.java)
            intent.putExtra("vPrice", "PKR " + coolFormat(amount.toInt()))
            intent.putExtra("vAdres", objProp.streetName)
            intent.putExtra("proprTpye", objProp.PropertyCategory)
            intent.putExtra("vProid", vID.toString())
            intent.putExtra("CAT_ID", objProp.PropertyCategoryID)
            intent.putExtra("vDes", objProp.Description)
            intent.putExtra("vPur", objProp.PurposeID)

            holder.itemView.context.startActivity(intent)

        }

        //delet
        holder._delPro.setOnClickListener {

            val builder = AlertDialog.Builder(context1)
            builder.setTitle("Delete Property")
            builder.setMessage("Are you sure?")

            builder.setPositiveButton("YES") { dialog, which ->
                // Do nothing but close the dialog
                FancyToast.makeText(context1, "Property has been deleted!", FancyToast.LENGTH_SHORT, FancyToast.INFO, R.mipmap.ic_launcher, true).show()

                doAsync {

                    val a = "http://rehajomobileapi.hundredalpha.com/api/ManageProperty/GETdeletepropertyfromdatabase?PropertyID=${objProp.PropertyID}"

                    Parser.Get(a)

                    uiThread {
                        removeObject(position)
                        notifyDataSetChanged()
                        dialog.dismiss()
                    }
                }

            }

            builder.setNegativeButton("NO") { dialog, which ->
                // Do nothing
                dialog.dismiss()
            }

            val alert = builder.create()
            alert.show()

        }

        Check_ID = objProp.PropertyID

        //To save idProperty
        val settings = context1.getSharedPreferences("YOUR_PREF_NAME", 0)
        val editor = settings.edit()
        editor.putInt("SNOW_DENSITY", objProp.PropertyID)
        editor.apply()

        //To retrieve idProperty
        val gettings: SharedPreferences = context1.getSharedPreferences("YOUR_PREF_NAME", 0)
        PROPERTY_ID = gettings.getInt("SNOW_DENSITY", 0) //0 is the default value

    }

    private fun removeObject(position: Int) {
        onDel(position)

        this.notifyItemRemoved(position)

    }

    override fun getItemCount(): Int {
        return _propsData.size
    }

    inner class PropertiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val _imgProp: ImageView = itemView.findViewById<View>(R.id.propertyImg) as ImageView
        val _txtLocation: TextView = itemView.findViewById<View>(R.id.locationText) as TextView
        val _txtPrice: TextView = itemView.findViewById<View>(R.id.pricTxt) as TextView
        val _typProp: TextView = itemView.findViewById<View>(R.id.type) as TextView
        val _txtArea: TextView = itemView.findViewById<View>(R.id.areaTxt) as TextView
        val _delPro: ImageButton = itemView.findViewById<View>(R.id.DelPro) as ImageButton
        val _editPro: ImageButton = itemView.findViewById<View>(R.id.EdiPro) as ImageButton
        val _txtbed: TextView = itemView.findViewById<View>(R.id.bedtag) as TextView
        val _txtbath: TextView = itemView.findViewById<View>(R.id.txtbath) as TextView


    }

}

