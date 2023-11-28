package cyou.ma.bartender.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import cyou.ma.bartender.R

val ralewayFontFamily = FontFamily(
  Font(R.font.raleway_black, FontWeight.Black),
  Font(R.font.raleway_bold, FontWeight.Bold),
  Font(R.font.raleway_italic, FontWeight.Normal, FontStyle.Italic),
  Font(R.font.raleway_medium, FontWeight.Medium),
  Font(R.font.raleway_regular, FontWeight.Normal)
)

// Set of Material typography styles to start with
val Typography = Typography(
  body1 = TextStyle(
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp
  ),
  caption = TextStyle(
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
  ),
  defaultFontFamily = ralewayFontFamily
)
