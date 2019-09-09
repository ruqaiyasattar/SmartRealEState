package ha.ecz.com.subscriberpanel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import ha.ecz.com.agentportal.R
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter
import ha.ecz.com.subscriberpanel.Adapter.PropertiesAdapter.Companion.landAre
import ha.ecz.com.subscriberpanel.Extras.emptyOrDouble
import ha.ecz.com.subscriberpanel.Models.Response
import ha.ecz.com.subscriberpanel.Utils.Parser
import ha.ecz.com.subscriberpanel.model.ACPValues
import ha.ecz.com.subscriberpanel.model.Amenity
import kotlinx.android.synthetic.main.activity_view_property.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.text.DecimalFormat


class ViewPropertyActivity : AppCompatActivity() {

    val vimageList = arrayListOf<String>()
    var vimgs: String = ""
    var h: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_property)

        setSupportActionBar(tb)
        myImageViewText.text = landAre

        val vProid: String? = intent.getStringExtra("vProid")

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        folding_cell.setOnClickListener {
            folding_cell.toggle(true)

        }

        val imagesmulti = "http://rehajomobileapi.hundredalpha.com/api/Properties/GetAdditionalImages/?PropertyID=$vProid"

        doAsync {

            val w = Parser.Get(imagesmulti)

            val xy = Response(w).getJSONArray("GetAdditionalImage")
            vimgs = xy.toString()
            val jsonImgs = JSONArray(vimgs)

            uiThread {
                for (imgIndex in 0 until jsonImgs.length()) {
                    vimageList.add(jsonImgs[imgIndex].toString())

                    val viewFlipper = findViewById<ViewFlipper>(R.id.viewFlipper)
                    if (viewFlipper != null) {
                        viewFlipper.setInAnimation(applicationContext, android.R.anim.slide_in_left)
                        viewFlipper.setOutAnimation(applicationContext, android.R.anim.slide_out_right)
                        viewFlipper.setFadingEdgeLength(23)
                    }

                    if (viewFlipper != null) {

                        for (image in vimageList) {
                            val imageView = ImageView(it)
                            val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                                    , ViewGroup.LayoutParams.WRAP_CONTENT)
                            layoutParams.setMargins(30, 30, 30, 30)
                            layoutParams.gravity = Gravity.CENTER
                            imageView.layoutParams = layoutParams

                            val myOptions = RequestOptions()
                                    .override(7000, 2000).centerCrop()
                            //  val bitmap = Compressor(it).run { compressToBitmap(image.toUri().toFile()) }
                            Glide.with(it)
                                    .asBitmap()
                                    .apply(myOptions)
                                    .skipMemoryCache(true)
                                    .load(image)
                                    .placeholder(R.mipmap.ic_launcher)
                                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                                    .thumbnail(0.1f)
                                    .into(imageView)
                            viewFlipper.addView(imageView)
                        }
                    }
                }


            }


        }

        val myv = "View :" + "\n"
        val e = myv.plus(checkv.text)
        checkv.text = e
        val myc = "Age of Property :" + "\n"
        val c = myc.plus(checky.text)
        checky.text = c
        val myeb = "Electricity Backup :" + "\n"
        val eb = myeb.plus(checke.text)
        checke.text = eb

        val price: String? = intent.getStringExtra("vPrice")
        val adres: String? = intent.getStringExtra("vAdres")
        val vDesc: String? = intent.getStringExtra("vDes")
        val vcid: Int = intent.getIntExtra("CAT_ID", 0)
        val vPur: Int = intent.getIntExtra("vPur", 0)

        vprice.text = price.toString()
        addre.text = adres.toString()
        pvalu.text = vProid.toString()
        pric.text = price.toString()
        loc.text = adres.toString()
        text_view.text = vDesc.toString()

        val amen = "http://rehajomobileapi.hundredalpha.com/api/Amenities/GETAmenityDynamicControls/?PropertyID=$vProid&CategID=$vcid"

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
                    //var gener = acpValues?.get(values_ac)!!.ACPID_FK.toString()

                    if ((acpValues?.get(values_ac)?.ACPID_FK) as Int > 0) {
                        intent.putExtra("Electricity Backup", acpValues[values_ac].ACPID_FK.toString())
                        PropertiesAdapter.melectric = acpValues[values_ac].ACPID_FK
                        checke.text = acpValues[values_ac].ACP_Value.toString()
                    }
                }

                val storage = ameinityList!![0].amenityNumericValue
                val furnish = ameinityList[32].amenityNumericValue
                val ac = ameinityList[31].amenityNumericValue
                val pking = ameinityList[27].amenityNumericValue
                val owr = ameinityList[1].amenityNumericValue
                val study = ameinityList[6].amenityNumericValue
                val draw = ameinityList[16].amenityNumericValue
                val bed = ameinityList[25].amenityNumericValue
                val bath = ameinityList[26].amenityNumericValue
                val kitch = ameinityList[10].amenityNumericValue
                val stroom = ameinityList[17].amenityNumericValue
                val laundry = ameinityList[11].amenityNumericValue
                val intrcm = ameinityList[18].amenityNumericValue
                val tvcm = ameinityList[19].amenityNumericValue
                val brod = ameinityList[20].amenityNumericValue
                val prayr = ameinityList[9].amenityNumericValue
                val maid = ameinityList[2].amenityNumericValue
                val lung = ameinityList[23].amenityNumericValue
                val suana = ameinityList[22].amenityNumericValue
                val lawn = ameinityList[22].amenityNumericValue
                val jac = ameinityList[21].amenityNumericValue
                val swim = ameinityList[13].amenityNumericValue
                val gym = ameinityList[24].amenityNumericValue
                val prkk = ameinityList[28].amenityNumericValue
                val uni = ameinityList[3].amenityNumericValue
                val schl = ameinityList[5].amenityNumericValue
                val colg = ameinityList[4].amenityNumericValue
                val servnt = ameinityList[8].amenityNumericValue
                val msjid = ameinityList[29].amenityNumericValue


                checkv.text = if (isEmpty(ameinityList[30].amenityTextValue)) "" else ameinityList[30].amenityTextValue

                val landar = ameinityList[14].amenityNumericValue.emptyOrDouble()
                val formater = DecimalFormat("#")
                val formated = formater.format(landar)
                checky.text = formated.emptyOrDouble().toString()
                val landar1 = bed.emptyOrDouble()
                val formater1 = DecimalFormat("#")
                val formated1 = formater1.format(landar1)
                bathtext.text = formated1.emptyOrDouble().toString()
                myImageViewTextbt.text = formated1.emptyOrDouble().toString()
                checkbed.text = formated1.emptyOrDouble().toString()
                val landar2 = bath.emptyOrDouble()
                val formater2 = DecimalFormat("#")
                val formated2 = formater2.format(landar2)
                bedtext.text = formated2.emptyOrDouble().toString()
                myImageViewTextbd.text = formated2.emptyOrDouble().toString()
                checkbth.text = if (isEmpty(formated2)) 0.toString() else formated2!!
                val landar3 = kitch.emptyOrDouble()
                val formater3 = DecimalFormat("#")
                val formated3 = formater3.format(landar3)
                checkitch.text = if (isEmpty(formated3)) 0.toString() else formated3
                val landar4 = stroom.emptyOrDouble()
                val formater4 = DecimalFormat("#")
                val formated4 = formater4.format(landar4)
                checkstrom.text =formated4.emptyOrDouble().toString()

                (msjid)?.emptyOrDouble()?.let { doubtostr(it, vw = checkmsjid) }!!
                (servnt)?.emptyOrDouble()?.let { doubtostr(it, vw = checkser) }!!
                (colg)?.emptyOrDouble()?.let { doubtostr(it, vw = checkcol) }!!
                (schl)?.emptyOrDouble()?.let { doubtostr(it, vw = checkschol) }!!
                (uni)?.emptyOrDouble()?.let { doubtostr(it, vw = checkuni) }!!
                (prkk)?.emptyOrDouble()?.let { doubtostr(it, vw = checkparkk) }!!
                (lung)?.emptyOrDouble()?.let { doubtostr(it, vw = checklung) }!!
                (suana)?.emptyOrDouble()?.let { doubtostr(it, vw = checksuana) }!!
                (lawn)?.emptyOrDouble()?.let { doubtostr(it, vw = checklawn) }!!
                (jac)?.emptyOrDouble()?.let { doubtostr(it, vw = checkjac) }!!
                (swim)?.emptyOrDouble()?.let { doubtostr(it, vw = checkswim) }!!
                (gym)?.emptyOrDouble()?.let { doubtostr(it, vw = checkgym) }!!
                (maid)?.emptyOrDouble()?.let { doubtostr(it, vw = checkmaid) }!!
                (prayr)?.emptyOrDouble()?.let { doubtostr(it, vw = checkpry) }!!
                (brod)?.emptyOrDouble()?.let { doubtostr(it, vw = checkbord) }!!
                (study)?.emptyOrDouble()?.let { doubtostr(it, vw = checkstdy) }!!
                (draw)?.emptyOrDouble()?.let { doubtostr(it, vw = checkdraw) }!!
                (laundry)?.emptyOrDouble()?.let { doubtostr(it, vw = checklandry) }!!
                (intrcm)?.emptyOrDouble()?.let { doubtostr(it, vw = checkintr) }!!
                (tvcm)?.emptyOrDouble()?.let { doubtostr(it, vw = checktv) }!!
                (storage)?.emptyOrDouble()?.let { doubtostr(it, vw = checks) }!!
                (owr)?.emptyOrDouble().let {
                    if (it != null) {
                        doubtostr(it, vw = checkcar)
                    }
                }
                (furnish)?.emptyOrDouble()?.let { doubtostr(it, vw = checkf) }!!
                (ac)?.emptyOrDouble()?.let { doubtostr(it, vw = checkp) }!!
                (pking)?.emptyOrDouble()?.let { doubtostr(it, vw = checkpar) }!!
                purps.text = (if (isEmpty(vPur.toString())) "For " + inttostr(vPur) else (vPur).let { "For " + inttostr(it) }).toString()
                avalble.text = (if (isEmpty(vPur.toString())) "Available for " + inttostr(vPur) else (vPur).let { "Available for " + inttostr(it) }).toString()
                ty.text = intent.getStringExtra("proprTpye").toString()

            }

        }

    }

    private fun doubtostr(value: Any, vw: TextView): Int {
        if (value == 1.0) {

            vw.visibility = View.VISIBLE

            return vw.visibility

        } else if (value == 0.0) {

            vw.visibility = View.GONE
            return vw.visibility

        }

        return vw.visibility
    }

    private fun inttostr(value: Int?): String {
        if (value == 1) {
            return "Sale"
        } else if (value == 2) {
            return "Rent"
        }
        return "NOt For Sale / Rent"
    }


}