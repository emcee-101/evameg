package de.egovt.evameg.utility

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat


fun activityAskForPermissions(activity: Activity, permissions:Array<String>):Boolean{

    // TODO add handling when permission was not given, maybe a alertbox
    ActivityCompat.requestPermissions(activity, permissions, 1)
    return true;
}

fun activityIsPermissionGiven(context: Context, permissions:Array<String>, activity: Activity):Boolean{

    var permissionsToBeAsked:Array<String> = arrayOf()

    if (context != null && permissions != null) {

        for (permission in permissions) {

            if (ActivityCompat.checkSelfPermission(context, permission!!) != PackageManager.PERMISSION_GRANTED)
            {
                permissionsToBeAsked = permissionsToBeAsked.plus(permission)
            }
        }

        if(permissionsToBeAsked.isNotEmpty()){
            activityAskForPermissions(activity, permissionsToBeAsked)
        }

    }

    return true


}

