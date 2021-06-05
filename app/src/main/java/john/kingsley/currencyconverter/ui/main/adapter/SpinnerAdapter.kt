package john.kingsley.currencyconverter.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import android.widget.TextView
import john.kingsley.currencyconverter.R
import john.kingsley.currencyconverter.data.Currency


class SpinnerAdapter(val context: Context, var dataSource: List<Currency>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.spinner_item, parent, false)
            vh =
                ItemHolder(
                    view
                )
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.name.text = dataSource.get(position).name
        vh.code.text = dataSource.get(position).code

        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val name: TextView
        val code: TextView

        init {
            name = row?.findViewById(R.id.desc) as TextView
            code = row.findViewById(R.id.code) as TextView
        }
    }

}