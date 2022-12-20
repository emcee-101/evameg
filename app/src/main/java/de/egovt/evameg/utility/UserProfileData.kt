package de.egovt.evameg.utility

class UserProfileData {
    var id: Int=0
    var firstName: String=" "
    var lastName: String=" "
    var dateOfBirth: String=" "
    var wohnort: String=" "
    var postalCode: String=" "
    var street: String=""

    constructor(
        firstName:String, lastName:String, dateOfBirth: String,
        wohnort: String, postalCode: String, street: String){
        this.id=id
        this.firstName=firstName
        this.lastName=lastName
        this.dateOfBirth=dateOfBirth
        this.wohnort=wohnort
        this.postalCode=postalCode
        this.street=street

    }
    constructor()

}


/*
   override fun toString(): String {
        return "UserProfileData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", DateOfBirth='" + dateOfBirth + '\'' +
                ", wohnort='" + wohnort + '\'' +
                ", postalCode=" + postalCode +
                ", street='" + street + '\'' +
                '}'
    }*/