package ha.ecz.com.subscriberpanel.Adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable

import java.util.ArrayList

import ha.ecz.com.subscriberpanel.googleMapApi.PlaceAPI


internal class PlacesAutoCompleteAdapter( mContext: Context,  mResource: Int) : ArrayAdapter<String>(mContext, mResource), Filterable {

   lateinit var resultList: ArrayList<String>

    var mPlaceAPI = PlaceAPI()

    override fun getCount(): Int {
        // Last item will be the footer
        return resultList.size
    }

    override fun getItem(position: Int): String? {
        return resultList[position]
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    resultList = mPlaceAPI.autocomplete(constraint.toString())

                    filterResults.values = resultList
                    filterResults.count = resultList.size
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }
/*
    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        var view: View

        //if (convertView == null) {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        if (position != resultList!!.size - 1)
        // view = inflater.inflate(R.layout.autocomplete_google_logo, null)
        else
        // view = inflater.inflate(R.layout.autocomplete_google_logo, null)
        //}
        //else {
        //    view = convertView;
        //}

           *//* if (position != resultList.size - 1) {
                val autocompleteTextView = view.findViewById(R.id.autocompleteText) as TextView
                autocompleteTextView.text = resultList.get(position)
            } else {
                // val imageView = view.findViewById(R.id.imageView) as ImageView
                // not sure what to do 😀
            }*//*

        return view
    }*/
}