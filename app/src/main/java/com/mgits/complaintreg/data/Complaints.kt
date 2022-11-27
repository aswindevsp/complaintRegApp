package com.mgits.complaintreg.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Complaints(
    var complaintId: String? = null,
    var complaintType: String? = null,
    var description: String? = null,
    var name: String? = null,
    var status: String? = null,
    var title: String? = null,
    var userId: String? = null,

    @ServerTimestamp
    var time: Timestamp? = null
)