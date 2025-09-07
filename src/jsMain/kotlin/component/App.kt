package component

import Video
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h3
import react.useEffectOnce
import react.useState

val mainScope = MainScope()

val App = FC<Props> {

   var currentVideo: Video? by useState(null)
   var unwatchedVideos: List<Video> by useState(emptyList())
   var watchedVideos: List<Video> by useState(emptyList())

   useEffectOnce {
      mainScope.launch {
         unwatchedVideos = fetchVideos()
      }
   }

   h1 {
      +"KotlinConf Explorer"
   }

   div {

      h3 {
         +"Videos to watch"
      }
      VideoList {
         videos = unwatchedVideos
         selectedVideo = currentVideo
         onSelectVideo = {
            currentVideo = it
         }
      }

      h3 {
         +"Videos watched"
      }
      VideoList {
         videos = watchedVideos
         selectedVideo = currentVideo
         onSelectVideo = {
            currentVideo = it
         }
      }
   }

   if (currentVideo != null) {
      VideoPlayer { ->
         isWatched = currentVideo in watchedVideos
         this.currentVideo = currentVideo!!
         onMark = {
            if (currentVideo in unwatchedVideos) {
               unwatchedVideos = unwatchedVideos - currentVideo!!
               watchedVideos = watchedVideos + currentVideo!!
            } else {
               watchedVideos = watchedVideos - currentVideo!!
               unwatchedVideos = unwatchedVideos + currentVideo!!
            }
         }
      }
   }
}

suspend fun fetchVideo(id: Int): Video {
   val response = window
      .fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id")
      .await()
      .text()
      .await()
   return Json.decodeFromString(response)
}

suspend fun fetchVideos(): List<Video> = coroutineScope {
   (1..25).map { id ->
      async {
         fetchVideo(id)
      }
   }.awaitAll()
}