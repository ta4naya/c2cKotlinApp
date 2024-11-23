package com.example.c2capp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFirstPageView(homeFirstPageViewModel: HomeFirstPageViewModel = viewModel()) {
    rememberCoroutineScope()

    LaunchedEffect(Unit) {
        homeFirstPageViewModel.fetchLatestVideo()
        homeFirstPageViewModel.fetchLatestNews()
        homeFirstPageViewModel.fetchCarousels()
        homeFirstPageViewModel.fetchDailyVerses()
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CarouselSection(homeFirstPageViewModel.carousels.value)
                Spacer(modifier = Modifier.height(16.dp))
                LatestNewsSection(homeFirstPageViewModel.latestNews.value)
                Spacer(modifier = Modifier.height(16.dp))
                LatestVideoSection(homeFirstPageViewModel.latestVideo.value)
            }
        }
    )
}

@Composable
fun CarouselSection(carousels: List<Carousel>?) {
    carousels?.let {
        Text("Carousel", style = MaterialTheme.typography.headlineSmall)
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(carousels.size) { index ->
                val carousel = carousels[index]
                carousel.picsPath?.let { path ->
                    Image(
                        painter = rememberAsyncImagePainter(model = path),
                        contentDescription = null,
                        modifier = Modifier
                            .height(300.dp)
                            .width(200.dp)
                            .padding(horizontal = 8.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    } ?: run {
        Text("Loading carousels...", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun LatestNewsSection(news: LatestNews?) {
    news?.let {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Latest News", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                news.title?.let { title ->
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
                news.picsPath?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    } ?: run {
        Text("Loading latest news...", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun LatestVideoSection(video: C2Cyoutube?) {
    video?.let {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Latest Cover2Cover", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Day ${video.day}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                video.picsPath?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    } ?: run {
        Text("Loading latest video...", style = MaterialTheme.typography.bodyMedium)
    }
}

