SUMMARY = "A sophisticated network protocol analyzer"
HOMEPAGE = "http://www.tcpdump.org/"
SECTION = "net"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1d4b0366557951c84a94fabe3529f867"

DEPENDS = "libpcap"

SRC_URI = " \
    http://www.tcpdump.org/release/${BP}.tar.gz \
    file://unnecessary-to-check-libpcap.patch \
    file://avoid-absolute-path-when-searching-for-libdlpi.patch \
    file://add-ptest.patch \
    file://run-ptest \
"

SRC_URI[md5sum] = "9bbc1ee33dab61302411b02dd0515576"
SRC_URI[sha256sum] = "798b3536a29832ce0cbb07fafb1ce5097c95e308a6f592d14052e1ef1505fe79"

inherit autotools-brokensep ptest

PACKAGECONFIG ?= "openssl"

PACKAGECONFIG[libcap-ng] = "--with-cap-ng,--without-cap-ng,libcap-ng"
PACKAGECONFIG[openssl] = "--with-crypto,--without-openssl --without-crypto,openssl"
PACKAGECONFIG[smi] = "--with-smi,--without-smi,libsmi"

EXTRA_AUTORECONF += "-I m4"

# FULL_OPTIMIZATION_append = " -Os"

CFLAGS += "-ffunction-sections -fdata-sections"
LDFLAGS += "-Wl,--gc-sections"

# Original:
# -rwxr-xr-x 2 root root 787820 Oct 25 00:07 ../packages-split/tcpdump/usr/sbin/tcpdump
# gc-sections
# -rwxr-xr-x 2 root root 785452 Oct 25 16:45 ../packages-split/tcpdump/usr/sbin/tcpdump
# -Os
# -rwxr-xr-x 2 root root 650608 Oct 25 16:40 ../packages-split/tcpdump/usr/sbin/tcpdump
# -Os + gc-sections
# -rwxr-xr-x 2 root root 649504 Oct 25 16:47 ../packages-split/tcpdump/usr/sbin/tcpdump

# AC_CHECK_LIB(pcap, pcap_compile, LIBS="$LIBS -l:libpcap.a -ldbus-1")

do_configure_prepend() {
    mkdir -p ${S}/m4
    if [ -f aclocal.m4 ]; then
        mv aclocal.m4 ${S}/m4
    fi
}

do_install_append() {
    # make install installs an unneeded extra copy of the tcpdump binary
    rm -f ${D}${sbindir}/tcpdump.${PV}
}

do_compile_ptest() {
    oe_runmake buildtest-TESTS
}
