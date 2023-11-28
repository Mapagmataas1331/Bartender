package cyou.ma.bartender.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
  primary = Brown500,
  primaryVariant = Pink700,
  secondary = Pink200,
  onPrimary = DarkBlueDark,
  onSecondary = DarkBlueDark
)

private val LightColorPalette = lightColors(
  primary = Brown500,
  primaryVariant = Pink700,
  secondary = Pink200,
  onPrimary = DarkBlue,
  onSecondary = DarkBlue
)

@Composable
fun BartenderTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
  val colors = if (darkTheme) {
    DarkColorPalette
  } else {
    LightColorPalette
  }

  MaterialTheme(
    colors = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}