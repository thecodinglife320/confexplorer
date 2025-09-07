package component

import Video
import csstype.*
import react.*
import emotion.react.css
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3

external interface VideoPlayerProps : Props {
   var currentVideo: Video
   var onMark: () -> Unit
   var isWatched: Boolean
}

val VideoPlayer = FC<VideoPlayerProps> { props ->

   div {

      css{
         position = Position.absolute
         left = 50.px
      }

      h3 {
         +"${props.currentVideo.speaker}: ${props.currentVideo.title}"
      }

      button {
         css {
            display = Display.block
         }
         onClick = {
            props.onMark()
         }
         if (props.isWatched) {
            +"Mark as unwatched"
         } else {
            +"Mark as watched"
         }
      }

      div {

         EmailShareButton {
            url = props.currentVideo.videoUrl
            EmailIcon {
               size = 32
               round = true
            }
         }
         TelegramShareButton {
            url = props.currentVideo.videoUrl
            TelegramIcon {
               size = 32
               round = true
            }
         }
      }

      YoutubePlayer {
         url = props.currentVideo.videoUrl
         controls = true
      }
   }
}