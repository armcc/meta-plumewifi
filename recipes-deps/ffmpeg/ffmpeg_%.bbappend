
# Commercial licensing doesn't apply with minimal config used here
LICENSE_FLAGS_remove = "commercial"

PACKAGECONFIG = "avcodec avformat"

EXTRA_FFCONF = " \
    --disable-all \
    --disable-debug \
    --disable-pthreads \
    --disable-runtime-cpudetect \
    --disable-static \
    --disable-symver \
    --enable-avutil \
    --enable-demuxer=matroska \
    --enable-demuxer=mov \
    --enable-lto \
"

# Original vendor suggested config options:
#
#   --arch=x86
#   --cpu=atom
#   --disable-all
#   --disable-bzlib
#   --disable-debug
#   --disable-pthreads
#   --disable-runtime-cpudetect
#   --disable-static
#   --disable-symver
#   --disable-yasm
#   --disable-zlib
#   --enable-avcodec
#   --enable-avformat
#   --enable-avutil
#   --enable-cross-compile
#   --enable-demuxer=matroska
#   --enable-demuxer=mov
#   --enable-lto
#   --enable-shared
#   --enable-small
#   --extra-libs=-lrt
#   --libdir=/lib
#   --prefix=/
#   --target-os=linux
#
