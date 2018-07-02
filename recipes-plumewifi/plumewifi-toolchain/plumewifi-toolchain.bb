SUMMARY = "PlumeWiFi specific installable toolchain"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit populate_sdk

TOOLCHAIN_TARGET_TASK_append = " \
    curl \
    jansson \
    libev \
    libmosquitto1 \
    libmosquittopp1 \
    openssl \
    openvswitch \
    protobuf \
    protobuf-c \
    zlib \
"

TOOLCHAIN_HOST_TASK_append = " \
    nativesdk-openvswitch \
"
