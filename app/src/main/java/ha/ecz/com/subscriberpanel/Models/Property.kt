package ha.ecz.com.subscriberpanel.Models

class Property {
    var PropertyID: Int = 0
    var Title: String? = " "
    var Description: String? = " "
    var Address: String? = " "
    var streetName: String? = " "
    var DistrictZone: String? = " "
    var PostalCode: String? = " "
    var CityID: Int = 0
    var City: City? = null
    var PropertyCategoryName :String=""
    var PropertyCategoryID: Int = 0
    var PropertyCategory: String? = null
    var PurposeID: Int = 0
    var Purpose: Purpose? = null
    var LandArea: Double = 0.toDouble()
    var LandAreaUnitID: Int = 0
    var LandAreaUnit: Unit? = null
    var PriceBudget: Double = 0.toDouble()
    var CurrencyID: Int = 0
    var Currency: Currency? = null
    var Latitude: Double = 0.toDouble()
    var Longitude: Double = 0.toDouble()
    var Privacy: Int = 0
    var IsHot: Boolean = false
    var IsFeatured: Boolean = false
    var IsPromo: Boolean = false
    var IsPopOut: Boolean = false
    var FrontImage: String? = " "
    var LocalAreaName: String? = " "
    var Active: Int = 0
    var SubscriberID: Int = 0
    var CreatedDate: String? = " "
    var CreatedBy: Int = 0
    var LastUpdated: String? = " "
    var DescriptionInText: String? = " "
    var Address_Component: String? = " "
    var LocationAlias: String? = " "
    var SubLocalityLevel: String? = " "
    var AdditionalImages:String?=""

}
