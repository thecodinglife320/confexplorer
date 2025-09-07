@file:JsModule("react-player")
@file:JsNonModule

package component

import react.*

@JsName("default")
external val YoutubePlayer: ComponentClass<YouTubePlayerProps>

external interface YouTubePlayerProps: Props{
   var url: String
   var controls: Boolean
}