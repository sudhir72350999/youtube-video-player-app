package com.sudhirtheindian.youtubevideoplayerapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform