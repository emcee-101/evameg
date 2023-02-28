package de.egovt.evameg.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.egovt.evameg.R
import de.egovt.evameg.utility.DB.DbHelper
import de.egovt.evameg.utility.ProposalData
import kotlinx.coroutines.launch


data class HistoryData(
    val title: String?,
    val date : String?,
    val status: String?,
    val desc: String?,
    var isExpandable: Boolean = false
)

class HistoryFragment : Fragment() {

    private lateinit var thisView: View
    private val recyclerViewData = ArrayList<HistoryData>()
    private lateinit var myRubbishBin: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initHistoryList().start()
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_history, container, false)

        return thisView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myRubbishBin = thisView.findViewById(R.id.historyRecyclerView)
        myRubbishBin.layoutManager = LinearLayoutManager(context)

        initHistoryList().start()

    }
    /**
     * R
     *
     * @author Mohammad Zidane
     */
    inner class initHistoryList : ViewModel() {

        fun start(){

            val historyList: List<ProposalData> = readProposalData()
            viewModelScope.launch {
                try {
                    for(item : ProposalData in historyList ){
                        var historyItemDesc = ""
                        if (item.status == "complete"){
                            historyItemDesc = getString(R.string.applicationCompleteDesc)
                        }
                        else if (item.status == "processing"){
                            historyItemDesc = getString(R.string.applicationBeingProcessedDesc)
                        }
                        recyclerViewData.add(HistoryData(item.category, item.date, item.status, historyItemDesc))
                    }
                    // This will pass the ArrayList to our Adapter
                    val adapter = HistoryAdapter(recyclerViewData)
                    // Setting the Adapter with the recyclerview
                    myRubbishBin.adapter = adapter
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Handle the exception
                }
            }
        }
    }

    /**
     * Adapter for Recycler View
     *
     * @author Mohammad Zidane
     */
    inner class HistoryAdapter(private val mList: List<HistoryData>) :
        RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.history_item_view, parent, false)

            return ViewHolder(view, true)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val historyItem = mList[position]

            // sets the text to the textview from our itemHolder class
            holder.itemTitle.text = historyItem.title
            holder.itemStatus.text = historyItem.status
            holder.itemDate.text = historyItem.date
            holder.itemDescription.text = historyItem.desc

            val isExpandable: Boolean = historyItem.isExpandable
            holder.itemDescription.visibility = if (isExpandable) View.VISIBLE else View.GONE

            holder.constraintLayout.setOnClickListener {
                isAnyItemExpanded(position)
                historyItem.isExpandable = !historyItem.isExpandable
                notifyItemChanged(position , Unit)
            }
        }
        private fun isAnyItemExpanded(position: Int){
            val temp = mList.indexOfFirst {
                it.isExpandable
            }
            if (temp >= 0 && temp != position){
                mList[temp].isExpandable = false
                notifyItemChanged(temp , 0)
            }
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int,
            payloads: MutableList<Any>
        ) {

            if(payloads.isNotEmpty() && payloads[0] == 0){
                holder.collapseExpandedView()
            }else{
                super.onBindViewHolder(holder, position, payloads)
            }
        }
        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList.size
        }

        // Holds the views for adding it to image and text
        inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView),
            View.OnClickListener {

            val itemTitle: TextView = itemView.findViewById(R.id.historyItemTitle)
            val itemStatus: TextView = itemView.findViewById(R.id.historyItemStatus)
            val itemDate: TextView = itemView.findViewById(R.id.historyItemdate)
            val itemDescription: TextView = itemView.findViewById(R.id.historyItemDesc)
            val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

            fun collapseExpandedView(){
                itemDescription.visibility = View.GONE
            }

            constructor(ItemView: View, listenerRequested: Boolean) : this(ItemView) {

                if (listenerRequested) ItemView.setOnClickListener(this)

            }

            // switch visibility of expanded text
            override fun onClick(p0: View?) {

            }
        }
    }

    /**
     * Calls Database to read Offices of given type
     *
     * @return list of offices to later be added to the map
     */
    private fun readProposalData():List<ProposalData>{

        val db = DbHelper(context)
        return db.readProposalData()
    }
}