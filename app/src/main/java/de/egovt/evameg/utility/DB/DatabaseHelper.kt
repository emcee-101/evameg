package de.egovt.evameg.utility.DB


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
import de.egovt.evameg.utility.ProposalDataContract.ProposalDataEntry.COLUMN_NAME_CATEGORY
import de.egovt.evameg.utility.ProposalDataContract.ProposalDataEntry.COLUMN_NAME_DATE
import de.egovt.evameg.utility.ProposalDataContract.ProposalDataEntry.COLUMN_NAME_STATUS
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_DATE_OF_BIRTH
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_FIRSTNAME
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_LASTNAME
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_POSTAL_CODE
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_STREET
import de.egovt.evameg.utility.UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_WOHNORT




/**
 * Handles everything Database related.
 *
 * @param context of Fragment or Activity running it
 *
 * @author Celina Ludwigs, Niklas Herzog
 */
class DbHelper(var context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(SQL_CREATE_ENTRIES_USER)
        db.execSQL(SQL_CREATE_ENTRIES_OFFICES)
        db.execSQL(SQL_CREATE_ENTRIES_PROPOSAL)
        Log.i("db", "Created the Tables in the DB")


        insertOfficeData(testOffices, db)
        insertUserData(testPerson, db)


    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        db.execSQL(SQL_DELETE_ENTRIES_USER)
        db.execSQL(SQL_DELETE_ENTRIES_OFFICES)
        db.execSQL(SQL_DELETE_ENTRIES_PROPOSAL)
        onCreate(db)


    }


    companion object {
        const val DATABASE_VERSION = 8
        const val DATABASE_NAME = "EVAMEG_DATA_DB"

        // TODO FIX MEMORY LEAK, OUR SHIP IS FLOODING WITH WATER, WE ARE SINKING AAAAAAAAAAAAAAHHHHHHHHHHHHHHHHH HEEEEEEEEEEEEEEEEEELP!!!!!!!!!!!!
        private var instance: DbHelper? = null

        //Accessing database
        operator fun invoke(context: Context) = instance ?: DbHelper(context).also { instance = it }
    }


    /**
     * Add user Date to User DB
     *
     * @param userProfileData object to be insertedd
     * @param db_ref is an optional value that can be used if the reference to the SQLliteDB if that is known
     *
     * @author Celina Ludwigs, Niklas Herzog
     */
    fun insertUserData(userProfileData: UserProfileData, db_ref: SQLiteDatabase? = null) {

        var db : SQLiteDatabase

        // enable to optionally pass a DB in (useful for testdata onCreate of a new DB)
        if(db_ref == null)
            db = this.writableDatabase
        else
            db = db_ref

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

    /**
     * Add Offices to Office DB
     *
     * @param list of offices to be inserted
     * @param db_ref is an optional value that can be used if the reference to the SQLliteDB if that is known
     *
     * @author Niklas Herzog
     */
    fun insertOfficeData(officeData: List<Office>, db_ref: SQLiteDatabase? = null) {

        var db : SQLiteDatabase

        // enable to optionally pass a DB in (useful for testdata onCreate of a new DB)
        if(db_ref == null)
            db = this.writableDatabase
        else
            db = db_ref

        for(instance:Office in officeData){

            val values = mapInValues(instance)

            var result = db.insert(OfficesDataContract.OfficeDataEntry.TABLE_NAME, null, values)

        }

    }

    /**
     * Add Porposal to DB
     *
     * @param list of proposals to be inserted
     * @param db_ref is an optional value that can be used if the reference to the SQLliteDB if that is known
     *
     * @author Celina Ludwigs
     */
    fun insertProposalData(proposalData: ProposalData, db_ref: SQLiteDatabase? = null) {

        var db : SQLiteDatabase

        // enable to optionally pass a DB in (useful for testdata onCreate of a new DB)
        if(db_ref == null)
            db = this.writableDatabase
        else
            db = db_ref


        val values = mapInValues(proposalData)

        var result = db.insert(ProposalDataContract.ProposalDataEntry.TABLE_NAME, null, values)



    }


    /**
     * Return last User entry in DB
     *
     * @return list with 1 user entry
     *
     * @author Celina Ludwigs, Niklas Herzog
     */
    fun readUserData() : MutableList<UserProfileData> {

        val db = this.readableDatabase


        // NEEDS TO MATCH THE CONSTRUCTOR OF THE OBJECT IT IS SUPPOSED TO REACH
        val queryObjects : Array<String> = arrayOf("rowid", COLUMN_NAME_USER_FIRSTNAME, COLUMN_NAME_USER_LASTNAME, COLUMN_NAME_DATE_OF_BIRTH, COLUMN_NAME_USER_WOHNORT, COLUMN_NAME_USER_POSTAL_CODE, COLUMN_NAME_USER_STREET)


        val query="SELECT ${queryObjects.joinToString(separator = ",")} " +
                "FROM ${UserProfileDataContract.UserProfileDataEntry.TABLE_NAME} ORDER BY ${BaseColumns._ID} DESC LIMIT 1"

        Log.i("DB", "SQL call with following query is executed: $query")

        val resultCursor = db.rawQuery(query,null)

        val list = mapOutValues(resultCursor, "profile") as MutableList<UserProfileData>

        db.close()

        return list
    }

    /**
     * Return All Offices from DB
     *
     * @return list of found offices
     *
     * @author Niklas Herzog
     */
    fun readOfficeData() : List<Office> {

        // NEEDS TO MATCH THE CONSTRUCTOR OF THE OBJECT IT IS SUPPOSED TO REACH
        // -> val id:String, val name:String, val address:String, val type:String, val latitude:Double, val longitude:Double
        val queryObjects : Array<String> = arrayOf("rowid", COLUMN_NAME_NAME,  COLUMN_NAME_ADDRESS, COLUMN_NAME_TYPE,  COLUMN_NAME_LAT, COLUMN_NAME_LONG)

        val query="SELECT ${ queryObjects.joinToString(separator = ",") } FROM ${ OfficesDataContract.OfficeDataEntry.TABLE_NAME } "

        return runOfficeQuery(query)

    }

    /**
     * Return Offices from DB
     *
     * @param type of Office
     * @return list of found offices
     *
     * @author Niklas Herzog
     */
    fun readOfficeData(type:String) : List<Office> {

        // NEEDS TO MATCH THE CONSTRUCTOR OF THE OBJECT IT IS SUPPOSED TO REACH
        // -> val id:String, val name:String, val address:String, val type:String, val latitude:Double, val longitude:Double
        val queryObjects : Array<String> = arrayOf("rowid", COLUMN_NAME_NAME,  COLUMN_NAME_ADDRESS, COLUMN_NAME_TYPE,  COLUMN_NAME_LAT, COLUMN_NAME_LONG)

        val query="SELECT ${ queryObjects.joinToString(separator = ",") } FROM ${ OfficesDataContract.OfficeDataEntry.TABLE_NAME } " +
                "WHERE $COLUMN_NAME_TYPE = \'$type\'"

        return runOfficeQuery(query)

    }

    /**
     * Return Offices from DB
     *
     * @param ids of Offices to be inquired about
     * @return list of found offices
     *
     * @author Niklas Herzog
     */
    fun readOfficeData(ids:Array<String>) : List<Office> {


        // NEEDS TO MATCH THE CONSTRUCTOR OF THE OBJECT IT IS SUPPOSED TO REACH
        // -> val id:String, val name:String, val address:String, val type:String, val latitude:Double, val longitude:Double
        val queryObjects : Array<String> = arrayOf("rowid", COLUMN_NAME_NAME,  COLUMN_NAME_ADDRESS, COLUMN_NAME_TYPE,  COLUMN_NAME_LAT, COLUMN_NAME_LONG)


        val query="SELECT ${ queryObjects.joinToString(separator = ",") } FROM ${ OfficesDataContract.OfficeDataEntry.TABLE_NAME } " +
                "WHERE ${ BaseColumns._ID }=${ ids.joinToString(separator = " OR ${BaseColumns._ID}=") }"


        return runOfficeQuery(query)
    }

    /**
     * Process Queries for the Data Object "Office"
     *
     * Run the Query, call the mapper for mapping the values to the objects and return the finished List
     *
     * @param query to be ran
     * @return list of found offices
     *
     * @author Niklas Herzog
     */
    private fun runOfficeQuery(query : String) : List<Office> {

        val db = this.readableDatabase

        Log.i("DB", "SQL call with following query is executed: $query")

        val officesCursor:Cursor = db.rawQuery(query, null)

        val list = mapOutValues(officesCursor, "office") as List<Office>

        db.close()

        return list
    }


    /**
     * Read all Proposals
     *
     * @return list of found proposal
     *
     * @author Celina Ludwigs
     */
    fun readProposalData() : MutableList<ProposalData> {

        val db = this.readableDatabase


        // NEEDS TO MATCH THE CONSTRUCTOR OF THE OBJECT IT IS SUPPOSED TO REACH
        val queryObjects : Array<String> = arrayOf("rowid", COLUMN_NAME_CATEGORY, COLUMN_NAME_DATE, COLUMN_NAME_STATUS
            )

        val query="SELECT ${queryObjects.joinToString(separator = ",")} " +
                "FROM ${ProposalDataContract.ProposalDataEntry.TABLE_NAME} ORDER BY ${BaseColumns._ID} DESC"

        Log.i("DB", "SQL call with following query is executed: $query")

        val resultCursor = db.rawQuery(query,null)

        val list = mapOutValues(resultCursor, "proposal") as MutableList<ProposalData>

        db.close()

        return list
    }

}

