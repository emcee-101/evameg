package de.egovt.evameg.utility

import android.provider.BaseColumns
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_ADDRESS
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_LAT
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_LONG
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_NAME
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_TYPE
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_DATE_OF_BIRTH
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_FIRSTNAME
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_LASTNAME
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_POSTAL_CODE
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_STREET
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_WOHNORT


//contract, that defines table name and column names
    object UserProfileDataContract{
        object UserProfileDataEntry: BaseColumns {
            const val TABLE_NAME = "USER_DATA_TABLE"
            const val COLUMN_NAME_USER_FIRSTNAME = "firstname"
            const val COLUMN_NAME_USER_LASTNAME = "lastname"
            const val COLUMN_NAME_DATE_OF_BIRTH = "date_of_birth"
            const val COLUMN_NAME_USER_WOHNORT= "wohnort"
            const val COLUMN_NAME_USER_POSTAL_CODE = "postal_code"
            const val COLUMN_NAME_USER_STREET = "street"
        }

    }

    // val id:String, val name:String, val address:String, val type:String, val latitude:Double, val longitude:Double
    object OfficesDataContract{
        object OfficeDataEntry: BaseColumns {
            const val TABLE_NAME = "OFFICES_DATA_TABLE"
            const val COLUMN_NAME_NAME = "office_name"
            const val COLUMN_NAME_ADDRESS = "office_address"
            const val COLUMN_NAME_TYPE = "office_type"
            const val COLUMN_NAME_LAT = "latitude"
            const val COLUMN_NAME_LONG = "longitude"
        }
    }


    //statement,that creates the table
     const val SQL_CREATE_ENTRIES_USER="CREATE TABLE ${UserProfileDataContract.UserProfileDataEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "$COLUMN_NAME_USER_FIRSTNAME TEXT, " +
            "$COLUMN_NAME_USER_LASTNAME TEXT, " +
            "$COLUMN_NAME_DATE_OF_BIRTH 'DATE', "  +
            "$COLUMN_NAME_USER_WOHNORT TEXT, " +
            "$COLUMN_NAME_USER_POSTAL_CODE INTEGER, " +
            "$COLUMN_NAME_USER_STREET TEXT) "

     const val SQL_CREATE_ENTRIES_OFFICES="CREATE TABLE ${OfficesDataContract.OfficeDataEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "$COLUMN_NAME_NAME TEXT, " +
             "$COLUMN_NAME_ADDRESS TEXT, " +
             "$COLUMN_NAME_TYPE TEXT, " +
             "$COLUMN_NAME_LAT REAL, " +
             "$COLUMN_NAME_LONG REAL " +
            ") "

    //statements, that deletes the table
     const val SQL_DELETE_ENTRIES_USER="DROP TABLE IF EXISTS ${UserProfileDataContract.UserProfileDataEntry.TABLE_NAME}"
     const val SQL_DELETE_ENTRIES_OFFICES="DROP TABLE IF EXISTS ${OfficesDataContract.OfficeDataEntry.TABLE_NAME}"

