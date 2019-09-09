package ha.ecz.com.subscriberpanel.Adapter


import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import ha.ecz.com.agentportal.R

class AddMultipleImagesadapter(private val urls: ArrayList<String>?) : RecyclerView.Adapter<AddMultipleImagesadapter.ImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.addimage, parent, false)
        return ImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {

        val objProp = urls?.get(position)

        Glide.with(holder.itemView.context)
                .load(objProp)
                .centerCrop().fitCenter()
                .override(600, 200)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        // log exception
                        Toast.makeText(holder.itemView.context, " Error loading images", Toast.LENGTH_SHORT).show()

                        return false // important to return false so the error placeholder can be placed
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: com.bumptech.glide.load.DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }


                })
                .into(holder._imgMulti)

        holder._delPro.setOnClickListener {
            urls?.removeAt(position)
            notifyDataSetChanged()
            notifyItemRemoved(position)
        }

    }


    override fun getItemCount(): Int {
        return urls!!.size
    }

    inner class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val _imgMulti: ImageView = itemView.findViewById<View>(R.id.imgshow) as ImageView
        val _delPro: ImageView = itemView.findViewById<View>(R.id.widget_delete_icon) as ImageView

        init {
        }
    }

}
