package cyou.ma.bartender.ui.ingredients

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cyou.ma.bartender.R
import cyou.ma.bartender.api.model.MixerDrink
import cyou.ma.bartender.ui.cocktailDetail.TagChip
import com.google.accompanist.coil.rememberCoilPainter

@ExperimentalAnimationApi
@Composable
fun InlineMixerDrinks(
  foundMixers: List<MixerDrink>,
  selectedMixers: Set<String>,
  onMixerDrinkClicked: (MixerDrink) -> Unit
) {
  AnimatedVisibility(
    visible = foundMixers.isNotEmpty() && selectedMixers.isNotEmpty(),
    enter = expandVertically(),
    exit = shrinkVertically()
  ) {
    LazyRow(Modifier.fillMaxWidth()) {
      items(foundMixers) {
        InlineMixerDrinkCard(mixerDrink = it, onMixerDrinkClicked)
      }
    }
  }
}


@Composable
fun InlineMixerDrinkCard(mixerDrink: MixerDrink, onMixerDrinkClicked: (MixerDrink) -> Unit) {
  val painter = rememberCoilPainter(request = mixerDrink.thumbnail)

  Surface(
    modifier = Modifier
      .padding(12.dp)
      .size(80.dp)
      .clickable {
        onMixerDrinkClicked(mixerDrink)
      },
    shape = RoundedCornerShape(8.dp)
  ) {
    Image(
      painter = painter,
      contentDescription = mixerDrink.name,
      contentScale = ContentScale.Crop
    )
  }
}

@ExperimentalAnimationApi
@Composable
fun MixerChipGroup(
  mixers: Set<String>,
  onRemoveMixer: (String) -> Unit,
  onClearMixers: () -> Unit
) {
  AnimatedVisibility(
    visible = mixers.isNotEmpty(),
    enter = expandVertically(),
    exit = shrinkVertically()
  ) {
    Column(Modifier.padding(start = 16.dp)) {
      Text(
        stringResource(id = R.string.mixer_search),
        fontWeight = FontWeight.Black,
        modifier = Modifier.padding(top = 12.dp)
      )
      Row(
        Modifier
          .fillMaxWidth()
          .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
      ) {

        LazyRow(
          Modifier
            .weight(1f)
            .align(Alignment.CenterVertically)
        ) {
          items(mixers.toList()) {
            TagChip(
              text = it,
              modifier = Modifier.clickable {
                onRemoveMixer(it)
              }
            )
            Spacer(Modifier.padding(4.dp))
          }
        }
        IconButton(
          onClick = {
            onClearMixers()
          },
          modifier = Modifier.padding(end = 16.dp)
        ) {
          Icon(imageVector = Icons.Default.Close, null)
        }
      }
    }

  }
}
