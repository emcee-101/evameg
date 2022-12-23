package de.egovt.evameg.utility

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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


// Map the Values for Input in DB to their collumns
private fun mapInValues(data:DataStructure):ContentValues {

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

// Map the Values for Output out of the DB to their collumns
private fun mapOutValues(data: Cursor, type: String) : MutableList<DataStructure>? {

    var values:MutableList<DataStructure>?

    // CREATE VARIABLE AS TYPE OF QUERY
    when (type){

        // if "profile" is the type, make the internal type of values a array of UserProfileData and so on....
        "profile" -> {
            values = arrayOf<UserProfileData>() as MutableList<DataStructure>
        }

        "office" -> {
            values = arrayOf<Office>() as MutableList<DataStructure>
        }

        else -> {
            Log.w("DB", "unrecognised type was tried to be read from the data")
            return null!!
        }
    }

    // iterate the cursor to read the data
    if(data.moveToFirst()){

        do {


            var userProfileData = UserProfileData(

                // TODO MAKE COMMUNINDEX AVAILABLE FROM THE QUERY ON AND THUS GENERIC-ISH
                data.getString(data.getColumnIndex(COLUMN_NAME_USER_FIRSTNAME)),
                data.getString(data.getColumnIndex(COLUMN_NAME_USER_LASTNAME)),
                data.getString(data.getColumnIndex(COLUMN_NAME_DATE_OF_BIRTH)),
                data.getString(data.getColumnIndex(COLUMN_NAME_USER_WOHNORT)),
                data.getString(data.getColumnIndex(COLUMN_NAME_USER_POSTAL_CODE)),
                data.getString(data.getColumnIndex(COLUMN_NAME_USER_STREET))

            )

            userProfileData.id = data.getString(data.getColumnIndex(BaseColumns._ID)).toInt()
            values.add(userProfileData)


        } while (data.moveToNext())

    }
    data.close()


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
        val values = mapInValues(userProfileData)

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
        val values = mapInValues(officeData)

        var result = db.insert(OfficesDataContract.OfficeDataEntry.TABLE_NAME, null, values)

        //TODO verify this somehow
    }


    @SuppressLint("Range")
    fun readUserData() :MutableList<UserProfileData> {

        var list: MutableList<UserProfileData> =ArrayList()
        val db= this.readableDatabase

        // TODO ADD CORRECT QUERY WITH VALUES TO BE ASKED FOR
        val query="SELECT * FROM $TABLE_NAME ORDER BY ${BaseColumns._ID} DESC LIMIT 1" //vorher nach Ids sortiert absteigend limit 1 , mit nachnamen*/
        //limit vor order by ?
        val result=db.rawQuery(query,null)//statt where and order by in selection args?
        if (result.moveToLast()){

            do {

                var userProfileData= UserProfileData(

                    result.getString(result.getColumnIndex(COLUMN_NAME_USER_FIRSTNAME)),
                    result.getString(result.getColumnIndex(COLUMN_NAME_USER_LASTNAME)),
                    result.getString(result.getColumnIndex(COLUMN_NAME_DATE_OF_BIRTH)),
                    result.getString(result.getColumnIndex(COLUMN_NAME_USER_WOHNORT)),
                    result.getString(result.getColumnIndex(COLUMN_NAME_USER_POSTAL_CODE)),
                    result.getString(result.getColumnIndex(COLUMN_NAME_USER_STREET))

                )

                userProfileData.id=result.getString(result.getColumnIndex(BaseColumns._ID)).toInt()
                list.add(userProfileData)

            }while (result.moveToNext())
        }

        result.close()
        db.close()

        return list
    }

    fun readOfficeData(ids:Array<String>) : Array<Office> {

        val db = this.readableDatabase

        // TODO update to correct query
        val officesCursor:Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        return mapOutValues(officesCursor, "office") as Array<Office>

    }



}

