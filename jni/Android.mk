# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# the purpose of this sample is to demonstrate how one can
# generate two distinct shared libraries and have them both
# uploaded in
#

LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := libicesshout
LOCAL_CFLAGS    := -DHAVE_CONFIG_H=1 -ffast-math -fsigned-char -pthread -O2 -ffast-math -Werror -DHAVE_CUALQUIERVARIABLE=1 -I$(LOCAL_PATH)/libvorbis/include -I$(LOCAL_PATH)/libogg/include -I$(LOCAL_PATH)/libshout/include -I$(LOCAL_PATH)/libshout/src 
LOCAL_SRC_FILES := libvorbis/src/analysis.c libvorbis/src/codebook.c libvorbis/src/floor1.c libvorbis/src/lpc.c libvorbis/src/mdct.c libvorbis/src/res0.c libvorbis/src/synthesis.c libvorbis/src/window.c libvorbis/src/bitrate.c libvorbis/src/envelope.c libvorbis/src/info.c libvorbis/src/lsp.c libvorbis/src/psy.c libvorbis/src/sharedbook.c libvorbis/src/vorbisenc.c libvorbis/src/block.c libvorbis/src/floor0.c libvorbis/src/lookup.c libvorbis/src/mapping0.c libvorbis/src/registry.c libvorbis/src/smallft.c libvorbis/src/vorbisfile.c libogg/src/bitwise.c libogg/src/framing.c libshout/src/shout.c libshout/src/timing libshout/src/mp3.c libshout/src/ogg.c libshout/src/util.c libshout/src/vorbis.c libshout/src/avl/avl.c libshout/src/httpp/httpp.c libshout/src/net/resolver.c libshout/src/net/sock.c libshout/src/thread/thread.c libshout/src/timing/timing.c libshout/src/icessend.c 
LOCAL_LDLIBS    := -lm -llog -ljnigraphics

include $(BUILD_SHARED_LIBRARY)
