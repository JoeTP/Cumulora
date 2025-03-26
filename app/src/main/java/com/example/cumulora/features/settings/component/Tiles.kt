package com.example.cumulora.features.settings.component

import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ListTile(title: String, leadingIcon: ImageVector) {
//    Column {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(70.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(title)
//        }
//        HorizontalDivider()
//    }
    ListItem(
        leadingContent = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = ""
            )
        },
        headlineContent = {
            Text(title)
        },
    )
//    HorizontalDivider()

}