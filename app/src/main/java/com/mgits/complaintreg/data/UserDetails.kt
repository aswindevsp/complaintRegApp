package com.mgits.complaintreg.data

data class UserDetails(
    val admin: Boolean = false,
    val adminType: String = "",
    val department: String = "",
    val email: String = "",
    val name: String = ""

)
