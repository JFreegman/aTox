package ltd.evilcorp.atox.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.contact_list_view_item.view.*
import ltd.evilcorp.atox.R
import ltd.evilcorp.atox.repository.ContactRepository
import ltd.evilcorp.atox.tox.byteArrayToHex
import ltd.evilcorp.atox.vo.Contact

class ContactAdapter(
    private val context: Context,
    lifecycleOwner: LifecycleOwner,
    contactRepository: ContactRepository
) : BaseAdapter() {
    private var contacts: List<Contact> = ArrayList()

    init {
        contactRepository.getContacts().observe(lifecycleOwner, Observer {
            contacts = it
            notifyDataSetChanged()
        })
    }

    override fun getCount(): Int = contacts.size
    override fun getItem(position: Int): Any = contacts[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View?
        val vh: ViewHolder

        if (convertView == null) {
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.contact_list_view_item, null, true)
            vh = ViewHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        val contact = contacts[position]
        vh.publicKey.text = contact.publicKey.byteArrayToHex().toUpperCase()
        vh.name.text = contact.name
        vh.lastMessage.text = contact.lastMessage

        return view!!
    }

    private class ViewHolder(row: View) {
        val name: TextView = row.name
        val publicKey: TextView = row.publicKey
        val lastMessage: TextView = row.lastMessage
    }
}