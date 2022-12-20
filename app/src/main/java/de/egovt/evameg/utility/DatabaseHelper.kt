package de.egovt.evameg.utility

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import android.widget.Toast
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_DATE_OF_BIRTH
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_FIRSTNAME
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_LASTNAME
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_POSTAL_CODE
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_STREET
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_WOHNORT
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.TABLE_NAME

private const val Tag="DataBaseHelper"
fun logging(){
    Log.e(Tag, "ERROR: a serious error like an app crash")
    Log.w(Tag, "WARN: warns about the potential for serious errors")
    Log.i(Tag, "INFO: reporting technical information, such as an operation succeeding")
    Log.d(Tag, "DataBaseHelper hat die DB : USER_DATS_TABLE erzeugt.")
    Log.v(Tag, "VERBOSE: more verbose than DEBUG logs")

}

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


//statement,that creates the table
private const val SQL_CREATE_ENTRIES="CREATE TABLE ${UserProfileDataContract.UserProfileDataEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
        "$COLUMN_NAME_USER_FIRSTNAME TEXT, " +
        "$COLUMN_NAME_USER_LASTNAME TEXT, " +
        "$COLUMN_NAME_DATE_OF_BIRTH 'DATE', "  +
        "$COLUMN_NAME_USER_WOHNORT TEXT, " +
        "$COLUMN_NAME_USER_POSTAL_CODE INTEGER, " +
        "$COLUMN_NAME_USER_STREET TEXT) "

//statement, that deletes the table
private const val SQL_DELETE_ENTRIES="DROP TABLE IF EXISTS ${UserProfileDataContract.UserProfileDataEntry.TABLE_NAME}"


class DbHelper(var context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        db?.execSQL(SQL_CREATE_ENTRIES)
        logging()

    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)


    }




    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "UserProfileData.db"

        private var instance: DbHelper? = null

        //Accessing database
        operator fun invoke(context: Context) = instance ?: DbHelper(context).also { instance = it }
    }



    fun insertUserData(userProfileData: UserProfileData) {
        //writing in database
        val db = this.writableDatabase
        //new map with values, column names are keys
        var values = ContentValues()
        values.put(COLUMN_NAME_USER_FIRSTNAME, userProfileData.firstName)
        values.put(COLUMN_NAME_USER_LASTNAME, userProfileData.lastName)
        values.put(COLUMN_NAME_DATE_OF_BIRTH, userProfileData.dateOfBirth)
        values.put(COLUMN_NAME_USER_WOHNORT, userProfileData.wohnort)
        values.put(COLUMN_NAME_USER_POSTAL_CODE, userProfileData.postalCode)
        values.put(COLUMN_NAME_USER_STREET, userProfileData.street)

        //new row insert,primary key value return
        var result =
            db.insert(UserProfileDataContract.UserProfileDataEntry.TABLE_NAME, null, values)
        if (result == 1.toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()

        }
    }
    @SuppressLint("Range")
    fun readUserData() :MutableList<UserProfileData>{
        var list: MutableList<UserProfileData> =ArrayList()
        val db= this.readableDatabase
        val query="SELECT * FROM $TABLE_NAME ORDER BY ${BaseColumns._ID} DESC LIMIT 1"
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



/*alt:

fun getUserData () {
    val db = this.readableDatabase
    val projection = arrayOf(
        BaseColumns._ID,
        COLUMN_NAME_USER_FIRSTNAME,
        COLUMN_NAME_USER_LASTNAME,
        COLUMN_NAME_DATE_OF_BIRTH,
        COLUMN_NAME_USER_WOHNORT,
        COLUMN_NAME_USER_POSTAL_CODE,
        COLUMN_NAME_USER_STREET
    )
    val selection = "${COLUMN_NAME_USER_LASTNAME}=?"
    val selectionArgs = arrayOf("Nachname")
    val sortOrder = "$COLUMN_NAME_USER_FIRSTNAME DESC"

    val cursor = db.query(
        UserProfileDataContract.UserProfileDataEntry.TABLE_NAME,  //table for query
        projection,                              //column array which will hopefully returned
        selection,                                 //columns for where clausel
        selectionArgs,                             //values for where clausel
        null,                               //would group the rows
        null,                              // would row groups filter
        sortOrder
    )
}
}


    val userIds = mutableListOf<Long>()
        with(cursor) {
            while (cursor.moveToNext()) {
                val user= getLong(getColumnIndexOrThrow(BaseColumns._ID))
                userIds.add(user)
            }
        }
        cursor.close()
        db.close()
    }

    override fun onDestroy(){
    dbHelper.close()
    super.onDestroy()
}*/
