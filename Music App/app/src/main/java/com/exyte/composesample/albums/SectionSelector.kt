package com.exyte.composesample.albums

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.exyte.composesample.ui.theme.PlayerTheme

/*
 * Created by Exyte on 11.10.2021.
 */

@Stable
@JvmInline
value class Section(val title: String)

@Composable
fun SectionSelector(modifier: Modifier = Modifier, onSelect: (Section) -> Unit = {}) {
    val sectionTitles = remember {
        listOf(
            Section("Comments"),
            Section("Popular"),
        )
    }
    var currentSelection by remember { mutableStateOf(sectionTitles.last()) }

    SectionSelector(
        modifier = modifier,
        sections = sectionTitles,
        selection = currentSelection,
        onClick = { selection ->
            if (selection != currentSelection) {
                currentSelection = selection
                onSelect(selection)
            }
        })
}

@Composable
fun SectionSelector(
    modifier: Modifier = Modifier,
    sections: Collection<Section>,
    selection: Section,
    onClick: (Section) -> Unit = {},
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, color = MaterialTheme.colors.secondary)
    ) {
        Row {
            sections.forEach { item ->
                val isSelected = item == selection
                SectionItem(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable { onClick(item) },
                    title = item.title,
                    isSelected = isSelected,
                )
            }
        }
    }
}

@Composable
fun SectionItem(modifier: Modifier, title: String, isSelected: Boolean) {
    val bgColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.secondary else MaterialTheme.colors.surface,
        animationSpec = tween(200)
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.surface else MaterialTheme.colors.secondary,
        animationSpec = tween(200)
    )

    Box(
        modifier = modifier.background(bgColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = title,
            color = textColor,
            style = MaterialTheme.typography.button,
            fontWeight = FontWeight.Light,
        )
    }
}

@Composable
@Preview
private fun PreviewSectionSelector() {
    PlayerTheme(darkTheme = false) {
        SectionSelector(
            modifier = Modifier
                .width(300.dp)
                .height(50.dp),
        )
    }
}