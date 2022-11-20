package de.egovt.evameg.utility

import androidx.viewpager2.widget.ViewPager2


    public fun cont(myViewPager: ViewPager2? ){
        myViewPager?.currentItem = myViewPager!!.currentItem + 1
    }

    public fun back(myViewPager: ViewPager2? ){
        if(myViewPager?.currentItem != 0)
            myViewPager?.currentItem = myViewPager!!.currentItem - 1
    }



