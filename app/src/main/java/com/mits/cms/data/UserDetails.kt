package com.mits.cms.data

data class UserDetails(
    val admin: Boolean = false,
    val adminType: String = "",
    val department: String = "",
    val email: String = "",
    val name: String = "",
    val phoneNo: String = ""

)
