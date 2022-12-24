package de.egovt.evameg.utility.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
import de.egovt.evameg.utility.*
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



// Map the Values for Input in DB to their collumns
private fun mapInValues(data:DataStructure):ContentValues {

    val values = ContentValues()


    if(data is UserProfileData){

        values.put(COLUMN_NAME_USER_FIRSTNAME, data.firstName)
        values.put(COLUMN_NAME_USER_LASTNAME, data.lastName)
        values.put(COLUMN_NAME_DATE_OF_BIRTH, data.dateOfBirth)
        values.put(COLUMN_NAME_USER_WOHNORT, data.wohnort)
        values.put(COLUMN_NAME_USER_POSTAL_CODE, data.postalCode)
        values.put(COLUMN_NAME_USER_STREET, data.street)

    } else if (data is Office){

        values.put(COLUMN_NAME_NAME, data.name)
        values.put(COLUMN_NAME_TYPE, data.type)
        values.put(COLUMN_NAME_ADDRESS, data.address)
        values.put(COLUMN_NAME_LAT, data.latitude)
        values.put(COLUMN_NAME_LONG, data.longitude)

    } else {

        Log.w("DB", "unrecognized Data is trying to be inserted into the function")

    }

    return values
}

// Map the Values for Output out of the DB to their collumns
private fun mapOutValues(data: Cursor, type: String) : MutableList<DataStructure> {

    val values: MutableList<DataStructure>

    // CREATE VARIABLE AS TYPE OF QUERY
    when (type){

        // if "profile" is the type, make the internal type of values a array of UserProfileData and so on....
        "profile" -> {
            values = mutableListOf<UserProfileData>() as MutableList<DataStructure>
        }

        "office" -> {
            values = mutableListOf<Office>() as MutableList<DataStructure>
        }

        else -> {
            Log.w("DB", "unrecognised type was tried to be read from the data")
            return null!!
        }
    }

    // iterate the cursor to read the data if moveToFirst returns true
    if(data.moveToFirst()){

        do {

            when (type){

                // if "profile" is the type, make the internal type of values a array of UserProfileData and so on....
                "profile" -> {

                                            // Thats why "queryObjects" in the read...Data() functions needs to match the constructors
                                            // COLUMN_NAME_USER_FIRSTNAME, COLUMN_NAME_USER_LASTNAME, COLUMN_NAME_DATE_OF_BIRTH, COLUMN_NAME_USER_WOHNORT, COLUMN_NAME_USER_POSTAL_CODE, COLUMN_NAME_USER_STREET
                    val userProfileData = UserProfileData(data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getString(4), data.getString(5))

                    values.add(userProfileData)


                }

                "office" -> {

                                            // BaseColumns._ID, COLUMN_NAME_NAME, ADDRESS, COLUMN_NAME_TYPE, COLUMN_NAME_LAT, COLUMN_NAME_LONG
                    val officeData = Office(data.getString(0), data.getString(1), data.getString(2), data.getString(3), data.getDouble(4), data.getDouble(5))

                    values.add(officeData)
                }

                else -> {
                    Log.w("DB", "unrecognised type was tried to be read from the data")
                    return null!!
                }
            }

        // while theres still data
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

        // TODO FIX MEMORY LEAK, OUR SHIP IS FLOODING WITH WATER, WE ARE SINKING AAAAAAAAAAAAAAHHHHHHHHHHHHHHHHH HEEEEEEEEEEEEEEEEEELP!!!!!!!!!!!!
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
        val result = db.insert(UserProfileDataContract.UserProfileDataEntry.TABLE_NAME, null, values)

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



    fun readUserData() : MutableList<UserProfileData> {

        val db = this.readableDatabase


        // NEEDS TO MATCH THE CONSTRUCTOR OF THE OBJECT IT IS SUPPOSED TO REACH
        val queryObjects : Array<String> = arrayOf(COLUMN_NAME_USER_FIRSTNAME, COLUMN_NAME_USER_LASTNAME, COLUMN_NAME_DATE_OF_BIRTH, COLUMN_NAME_USER_WOHNORT, COLUMN_NAME_USER_POSTAL_CODE, COLUMN_NAME_USER_STREET)


        val query="SELECT ${queryObjects.joinToString(separator = ",")} " +
                "FROM ${UserProfileDataContract.UserProfileDataEntry.TABLE_NAME} ORDER BY ${BaseColumns._ID} DESC LIMIT 1"

        Log.i("DB", "SQL call with following query is executed: $query")

        val resultCursor = db.rawQuery(query,null)

        val list = mapOutValues(resultCursor, "profile") as MutableList<UserProfileData>

        db.close()

        return list
    }

    // TODO test this function fully - it isnt necesssary atm and does not keep the app from running
    fun readOfficeData(ids:Array<String>) : List<Office> {

        val db = this.readableDatabase


        // NEEDS TO MATCH THE CONSTRUCTOR OF THE OBJECT IT IS SUPPOSED TO REACH
        // -> val id:String, val name:String, val address:String, val type:String, val latitude:Double, val longitude:Double
        val queryObjects : Array<String> = arrayOf("rowid", COLUMN_NAME_NAME,  COLUMN_NAME_ADDRESS, COLUMN_NAME_TYPE,  COLUMN_NAME_LAT, COLUMN_NAME_LONG)


        val query="SELECT ${ queryObjects.joinToString(separator = ",") } FROM ${ OfficesDataContract.OfficeDataEntry.TABLE_NAME } " +
                "WHERE ${ BaseColumns._ID }=${ ids.joinToString(separator = " OR ${BaseColumns._ID}=") }"

        Log.i("DB", "SQL call with following query is executed: $query")

        val officesCursor:Cursor = db.rawQuery(query, null)

        val list = mapOutValues(officesCursor, "office") as List<Office>

        db.close()

        return list
    }



}

