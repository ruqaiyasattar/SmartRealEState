package ha.ecz.com.subscriberpanel.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ACPValues {

    @SerializedName("ACPID_PK")
    @Expose
    var ACPID_PK: Int? = null
    @SerializedName("ACP_Value")
    @Expose
    var ACP_Value: String? = null
    @SerializedName("ACPID_FK")
    @Expose
    var ACPID_FK: Int? = null
    @SerializedName("AmenityID_FK")
    @Expose
    var AmenityID_FK: Int? = null

}