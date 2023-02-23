package de.egovt.evameg.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import com.squareup.picasso.Picasso
import de.egovt.evameg.R
import de.egovt.evameg.activities.NewApplication
import de.egovt.evameg.activities.simpleWebView
import kotlinx.coroutines.launch
import java.nio.charset.Charset

data class ItemsViewModel(val text: String?, val image: String?, val link : String?) {
}

/**
 * A Fragment that displays a RSS feed and a button to refer to forms.
 *
 * @author Niklas Herzog, Mohammad Zidane
 */
class Home : Fragment() {

    private lateinit var thisView : View
    private lateinit var parser : Parser
    private lateinit var momentaryContext : Context
    private val recyclerViewData = ArrayList<ItemsViewModel>()
    private lateinit var myRubbishBin : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        thisView = inflater.inflate(R.layout.fragment_home, container, false)
        return thisView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        momentaryContext = context

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // New Form logic
        val newApplication = thisView.findViewById<FloatingActionButton>(R.id.new_application)
        newApplication.setOnClickListener {
            startActivity(Intent(activity, NewApplication::class.java))
        }


        thisView.findViewById<TextView>(R.id.home_textView).text = getString(R.string.office_rss_title)

        myRubbishBin = thisView.findViewById(R.id.recycler_home)
        myRubbishBin.layoutManager = LinearLayoutManager(momentaryContext)


        rssReader().start()


    }


    /**
     * RSS Parser
     *
     * @author Niklas Herzog
     */
    inner class rssReader : ViewModel() {

        private val url = "https://www.erfurt.de/ef/de/service/rss/medien"

        fun start(){

            parser = Parser.Builder()
                .context(momentaryContext)
                .charset(Charset.forName("UTF-8"))
                .cacheExpirationMillis(24L * 60L * 60L * 1000L) // one day
                .build()

            viewModelScope.launch {
                try {
                    val channel = parser.getChannel(url)
                    // Do something with your data

                    Log.i("aaaa", "Your Rss feed dear sir or madam - title is ${channel.title}")

                    for(article : Article in channel.articles ){

                        recyclerViewData.add(ItemsViewModel(article.title, article.image, article.link))

                        //Log.i("aaaa", "added an article to list of articles")
                        //Log.i("aaaa", "The image is: ${article.image}")

                    }

                    // This will pass the ArrayList to our Adapter
                    val adapter = CustomAdapter(recyclerViewData)

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
     * @author Niklas Herzog
     */
    inner class CustomAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.home_card_view, parent, false)


            return ViewHolder(view, true)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val ItemsViewModel = mList[position]

            // Display Image as 50x50
            Picasso.with(momentaryContext).load(ItemsViewModel.image).resize(50, 50).into(holder.imageView);

            // sets the text to the textview from our itemHolder class
            holder.textView.text = ItemsViewModel.text

            holder.positionInArray = position

            holder.link = ItemsViewModel.link


        }

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList.size
        }

        // Holds the views for adding it to image and text
        inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {

            val imageView: ImageView = itemView.findViewById(R.id.imageview)
            val textView: TextView = itemView.findViewById(R.id.textView)
            var link: String? = null
            var positionInArray : Int = 0

            constructor(ItemView: View, listenerRequested : Boolean) : this(ItemView){

                if(listenerRequested) ItemView.setOnClickListener(this)

            }

            // switch visibility of expanded text
            override fun onClick(p0: View?) {

                val referToWeb : Intent = Intent(momentaryContext, simpleWebView::class.java)
                referToWeb.putExtra("url_", link)
                startActivity(referToWeb)

            }

        }

    }

}


