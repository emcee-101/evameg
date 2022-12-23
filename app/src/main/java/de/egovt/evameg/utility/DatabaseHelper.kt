package de.egovt.evameg.utility

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
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
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.TABLE_NAME





private fun mapValues(data:DataStructure):ContentValues {

    var values = ContentValues()


    if(data is UserProfileData){

        values.put(COLUMN_NAME_USER_FIRSTNAME, data.firstName)
        values.put(COLUMN_NAME_USER_LASTNAME, data.lastName)
        values.put(COLUMN_NAME_DATE_OF_BIRTH, data.dateOfBirth)
        values.put(COLUMN_NAME_USER_WOHNORT, data.wohnort)
        values.put(COLUMN_NAME_USER_POSTAL_CODE, data.postalCode)
        values.put(COLUMN_NAME_USER_STREET, data.street)

    } else if (data is Office){

        values.put(COLUMN_NAME_LAT, data.latitude)
        values.put(COLUMN_NAME_LONG, data.longitude)
        values.put(COLUMN_NAME_NAME, data.name)
        values.put(COLUMN_NAME_TYPE, data.type)
        values.put(COLUMN_NAME_ADDRESS, data.address)

    } else {

        Log.w("DB", "unrecognized Data is trying to be inserted into the function")

    }

    return values
}


class DbHelper(var context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(SQL_CREATE_ENTRIES_USER)
        db.execSQL(SQL_CREATE_ENTRIES_OFFICES)
        Log.i("db", "Created the Tables in the DB")

    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL(SQL_DELETE_ENTRIES_USER)
        db.execSQL(SQL_DELETE_ENTRIES_OFFICES)
        onCreate(db)


    }


    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "EVAMEG_DATA_DB"

        private var instance: DbHelper? = null

        //Accessing database
        operator fun invoke(context: Context) = instance ?: DbHelper(context).also { instance = it }
    }



    fun insertUserData(userProfileData: UserProfileData) {

        //writing in database
        val db = this.writableDatabase

        //new map with values, column names are keys
        val values = mapValues(userProfileData)

        //new row insert,primary key value return
        var result =
            db.insert(UserProfileDataContract.UserProfileDataEntry.TABLE_NAME, null, values)
        if (result == 1.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

        }
    }


    fun insertOfficeData(officeData: Office) {

        //writing in database
        val db = this.writableDatabase

        //new map with values, column names are keys
        val values = mapValues(officeData)

        var result = db.insert(OfficesDataContract.OfficeDataEntry.TABLE_NAME, null, values)

        //TODO verify this somehow
    }


    fun readUserData() :MutableList<UserProfileData>{
        var list: MutableList<UserProfileData> =ArrayList()
        val db= this.readableDatabase
        val query="SELECT * FROM $TABLE_NAME ORDER BY ${BaseColumns._ID} DESC LIMIT 1" //vorher nach Ids sortiert absteigend limit 1 , mit nachnamen*/
        //limit vor order by ?
        val result=db.rawQuery(query,null)//statt where and order by in selection args?
        if (result.moveToLast()){
            do {
                var userProfileData= UserProfileData()
                userProfileData.id=result.getString(result.getColumnIndex(BaseColumns._ID)).toInt()
                userProfileData.firstName=result.getString(result.getColumnIndex(COLUMN_NAME_USER_FIRSTNAME))
                userProfileData.lastName=result.getString(result.getColumnIndex(COLUMN_NAME_USER_LASTNAME))
                userProfileData.dateOfBirth=result.getString(result.getColumnIndex(COLUMN_NAME_DATE_OF_BIRTH))
                userProfileData.wohnort=result.getString(result.getColumnIndex(COLUMN_NAME_USER_WOHNORT))
                userProfileData.postalCode=result.getString(result.getColumnIndex(COLUMN_NAME_USER_POSTAL_CODE))
                userProfileData.street=result.getString(result.getColumnIndex(COLUMN_NAME_USER_STREET))
                list.add(userProfileData)

            }while (result.moveToNext())
        }

        result.close()
        db.close()

        return list
    }

}

