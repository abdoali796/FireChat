package com.abdoali.firebasetest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abdoali.firebasetest.dataLayer.Friends
import com.abdoali.firebasetest.dataLayer.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendItem(
    friends: Friends , clickAction: () -> Unit

) {
    Card(onClick = clickAction , modifier = Modifier.fillMaxWidth()) {
        val rtfArrangement =
            if (friends.readLastMass) Arrangement.Start else Arrangement.SpaceBetween
        Row(
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = rtfArrangement ,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row() {
                AsyncImage(
                    model = friends.picture ,
                    contentDescription = null ,
                    contentScale = ContentScale.Crop ,
                    modifier = Modifier
                        .padding(10.dp)
                        .size(45.dp)
                        .border(BorderStroke(0.2.dp , Color.Black) , CircleShape)
                        .clip(CircleShape) ,
                    placeholder = painterResource(com.airbnb.lottie.R.drawable.abc_ic_search_api_material)

                )

                Column() {
                    friends.nikeName?.let {
                        Text(
                            text = it , style = MaterialTheme.typography.titleLarge
                        )
                    }
                    friends.lastMass?.let {
                        Text(
                            text = it , style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
            }
            if (! friends.readLastMass) {
                Icon(
                    Icons.Default.NewReleases ,
                    "new Massage" ,
                    tint = Color.Green ,
                    modifier = Modifier.padding(end = 24.dp)
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(
    user: User ,


    clickAction: () -> Unit
) {
    Card(
        onClick = clickAction , modifier = Modifier
            .fillMaxWidth()

    ) {


        Row(
            Modifier


        ) {
            AsyncImage(
                model = user.picture ,
                contentDescription = null ,
                contentScale = ContentScale.Crop ,
                modifier = Modifier
                    .padding(10.dp)
                    .size(45.dp)
                    .border(BorderStroke(0.2.dp , Color.Black) , CircleShape)
                    .clip(CircleShape) ,
                placeholder = painterResource(com.airbnb.lottie.R.drawable.abc_ic_search_api_material)

            )
            Column() {

                user.nikeName?.let { Text(text = it , style = MaterialTheme.typography.titleLarge) }
                user.job?.let { Text(text = it , style = MaterialTheme.typography.titleSmall) }


            }

        }
    }
}


@Composable
fun UserCard(
    user: User , clickAction: () -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium ,
        modifier = Modifier
            .padding(vertical = 2.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .clickable(onClick = clickAction)
    ) {
        Column(
            horizontalAlignment = Alignment.Start
, modifier = Modifier.padding( horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,


            ) {
                Column() {
                    user.nikeName?.let {
                        Text(
                            text = it , style = MaterialTheme.typography.headlineSmall
                        )
                    }
                    user.job?.let {
                        Text(
                            text = it , style = MaterialTheme.typography.titleLarge
                        )
                    }
                }

                AsyncImage(
                    model = user.picture ,
                    contentDescription = null ,
                    contentScale = ContentScale.Crop ,
                    modifier = Modifier
                        .padding(7.dp)
                        .size(60.dp)
                        .border(BorderStroke(0.5.dp , Color.Black) , CircleShape)
                        .clip(CircleShape) ,
                    placeholder = painterResource(com.airbnb.lottie.R.drawable.abc_ic_search_api_material)

                )
            }
            user.email?.let { Text(text = it , style = MaterialTheme.typography.titleSmall) }
            user.age?.let { Text(text = "Age: $it" , style = MaterialTheme.typography.titleSmall) }
            user.info?.let { Text(text = it , style = MaterialTheme.typography.labelLarge) }
        }

    }
}


//@Preview(showBackground = true)
//@Composable
//fun UserUr() {
//    UserItem(
//        user = User(
//            nikeName = "abdo ali" , job = "killer" , info = "More info", age = 29,
//        )
//        ,
//        clickAction = {}
//    )
//}

@Preview(showBackground = true)
@Composable
fun UserPx() {
    UserCard(
        user = User(
            nikeName = "abdo ali" ,
            job = "killer" ,
            info = "Morecccccccccccccccccccccccccccccccccccc info" ,
            age = 29 ,
            email = "aass@ssk.sdk"
        )
    ) {

    }
}

@Preview
@Composable
fun ItmPe() {
    UserItem(
        user = User(
            nikeName = "abdo ali" ,
            job = "killer" ,
            info = "More info" ,
            age = 29 ,
            email = "aass@ssk.sdk"
        )
    ) {

    }
}

@Preview(locale = "ar")
@Composable
fun FriendsPrei() {
    val f = Friends(
        nikeName = "abdo ali" ,
        job = "killer" ,
        info = "Moreddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd info" ,
        age = 29 ,
        email = "aass@ssk.sdk" ,
        lastMass = "hi wokcgf" ,
        readLastMass = true
    )
    FriendItem(friends = f , clickAction = { /*TODO*/ })
    FriendItem(friends = f , clickAction = { /*TODO*/ })
}
