package ha.ecz.com.subscriberpanel.retrofit

import ha.ecz.com.subscriberpanel.ManagePropertyMasterActivity.Companion.updatePropertyId
import ha.ecz.com.subscriberpanel.Models.GetAdditionalImage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {
    @POST("ManageProperty/POSTaddpropertyintodatabase/")
    @FormUrlEncoded
    fun savePost(@Field("title") title: String,
                 @Field("streetName") streetName: String,
                 @Field("Address") Address: String,
                 @Field("LocationAlias") LocationAlias: String,
                 @Field("SubLocalityLevel") SubLocalityLevel: String,
                 @Field("Latitude") Latitude: Double,
                 @Field("Longitude") Longitude: Double,
                 @Field("PurposeID") PurposeID: Int,
                 @Field("CurrencyID") CurrencyID: Int,
                 @Field("PriceBudget") PriceBudget: Double,
                 @Field("LandArea") LandArea: Double,
                 @Field("UnitID") UnitID: Int,
                 @Field("PropertyCategoryID") CategoryID: Int,
                 @Field("Privacy") Privacy: Int,
                 @Field("IsHot") IsHot: Boolean,
                 @Field("IsFeatured") IsFeatured: Boolean,
                 @Field("IsPromo") IsPromo: Boolean,
                 @Field("IsPopOut") IsPopOut: Boolean,
                 @Field("SubscriberID") SubscriberID: Int,
                 @Field("CreatedBy") CreatedBy: Int,
                 @Field("FrontImage") FrontImage: String,
                 @Field("Description") Description: String,
                 @Field("Amenities") Amenities: String,
                 @Field("CityID") CityID: Int): Call<ResponseBody>

    @PUT("ManageProperty/PUTUpdatepropertyintodatabase/")
    @FormUrlEncoded
    fun updatePost(
            @Field("PropertyID") propertyId: Int,
            @Field("title") title: String,
            @Field("streetName") streetName: String,
            @Field("Address") Address: String,
            @Field("LocationAlias") LocationAlias: String,
            @Field("SubLocalityLevel") SubLocalityLevel: String,
            @Field("Latitude") Latitude: Double,
            @Field("Longitude") Longitude: Double,
            @Field("PurposeID") PurposeID: Int,
            @Field("CurrencyID") CurrencyID: Int,
            @Field("PriceBudget") PriceBudget: Double,
            @Field("LandArea") LandArea: Double,
            @Field("UnitID") UnitID: Int,
            @Field("PropertyCategoryID") CategoryID: Int,
            @Field("Privacy") Privacy: Int,
            @Field("IsHot") IsHot: Boolean,
            @Field("IsFeatured") IsFeatured: Boolean,
            @Field("IsPromo") IsPromo: Boolean,
            @Field("IsPopOut") IsPopOut: Boolean,
            @Field("SubscriberID") SubscriberID: Int,
            @Field("CreatedBy") CreatedBy: Int,
            @Field("FrontImage") FrontImage: String,
            @Field("Description") Description: String,
            @Field("Amenities") Amenities: String,
            @Field("CityID") CityID: Int): Call<ResponseBody>


    @POST("ManageProperty/additionalImagesintodatatbase/")
    @FormUrlEncoded
    fun imgPost(
            @Field("PropertyID") propertyId: Any?,
            @Field("AdditionalImage") MultiImg: Any?): Call<ResponseBody>

    @GET("Properties/GetAdditionalImages/")
    fun getImagesPhotos(@Query("PropertyID") result: Int): Call<GetAdditionalImage>
}



