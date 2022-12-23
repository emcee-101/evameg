package de.egovt.evameg.utility.UI

import androidx.viewpager2.widget.ViewPager2


fun cont(myViewPager: ViewPager2? ){
        myViewPager?.currentItem = myViewPager!!.currentItem + 1
    }

fun back(myViewPager: ViewPager2? ){
        if(myViewPager?.currentItem != 0)
            myViewPager?.currentItem = myViewPager!!.currentItem - 1
    }



