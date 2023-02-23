package de.egovt.evameg.utility

import android.content.ContentValues
import android.database.Cursor
import android.util.Log
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

interface DataStructure {}

data class UserProfileData (var firstName:String = "", var lastName:String = "", var dateOfBirth: String = "", var wohnort: String = "", var postalCode: String = "", var street: String = "") : DataStructure {
    var id: Int=1
}

data class Office (val name:String, val address:String, val type:String, val latitude:Double, val longitude:Double)  : DataStructure {

    constructor(name:String, address:String, type:String, latitude:Double, longitude:Double, map: MapView):this(name, address, type, latitude, longitude){

        marker = Marker(map)
        val location = GeoPoint(latitude, longitude)
        marker.position = location
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = name

    }

    var id: Int=1
    lateinit var marker: Marker
}

data class ProposalData (var proposalName:String = "", var category:String = "", var date: String = "", var status: String = "", var officeId: String = "",
 ) : DataStructure {

    var id: Int = 1
}

    /**
     * Map a Value to insert into the DB to the Columns where it is supposed to be inserted
     *
     * @param data to be inserted, either a Profile or a Office 0r a Proposal
     * @return the insertable data for the SQLLite DB in ContentValues format
     *
     * @author Celina Ludwings, Niklas Herzog
     */
    fun mapInValues(data: DataStructure): ContentValues {

        val values = ContentValues()


        if (data is UserProfileData) {

            values.put(UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_FIRSTNAME, data.firstName)
            values.put(UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_LASTNAME, data.lastName)
            values.put(UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_DATE_OF_BIRTH, data.dateOfBirth)
            values.put(UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_WOHNORT, data.wohnort)
            values.put(UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_POSTAL_CODE, data.postalCode)
            values.put(UserProfileDataContract.UserProfileDataEntry.COLUMN_NAME_USER_STREET, data.street)

        } else if (data is Office) {

            values.put(OfficesDataContract.OfficeDataEntry.COLUMN_NAME_NAME, data.name)
            values.put(OfficesDataContract.OfficeDataEntry.COLUMN_NAME_TYPE, data.type)
            values.put(OfficesDataContract.OfficeDataEntry.COLUMN_NAME_ADDRESS, data.address)
            values.put(OfficesDataContract.OfficeDataEntry.COLUMN_NAME_LAT, data.latitude)
            values.put(OfficesDataContract.OfficeDataEntry.COLUMN_NAME_LONG, data.longitude)

        } else if (data is ProposalData) {

            values.put(ProposalDataContract.ProposalDataEntry.COLUMN_NAME_PROPOSAL_NAME, data.proposalName)
            values.put(ProposalDataContract.ProposalDataEntry.COLUMN_NAME_PROPOSAL_NAME, data.category)
            values.put(ProposalDataContract.ProposalDataEntry.COLUMN_NAME_PROPOSAL_NAME, data.date)
            values.put(ProposalDataContract.ProposalDataEntry.COLUMN_NAME_PROPOSAL_NAME, data.status)
            values.put(ProposalDataContract.ProposalDataEntry.COLUMN_NAME_PROPOSAL_NAME, data.officeId)



        } else {

            Log.w("DB", "unrecognized Data is trying to be inserted into the function")

        }

        return values
    }

    /**
     * Map the values returned from a query to Objects usable for Devs
     *
     * @param data returned from the db as a cursor
     * @param type of data - can be "profile" or "office" or "proposal"
     * @return List of the wanted DataStructure
     *
     * @author Celina Ludwings, Niklas Herzog
     */
    fun mapOutValues(data: Cursor, type: String): MutableList<DataStructure> {

        val values: MutableList<DataStructure>

        // CREATE VARIABLE AS TYPE OF QUERY
        when (type) {

            // if "profile" is the type, make the internal type of values a array of UserProfileData and so on....
            "profile" -> {
                values = mutableListOf<UserProfileData>() as MutableList<DataStructure>
            }

            "office" -> {
                values = mutableListOf<Office>() as MutableList<DataStructure>
            }

            "proposal" -> {
                values = mutableListOf<ProposalData>() as MutableList<DataStructure>
            }

            else -> {
                Log.w("DB", "unrecognised type was tried to be read from the data")
                return null!!
            }
        }

        // iterate the cursor to read the data if moveToFirst returns true
        if (data.moveToFirst()) {

            do {

                when (type) {

                    // if "profile" is the type, make the internal type of values a array of UserProfileData and so on....
                    "profile" -> {

                        // Map to constructor and Data objects
                        // rowid, COLUMN_NAME_USER_FIRSTNAME, COLUMN_NAME_USER_LASTNAME, COLUMN_NAME_DATE_OF_BIRTH, COLUMN_NAME_USER_WOHNORT, COLUMN_NAME_USER_POSTAL_CODE, COLUMN_NAME_USER_STREET
                        val userProfileData = UserProfileData(
                            data.getString(1),
                            data.getString(2),
                            data.getString(3),
                            data.getString(4),
                            data.getString(5),
                            data.getString(6)
                        )
                        userProfileData.id = data.getInt(0)

                        values.add(userProfileData)

                    }

                    "office" -> {

                        // BaseColumns._ID, COLUMN_NAME_NAME, ADDRESS, COLUMN_NAME_TYPE, COLUMN_NAME_LAT, COLUMN_NAME_LONG
                        val officeData = Office(
                            data.getString(1),
                            data.getString(2),
                            data.getString(3),
                            data.getDouble(4),
                            data.getDouble(5)
                        )
                        officeData.id = data.getInt(0)

                        values.add(officeData)
                    }

                    "proposal" -> {

                        // BaseColumns._ID, COLUMN_NAME_NAME, ADDRESS, COLUMN_NAME_TYPE, COLUMN_NAME_LAT, COLUMN_NAME_LONG
                        val proposalData = ProposalData(
                            data.getString(1),
                            data.getString(2),
                            data.getString(3),
                            data.getString(4),
                            //data.getString(5)
                        )
                        proposalData.id = data.getInt(0)

                        values.add(proposalData)
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
