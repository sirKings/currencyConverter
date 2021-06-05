package john.kingsley.currencyconverter.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import john.kingsley.currencyconverter.R
import john.kingsley.currencyconverter.data.Rate
import john.kingsley.currencyconverter.data.util.getFormattedDate

class RecyclerViewAdapter(private val dataSet: ArrayList<Rate>, private val context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rate: TextView = view.findViewById(R.id.rate)
        val desc: TextView = view.findViewById(R.id.desc)
        val time: TextView = view.findViewById(R.id.time)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.rate_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.desc.text = dataSet[position].name
        viewHolder.time.text = dataSet[position].updatedAt.getFormattedDate(context)
        viewHolder.rate.text = "${dataSet[position].rate} ${dataSet[position].code}"
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}