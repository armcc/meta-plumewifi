SUMMARY = "A full-featured and high-performance event loop that is loosely \
modelled after libevent."
HOMEPAGE = "http://software.schmorp.de/pkg/libev.html"
LICENSE = "BSD-2-Clause | GPL-2.0+"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d6ad416afd040c90698edcdf1cbee347"

SRC_URI = "http://dist.schmorp.de/libev/Attic/${BP}.tar.gz"
SRC_URI += "file://0001-workaround-bug-in-automake-1.14-and-1.4.1.patch"

SRC_URI[md5sum] = "01d1c672697f649b4f94abd0b70584ff"
SRC_URI[sha256sum] = "88fc5f89ca96ceca14c16c10e7be3e921dae65e84932d680c2fd6a40173edccb"

inherit autotools

EXTRA_OECONF += "--with-pic"

do_install_append() {
    # Avoid conflicting with libevent. The provided compatibility layer is
    # still basic so drop it for now.
    rm ${D}${includedir}/event.h
}
