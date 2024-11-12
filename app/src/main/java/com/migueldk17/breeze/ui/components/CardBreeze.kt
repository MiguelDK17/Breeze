package com.migueldk17.breeze.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons
import com.migueldk17.breeze.R
import com.migueldk17.breeze.ui.theme.BreezeTheme

@Composable
fun CardBreeze(
    imageVector: FaIconType
){
    var text by remember {
        mutableStateOf("")
    }
    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)

    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
           ) {
            FaIcon(imageVector, size = 48.dp)
            Spacer(Modifier.size(20.dp))
            Text("Internet")
            Spacer(modifier = Modifier.size(90.dp))
            OutlinedTextField(
                text, onValueChange = {value ->
                text = value
            },
                modifier = Modifier.size(width = 197.dp, height = 56.dp),)
        }
        Spacer(modifier = Modifier.size(10.dp))
    }
}
// <a href="https://br.freepik.com/icone/internet_4538777">Ícone de Freepik</a>
@Composable
@Preview(showBackground = false)
private fun Preview(){
    BreezeTheme {
        val iconGlobe = painterResource(R.drawable.icon_globe)
        CardBreeze(FaIcons.Globe)
    }
}