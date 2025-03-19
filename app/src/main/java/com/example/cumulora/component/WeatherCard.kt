package com.example.cumulora.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cumulora.R
import com.example.cumulora.utils.weatherIcons

@Composable
fun WeatherCard ( title: String, iconId: Int,color: Color = Color.DarkGray, composable: @Composable
    ()->
Unit,) {

    Surface(shape = RoundedCornerShape(20.dp ), color = color) {
        Column ( modifier = Modifier.padding(8.dp).height(160.dp)){
            Row (verticalAlignment = Alignment.Bottom){
                Image(modifier = Modifier.size(24.dp), painter = painterResource(id = iconId),
                    contentDescription = "")
                Spacer(Modifier.width(8.dp))
                Text(title)
            }
            Spacer(Modifier.height(8.dp))
            composable()

//            Box (modifier = Modifier.fillMaxSize()){
//            }
        }
    }

}