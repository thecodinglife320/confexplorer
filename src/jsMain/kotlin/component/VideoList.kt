package component

import Video
import csstype.Cursor
import csstype.NamedColor
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.p

val VideoList = FC<VideoListProps> { props ->
   for (video in props.videos) {
      p {
         key = video.id.toString()
         onClick = {
            props.onSelectVideo(video)
         }
         css {
            cursor = Cursor.pointer
            if (video == props.selectedVideo) {
               color = NamedColor.red
            }
         }
         +"${video.speaker}: ${video.title}"
      }
   }
}

external interface VideoListProps : Props {
   var videos: List<Video>
   var selectedVideo: Video?

   var onSelectVideo: (Video) -> Unit
}