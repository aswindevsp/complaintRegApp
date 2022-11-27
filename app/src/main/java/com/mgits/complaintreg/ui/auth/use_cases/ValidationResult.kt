package com.mgits.complaintreg.ui.auth.use_cases

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null

    )