IcesDroide para android, es una aplicacion que permite enviar datos(audio) al servidor streaming icecast http://icecast.org
Tiene las siguientes caracteristicas:
a) Usa la libreria libshout del server streaming icecast que permite crear clientes.
b) Soporta los formatos Mp3 y Ogg vorbis.
c) Soporta idiomas español e ingles.


Requerimientos
-Libreria libshout descargar desde http://icecast.org
-Librerias libogg y libvorbis descargar desde http://www.xiph.org
-Android SDK y Android NDK descargar desde http://developer.android.com

Nota acerca de los requisitos
La aplicacion contiene las librerias libshout, libogg y libvorbis, usted puede actualizarlas con las nuevas versiones.

Compilacion e instalacion
-Configurar correctamente Android SDK y Android NDK
-En modo consola ejecutar
ndk-build
ant debug
-Instalar
adb -s xxxxxx install -r bin/IcesDroide-debug.apk



Mas informacion en:
http://androidprojectssl.blogspot.com
https://github.com/luisrevilla/IcesDroide
