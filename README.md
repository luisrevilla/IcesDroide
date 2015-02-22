IcesDroide for android, is a app that sends data(audio) streaming to icecast server http://icecast.org
Has the following characteristics:
a) Use of the library libshout icecast streaming server that allows clients to create.
c) Supports MP3 and Ogg Vorbis formats.
d) Supports English and Spanish languages.

Requirements
-Library libshout download from http://icecast.org
-Library libogg and libvorbis download from http://www.xiph.org
-Android SDK y Android NDK download from http://developer.android.com

Note about requirements
The application contains the libraries libshout, libogg and libvorbis, you can update them with new versions.

Compilation and installation
-Setting Android SDK and Android NDK
-Run in console mode
ndk-build
ant debug
-Install
adb -s xxxxxx install -r bin/IcesDroide-debug.apk


More information on:
http://androidprojectssl.blogspot.com
https://github.com/luisrevilla/IcesDroide
