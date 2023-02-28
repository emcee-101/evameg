package de.egovt.evameg.utility.DB

import de.egovt.evameg.utility.Office
import de.egovt.evameg.utility.ProposalData
import de.egovt.evameg.utility.UserProfileData


val testOffices = listOf<Office>(

    Office("Standesamt Erfurt", "Große Arche 6, 99084 Erfurt", "standesamt", 50.976949, 11.026396),
    Office("Finanzamt Erfurt", "August-Röbling-Straße 10, 99091 Erfurt", "finanzamt", 51.018121, 11.012042),
    Office("Sozialamt Erfurt", "Juri-Gagarin-Ring 150, 99084 Erfurt", "sozialamt", 50.982732, 11.034211),
    Office("Ausländerbehörde Erfurt", "Bürgermeister-Wagner-Straße 1, 99084 Erfurt", "auslaenderamt", 50.974665, 11.037730)

)

val testPerson = UserProfileData("Robert", "Käsemann", "07.08.1987", "Solingen", "42651", "Kaiser-Wilhelm Strasse 3")

val testProposal = ProposalData("Heiratsantrag","12.12.2023","In Arbeit")

