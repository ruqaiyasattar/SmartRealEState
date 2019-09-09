package ha.ecz.com.subscriberpanel.Adapter

import android.content.Context
import android.util.DisplayMetrics
import android.graphics.PointF
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class CustomLinearLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean) : LinearLayoutManager(context, orientation, reverseLayout) {

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            private val MILLISECONDS_PER_INCH = 200f

            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@CustomLinearLayoutManager
                        .computeScrollVectorForPosition(targetPosition)
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }
        }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }
}
