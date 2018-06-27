SUMMARY = "Protocol Buffers - structured data serialisation mechanism"
DESCRIPTION = "This is protobuf-c, a C implementation of the Google Protocol Buffers data \
serialization format. It includes libprotobuf-c, a pure C library that \
implements protobuf encoding and decoding, and protoc-c, a code generator that \
converts Protocol Buffer .proto files to C descriptor code, based on the \
original protoc. protobuf-c formerly included an RPC implementation; that code \
has been split out into the protobuf-c-rpc project."
HOMEPAGE = "https://github.com/protobuf-c/protobuf-c"
SECTION = "console/tools"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=69b77d3fc4947bd8dd1135355ab11dd3"

DEPENDS = "protobuf-native protobuf"

PV .= "+git${SRCPV}"
SRCREV = "3b7d27a462bcbd9500f21a8de06655f779e247a2"

SRC_URI = "git://github.com/protobuf-c/protobuf-c.git"
SRC_URI += "file://skip-protoc-c-tests-on-cross-compiling.patch"

S = "${WORKDIR}/git"

inherit autotools pkgconfig

PACKAGE_BEFORE_PN = "${PN}-compiler"

FILES_${PN}-compiler = "${bindir}"

RDEPENDS_${PN}-compiler = "protobuf-compiler"
RDEPENDS_${PN}-dev += "${PN}-compiler"

BBCLASSEXTEND = "native nativesdk"
