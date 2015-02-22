/*
 * Created by Luis Revilla on 06.18.2012
 * mail: luis.revilla@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <jni.h>
#include <android/log.h>
#include <shout/shout.h>
#include <pthread.h>

    char *bufferA=NULL;
    int fileSize=0;
	void *CicloThread (void *ParamTheards);
	int ErrorTheards;
	pthread_t tid;
	pthread_attr_t *attr=NULL;
	int Eliminar_Thread();
	int Crear_Thread();
	shout_t *shout=NULL;
	char buff[4096];
	long read, ret, total;
	int loopA;
	int ErrConext=0;
	void Close_shout();
	int stopbucle=0;	
	static JNIEnv* envEventA;
	static jobject thisEventA;	
	int Send_EventA();	
	JavaVM *g_vm; 	
	int factorReprod=0;
	int ContafactorReprod=0;
	
void *CicloThread(void *ParamTheards)
{
	total = 0;				
	factorReprod=fileSize/4096;
	ContafactorReprod=0;
	if(bufferA!=NULL)
	for(loopA=0;loopA<factorReprod;loopA++)
	{
			if(stopbucle==1)break;
			memset(buff,'\0',4096);
			memcpy(buff,bufferA+(4096*loopA),4096);
			ret = shout_send(shout,buff,4096);
			if (ret != SHOUTERR_SUCCESS) {
					break;
			}		
			shout_sync(shout);
			ContafactorReprod++;
		}
	Eliminar_Thread();
	Close_shout();
	if(bufferA!=NULL)
	{
		free(bufferA);
		bufferA=NULL;
	}
	stopbucle=1;		
}

int Crear_Thread()
{
  attr=malloc(sizeof(pthread_attr_t));
  pthread_attr_init(attr);
  ErrorTheards = pthread_create(&tid, attr,CicloThread, NULL);	
	if(ErrorTheards != 0)
	{		
		return -1;
	}	
	return 1;
}
int Eliminar_Thread()
{
	if(attr!=NULL)
	{
	pthread_attr_destroy(attr);
	free(attr);
	attr=NULL;
	}
}
void Close_shout()
{
	if(shout!=NULL)
	{
		shout_close(shout);
		shout_shutdown();
		ErrConext=0;
	}
}
int Leer_OggMp3(const unsigned char  *pathfilamp3oggA)
{
    int err;
    FILE *filaA;
	filaA = fopen(pathfilamp3oggA,"rb"); 
	if(filaA==NULL)
	{
		return 0;
	}
	fseek (filaA, 0, SEEK_END);
	fileSize = ftell(filaA);
	rewind(filaA);
	bufferA = (char*) malloc(sizeof(char)*fileSize);
	memset(bufferA,'\0',fileSize);
	fread(bufferA, 1, fileSize, filaA);    
    fclose(filaA);        	        
    return 1;  
}
int Init_Icecast_Client(unsigned char *urlservercastA,int puertocast,unsigned char *montajecastA,unsigned char *usuariocastA,unsigned char *passwdcastA,int IsMp3Ogg)
{
	stopbucle=0;	
	shout_init();
	if (!(shout = shout_new())) {
		return -1;
	}
	if (shout_set_host(shout,urlservercastA) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_protocol(shout, SHOUT_PROTOCOL_HTTP) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_port(shout,puertocast) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_password(shout,passwdcastA) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_mount(shout,montajecastA) != SHOUTERR_SUCCESS) {
		return -1;
	}
	if (shout_set_user(shout,usuariocastA) != SHOUTERR_SUCCESS) {
		return -1;
	}	
	if(IsMp3Ogg==0)
	{
		if (shout_set_format(shout,SHOUT_FORMAT_MP3) != SHOUTERR_SUCCESS) {
			return -1;
		}
	}else
	{
		if (shout_set_format(shout, SHOUT_FORMAT_OGG) != SHOUTERR_SUCCESS) {
			return -1;
		}		
	}
	if (shout_open(shout) == SHOUTERR_SUCCESS) {
		ErrConext=1;
	} else {
		ErrConext=-1;
	}	
	return ErrConext;
}
int Send_EventA()
{
	jclass          cls; 
	jmethodID       mid; 
	int             i; 
	jstring         js; 
	int envErr = 0;        
	cls = (*envEventA)->FindClass(envEventA,"com/ices/IcesDroide/IcesDroide");         
	if (cls == 0) 
        return; 
	js = (*envEventA)->NewStringUTF(envEventA, "Se envio el eventoA"); 
	mid = (*envEventA)->GetMethodID(envEventA, cls, "RecibeEventA", "(Ljava/lang/String;)V"); 
	if (mid == 0) 
       return; 
	(*envEventA)->CallVoidMethod(envEventA, thisEventA, mid, js); 
}
jint Java_com_ices_IcesDroide_IcesDroide_RetPorcetjPlay( JNIEnv* env, jobject this)
{
	return (ContafactorReprod*100)/factorReprod;
}	
jint Java_com_ices_IcesDroide_IcesDroide_EventA( JNIEnv* env, jobject this)
{
	return stopbucle;
}
jint Java_com_ices_IcesDroide_IcesDroide_StopStreaming( JNIEnv* env, jobject this)
{
	stopbucle=1;
    return 0;
}
jint Java_com_ices_IcesDroide_IcesDroide_ReadStreaming( JNIEnv* env, jobject this)
{
	Crear_Thread();
    return 0;
}
jint Java_com_ices_IcesDroide_IcesDroide_LoadFile( JNIEnv* env, jobject this,jstring pathfilamp3ogg)
{
	const unsigned char  *pathfilamp3oggA=NULL;
	pathfilamp3oggA = (*env)->GetStringUTFChars(env,pathfilamp3ogg, 0);
    return Leer_OggMp3(pathfilamp3oggA);
}
jint Java_com_ices_IcesDroide_IcesDroide_InitIcecast( JNIEnv*  env,jobject  this,jstring urlservercast,jint puertocast,jstring montajecast,jstring usuariocast,jstring passwdcast,jint IsMp3Ogg)
{
	const unsigned char  *urlservercastA=NULL;
	const unsigned char  *montajecastA=NULL;
	const unsigned char  *usuariocastA=NULL;
	const unsigned char  *passwdcastA=NULL;
	urlservercastA = (*env)->GetStringUTFChars(env,urlservercast, 0);
	montajecastA = (*env)->GetStringUTFChars(env,montajecast, 0);
	usuariocastA = (*env)->GetStringUTFChars(env,usuariocast, 0);
	passwdcastA = (*env)->GetStringUTFChars(env,passwdcast, 0);	
	return Init_Icecast_Client((unsigned char*)urlservercastA,puertocast,(unsigned char*)montajecastA,(unsigned char*)usuariocastA,(unsigned char*)passwdcastA,IsMp3Ogg);
}
