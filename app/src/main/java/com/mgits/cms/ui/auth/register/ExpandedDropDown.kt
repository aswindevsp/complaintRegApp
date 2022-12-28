package com.mgits.cms.ui.auth.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ExpandedDropDown(
    modifier: Modifier = Modifier,
    listOfItems: List<T>,
    enable: Boolean = false,
    isError: Boolean = false,
    readOnly: Boolean = false,
    placeholder: String = "Select Option",
    openedIcon: ImageVector = Icons.Outlined.KeyboardArrowUp,
    closedIcon: ImageVector = Icons.Outlined.KeyboardArrowDown,
    parentTextFieldCornerRadius: Dp = 12.dp,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    onDropDownItemSelected: (T) -> Unit = {},
    dropdownItem: @Composable (T) -> Unit
) {
    var selectedOptionText by rememberSaveable { mutableStateOf("") }
    var searchedOption by rememberSaveable { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var filteredItems = mutableListOf<T>()

    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 530.dp
    val density = LocalDensity.current

    val maxHeight = remember(itemHeights.toMap()) {
        if (itemHeights.keys.toSet() != listOfItems.indices.toSet()) {
            // if we don't have all heights calculated yet, return default value
            return@remember baseHeight
        }
        val baseHeightInt = with(density) { baseHeight.toPx().toInt() }

        // top+bottom system padding
        var sum = with(density) { DropdownMenuVerticalPadding.toPx().toInt() } * 2
        for ((_, itemSize) in itemHeights.toSortedMap()) {
            sum += itemSize
            if (sum >= baseHeightInt) {
                return@remember with(density) { (sum - itemSize / 2).toDp() }
            }
        }
        // all items fit into base height
        baseHeight
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            readOnly = readOnly,
            modifier = modifier
                .clickable {
                    expanded = !expanded
                },
            colors = colors,
            value = selectedOptionText,
            isError = isError,
            enabled = enable,
            onValueChange = { selectedOptionText = it },
            placeholder = {
                Text(text = placeholder)
            },
            trailingIcon = {
                IconToggleButton(
                    checked = expanded,
                    onCheckedChange = {
                        expanded = it
                    }
                ) {
                    if (expanded) Icon(
                        imageVector = openedIcon,
                        contentDescription = null
                    ) else Icon(
                        imageVector = closedIcon,
                        contentDescription = null
                    )
                }
            },
            shape = RoundedCornerShape(parentTextFieldCornerRadius)
        )
        if (expanded) {
            DropdownMenu(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .requiredSizeIn(maxHeight = maxHeight),
                expanded = expanded,
                onDismissRequest = { expanded = false }) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        value = searchedOption,
                        onValueChange = { selectedSport ->
                            searchedOption = selectedSport
                            filteredItems = listOfItems.filter {
                                it.toString().contains(
                                    searchedOption,
                                    ignoreCase = true
                                )
                            }.toMutableList()
                        },
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                        },
                        placeholder = {
                            Text(text = "Search")
                        }
                    )

                    val items = if (filteredItems.isEmpty()) {
                        listOfItems
                    } else {
                        filteredItems
                    }

                    items.forEach { selectedItem ->
                        DropdownMenuItem(
                            text = { selectedItem },
                            onClick = {
                                selectedOptionText = selectedItem.toString()
                                onDropDownItemSelected(selectedItem)
                                searchedOption = ""
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

private val DropdownMenuVerticalPadding = 8.dp