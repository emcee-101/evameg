package de.egovt.evameg.utility

import android.provider.BaseColumns
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_ADDRESS
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_LAT
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_LONG
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_NAME
import de.egovt.evameg.utility.OfficesDataContract.OfficeDataEntry.COLUMN_NAME_TYPE
import de.egovt.evameg.utility.ProposalDataContract.ProposalDataEntry.COLUMN_NAME_CATEGORY
import de.egovt.evameg.utility.ProposalDataContract.ProposalDataEntry.COLUMN_NAME_DATE
import de.egovt.evameg.utility.ProposalDataContract.ProposalDataEntry.COLUMN_NAME_OFFICE_ID
//import de.egovt.evameg.utility.ProposalDataContract.ProposalDataEntry.COLUMN_NAME_OFFICE_ID
import de.egovt.evameg.utility.ProposalDataContract.ProposalDataEntry.COLUMN_NAME_PROPOSAL_NAME
import de.egovt.evameg.utility.ProposalDataContract.ProposalDataEntry.COLUMN_NAME_STATUS
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_DATE_OF_BIRTH
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_FIRSTNAME
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_LASTNAME
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_POSTAL_CODE
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_STREET
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_WOHNORT

/**
 * userProfilData, OfficesData and ProposalData contracts, that define table names and column names
 *
 * @author Celina Ludwigs, Niklas Herzog
 */

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

    object ProposalDataContract{
        object ProposalDataEntry: BaseColumns {
            const val TABLE_NAME = "PROPOSAL_DATA_TABLE"
            const val COLUMN_NAME_PROPOSAL_NAME = "proposal_name"
            const val COLUMN_NAME_CATEGORY = "category"
            const val COLUMN_NAME_DATE = "date"
            const val COLUMN_NAME_STATUS= "status"
            const val COLUMN_NAME_OFFICE_ID = "office_id"

        }
    }

/**
 * userProfilData, OfficesData and ProposalData statements,which create the tables
 *
 * @author Celina Ludwigs, Niklas Herzog
 */

     const val SQL_CREATE_ENTRIES_USER="CREATE TABLE ${UserProfileDataContract.UserProfileDataEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "$COLUMN_NAME_USER_FIRSTNAME TEXT, " +
            "$COLUMN_NAME_USER_LASTNAME TEXT, " +
            "$COLUMN_NAME_DATE_OF_BIRTH 'DATE', "  +         //'DATE' not existing, but it works
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

const val SQL_CREATE_ENTRIES_PROPOSAL="CREATE TABLE ${ProposalDataContract.ProposalDataEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
        "$COLUMN_NAME_PROPOSAL_NAME TEXT, " +
        "$COLUMN_NAME_CATEGORY TEXT, " +
        "$COLUMN_NAME_DATE 'DATE', " +
        "$COLUMN_NAME_STATUS TEXT, " +
        "$COLUMN_NAME_OFFICE_ID INTEGER, " +
        "FOREIGN KEY ($COLUMN_NAME_OFFICE_ID) REFERENCES ${OfficesDataContract.OfficeDataEntry.TABLE_NAME}(${BaseColumns._ID})" +
        //Column in der man die Daten aus dem Antrag als Text abspeichert für Zukünftige Änderungen
        ") "

/**
 * userProfilData, OfficesData and ProposalData statements, which delete the tables
 *
 * @author Celina Ludwigs, Niklas Herzog
 */

     const val SQL_DELETE_ENTRIES_USER="DROP TABLE IF EXISTS ${UserProfileDataContract.UserProfileDataEntry.TABLE_NAME}"
     const val SQL_DELETE_ENTRIES_OFFICES="DROP TABLE IF EXISTS ${OfficesDataContract.OfficeDataEntry.TABLE_NAME}"
     const val SQL_DELETE_ENTRIES_PROPOSAL="DROP TABLE IF EXISTS ${ProposalDataContract.ProposalDataEntry.TABLE_NAME}"
