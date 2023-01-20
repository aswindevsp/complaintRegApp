package com.mgits.cms.ui.auth.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ExpandedDropDown(
    modifier: Modifier = Modifier,
    listOfItems: List<T>,
    isError: Boolean = false,
    readOnly: Boolean = false,
    placeholder: String = "Select Option",
    parentTextFieldCornerRadius: Dp = 12.dp,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    onDropDownItemSelected: (T) -> Unit = {},
    dropdownItem: @Composable (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable { mutableStateOf("") }


    Column() {
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange ={expanded = !expanded} ) {
            OutlinedTextField(
                singleLine = true,
                value = selectedOptionText,
                readOnly = true,
                onValueChange = {  },
                label = {
                    Text(
                        text = placeholder,
                        fontSize = 15.sp,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
                    .menuAnchor()
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                listOfItems.forEach {option->
                    DropdownMenuItem(
                        text = { Text(text = option.toString())},
                        onClick = {
                            expanded = !expanded
                            onDropDownItemSelected(option)
                            selectedOptionText = option.toString()
                        })
                }
            }
        }

    }
}