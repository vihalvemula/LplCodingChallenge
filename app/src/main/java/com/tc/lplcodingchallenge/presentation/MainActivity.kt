package com.tc.lplcodingchallenge.presentation

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.tc.lplcodingchallenge.R
import com.tc.lplcodingchallenge.data.model.PostsItemModel
import com.tc.lplcodingchallenge.ui.theme.LplCodingChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel:PostsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LplCodingChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PostsListScreen(viewModel,modifier =Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun PostsListScreen(viewModel: PostsViewModel,modifier: Modifier=Modifier){
    val posts by viewModel.posts.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    when(val state = posts){
        is UiState.LOADING->{
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(modifier.size(100.dp))
            }
        }
        is UiState.SUCCESS->{
            if (isLandscape) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = modifier.fillMaxSize(),
                ) {
                    items(state.data) { item ->
                        PostItem(item)
                    }
                }
            } else {
                LazyColumn(modifier = modifier) {
                    items(state.data) { item ->
                        PostItem(item)
                    }
                }
            }

        }
        is UiState.ERROR->{
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
             Text(text = "${state.msg}")
            }
        }
    }

}

@Composable
fun PostItem(comment: PostsItemModel) {
    var imageUri by rememberSaveable  { mutableStateOf<Uri?>(null) }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(5.dp),
            verticalAlignment = Alignment.Top
        ) {

            Image(
                painter = if (imageUri != null) {
                    rememberAsyncImagePainter(imageUri)
                } else {
                    painterResource(id = R.drawable.img)
                },
                contentDescription = "icon",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") }
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = comment.name ?: "",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Clip
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = comment.email ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Clip,
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = comment.id.toString() ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                )

                Text(
                    text = comment.body ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = Int.MAX_VALUE,
                    overflow = TextOverflow.Clip
                )
            }
        }
    }
}
