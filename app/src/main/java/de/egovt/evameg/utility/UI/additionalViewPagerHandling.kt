package de.egovt.evameg.utility.UI

import androidx.viewpager2.widget.ViewPager2


/**
 *  when when scrolling to next item in Setupactivity
 */
fun cont(myViewPager: ViewPager2? ){
        myViewPager?.currentItem = myViewPager!!.currentItem + 1
    }

/**
 *  when "back" is pressed in Setupactivity
 */
fun back(myViewPager: ViewPager2? ){
        if(myViewPager?.currentItem != 0)
            myViewPager?.currentItem = myViewPager!!.currentItem - 1
    }



