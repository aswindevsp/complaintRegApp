package com.mgits.complaintreg.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Complaints(
    var complaintId: String? = null,
    var complaintType: String? = null,
    var description: String? = null,
    var complainant: String? = null,
    var status: String? = null,
    var location: String? = null,
    var room: String? = null,
    var floor: String? = null,
    var userId: String? = null,
    var resolvedBy: String? = null,

    @ServerTimestamp
    var registeredTimeStamp: Timestamp? = null
)