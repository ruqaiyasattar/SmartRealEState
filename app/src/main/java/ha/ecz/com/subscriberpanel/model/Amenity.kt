package ha.ecz.com.subscriberpanel.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Amenity {

    @SerializedName("ControlTypeID")
    @Expose
    var controlTypeID: Int? = null
    @SerializedName("amenityName")
    @Expose
    var amenityName: String? = null
    @SerializedName("AmenityID_PK")
    @Expose
    var amenityIDPK: Int? = null
    @SerializedName("AmenityGroupID_FK")
    @Expose
    var amenityGroupIDFK: Int? = null
    @SerializedName("AmenityID_FK")
    @Expose
    var amenityIDFK: Int? = null
    @SerializedName("AmenityTextValue")
    @Expose
    var amenityTextValue: String? = null
    @SerializedName("AmenityNumericValue")
    @Expose
    var amenityNumericValue: String? = null

}