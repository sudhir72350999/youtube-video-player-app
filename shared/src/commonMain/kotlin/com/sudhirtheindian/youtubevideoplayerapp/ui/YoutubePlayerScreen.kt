package com.sudhirtheindian.youtubevideoplayerapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.sudhirtheindian.youtubevideoplayerapp.model.VideoItem
import com.sudhirtheindian.youtubevideoplayerapp.viewmodel.YoutubeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun YoutubePlayerScreen(viewModel: YoutubeViewModel = viewModel { YoutubeViewModel() }) {
    val videos by viewModel.videos.collectAsState()
    val selectedVideo by viewModel.selectedVideo.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
        selectedVideo?.let { video ->
            YoutubePlayer(video.id)
        } ?: Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("Select a video to play", style = MaterialTheme.typography.headlineSmall)
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Search Videos") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        HorizontalDivider()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(videos) { video ->
                VideoListItem(video = video, onClick = { viewModel.selectVideo(video) })
            }
        }
    }
}

@Composable
fun YoutubePlayer(videoId: String) {
    val webViewState = rememberWebViewState("https://www.youtube.com/embed/$videoId")
    
    Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize()
        )
        if (webViewState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().align(androidx.compose.ui.Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun VideoListItem(video: VideoItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = video.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Video ID: ${video.id}", style = MaterialTheme.typography.bodySmall)
        }
    }
}
