SUMMARY = "HTTP/2 C Library and tools"
HOMEPAGE = "https://nghttp2.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=764abdf30b2eadd37ce47dcbce0ea1ec"

SRC_URI = "https://github.com/nghttp2/nghttp2/releases/download/v${PV}/nghttp2-${PV}.tar.xz"

SRC_URI[md5sum] = "c1d607bf3830000acd7a51f0058f4bd2"
SRC_URI[sha256sum] = "f7d54fa6f8aed29f695ca44612136fa2359013547394d5dffeffca9e01a26b0f"

inherit autotools pkgconfig

EXTRA_OECONF += "--enable-lib-only"

do_install_append() {

	# Remove empty dir
	rmdir ${D}${bindir}

	# The fetch-ocsp-response script is python and not required for lib only installs.
	rm ${D}${datadir}/${BPN}/fetch-ocsp-response
	rmdir ${D}${datadir}/${BPN}
}
