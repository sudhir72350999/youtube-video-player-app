package com.sudhirtheindian.youtubevideoplayerapp.viewmodel

import androidx.lifecycle.ViewModel
import com.sudhirtheindian.youtubevideoplayerapp.model.VideoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class YoutubeViewModel : ViewModel() {
    private val _videos = MutableStateFlow<List<VideoItem>>(emptyList())
    val videos: StateFlow<List<VideoItem>> = _videos.asStateFlow()

    private val _selectedVideo = MutableStateFlow<VideoItem?>(null)
    val selectedVideo: StateFlow<VideoItem?> = _selectedVideo.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val allVideos = listOf(
        VideoItem("dQw4w9WgXcQ", "Never Gonna Give You Up", "https://img.youtube.com/vi/dQw4w9WgXcQ/0.jpg"),
        VideoItem("9bZkp7q19f0", "PSY - GANGNAM STYLE(강남스타일) M/V", "https://img.youtube.com/vi/9bZkp7q19f0/0.jpg"),
        VideoItem("kJQP7kiw5Fk", "Luis Fonsi - Despacito ft. Daddy Yankee", "https://img.youtube.com/vi/kJQP7kiw5Fk/0.jpg"),
        VideoItem("JGwWNGJdvx8", "Ed Sheeran - Shape of You [Official Video]", "https://img.youtube.com/vi/JGwWNGJdvx8/0.jpg"),
        VideoItem("OPf0YbXqDm0", "Mark Ronson - Uptown Funk ft. Bruno Mars", "https://img.youtube.com/vi/OPf0YbXqDm0/0.jpg")
    )

    init {
        _videos.value = allVideos
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        _videos.value = if (query.isEmpty()) {
            allVideos
        } else {
            allVideos.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    fun selectVideo(video: VideoItem) {
        _selectedVideo.value = video
    }
}
