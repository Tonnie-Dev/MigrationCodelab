/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.plantdetail

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.google.samples.apps.sunflower.R
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.viewmodels.PlantDetailViewModel

@Composable
fun PlantDetailDescription(plantDetailViewModel: PlantDetailViewModel) {


    /*•	by is the property delegate syntax in Kotlin, it lets us
    automatically unwrap the State<Plant> from observeAsState
    into a regular  Plant */
    val plant by plantDetailViewModel.plant.observeAsState()

    //perform null-check on the returned value from liveData

    plant?.let { PlantDetailContent(plant = it) }
    /*Surface {
        Text(text = plant.toString())
    }*/
}

@Composable
fun PlantDetailContent(plant: Plant) {
    Surface() {

        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_normal))) {
            PlantName(name = plant.name)
            PlantWatering(wateringInterval = plant.wateringInterval)
        }

    }



}

@Composable
fun PlantName(name: String) {

    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier

            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.margin_small))
            .wrapContentWidth(Alignment.CenterHorizontally)
    )

}

@Preview(name = "PlantNamePreview")
@Composable
fun PreviewPlantName() {

    PlantName(name = "Apple")

}

@Composable
fun PlantWatering(wateringInterval: Int) {


    Column(modifier = Modifier.fillMaxWidth()) {
        // Same modifier used by both Texts
        val centerWithPaddingModifier = Modifier
            .padding(
                horizontal = dimensionResource(id = R.dimen.margin_small)
            )
            .align(alignment = Alignment.CenterHorizontally)

        //padding
        val normalPadding = dimensionResource(id = R.dimen.margin_normal)

        //Header Text
        Text(
            text = stringResource(id = R.string.watering_needs_prefix),
            color = MaterialTheme.colors.primaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = centerWithPaddingModifier.padding(top = normalPadding)
        )

        //Local Context Provides a Context that can be used by Android applications.
        val wateringIntervalText = LocalContext.current.resources.getQuantityString(R.plurals.watering_needs_suffix, wateringInterval, wateringInterval)
      
        //2nd Text

        Text(text = wateringIntervalText, modifier= centerWithPaddingModifier.padding(bottom = normalPadding))


    }

}


@Composable
fun PlantDescription(description:String) {


    val htmlDescription = remember(key1 = description) {

        HtmlCompat.fromHtml(description,HtmlCompat.FROM_HTML_MODE_COMPACT)
    }

}

@Preview
@Composable
private fun PlantDetailContentPreview() {
    val plant = Plant("id", "Apple", "description", 3, 30, "")
    MaterialTheme {
        PlantDetailContent(plant)
    }
}

@Preview(name = "WateringInterval")
@Composable
fun PlantWateringPreview() {
    MaterialTheme {
        PlantWatering(7)
    }
}

