package com.mgits.complaintreg.data

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Complaints(
    var complaintType: String? = null,
    var description: String? = null,
    var name: String? = null,
    var status: String? = null,
    var title: String? = null,
    var userId: String? = null,

    @ServerTimestamp
    var time: Date? = null
)