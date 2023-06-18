package dev.cisnux.jetcoffee.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.cisnux.jetcoffee.model.Menu
import dev.cisnux.jetcoffee.model.dummyMenu
import dev.cisnux.jetcoffee.ui.theme.JetCoffeeTheme

@Preview(showBackground = true)
@Composable
fun MenuItemPreview() {
    JetCoffeeTheme {
        MenuItem(menu = dummyMenu.first())
    }
}

@Composable
fun MenuItem(
    menu: Menu,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .width(140.dp)
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 8.dp))
    ) {
        Column {
            Image(
                painter = painterResource(id = menu.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 8.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = menu.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                    ),
                )
                Text(
                    text = menu.price,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}
