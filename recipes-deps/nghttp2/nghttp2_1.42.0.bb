SUMMARY = "HTTP/2 C Library and tools"
HOMEPAGE = "https://nghttp2.org/"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=764abdf30b2eadd37ce47dcbce0ea1ec"

SRC_URI = "https://github.com/nghttp2/nghttp2/releases/download/v${PV}/nghttp2-${PV}.tar.xz"

SRC_URI[md5sum] = "c38f1d230af8cec480ff9dd60c9e0db0"
SRC_URI[sha256sum] = "c5a7f09020f31247d0d1609078a75efadeccb7e5b86fc2e4389189b1b431fe63"

inherit autotools pkgconfig

EXTRA_OECONF += "--enable-lib-only"

do_install_append() {

	# Remove empty dir
	rmdir ${D}${bindir}

	# The fetch-ocsp-response script is python and not required for lib only installs.
	rm ${D}${datadir}/${BPN}/fetch-ocsp-response
	rmdir ${D}${datadir}/${BPN}
}
