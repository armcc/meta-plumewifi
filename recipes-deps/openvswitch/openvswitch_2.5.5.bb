SUMMARY = "Production Quality, Multilayer Open Virtual Switch"
DESCRIPTION = "Open vSwitch is a production quality, multilayer virtual switch \
licensed under the open source Apache 2.0 license.  It is designed to enable \
massive network automation through programmatic extension, while still \
supporting standard management interfaces and protocols (e.g. NetFlow, sFlow, \
IPFIX, RSPAN, CLI, LACP, 802.1ag)."
HOMEPAGE = "http://openvswitch.org/"
SECTION = "networking"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=247d8817aece26b21a8cd6791b3ea994"

DEPENDS = "openssl"

SRC_URI = "http://openvswitch.org/releases/openvswitch-2.5.5.tar.gz \
           file://0001-m4-handle-configuring-with-PYTHON-usr-bin-env-python.patch \
           file://0001-combine-ovsdb-client-and-ovsdb-server-into-single-mu.patch \
"

SRC_URI[md5sum] = "dc38146f0815348d8347a6b972bd4920"
SRC_URI[sha256sum] = "a7c56ce91546de025e1b2934811657c471f52700981233a176827c22615cfb4a"

inherit autotools perlnative pythonnative

EXTRA_OECONF += "PYTHON='/usr/bin/env python' --disable-libcapng"

# Only the ovsdb-server and ovsdb-client apps are required, and linking them
# statically with libopenvswitch and libovsdb (rather than shipping the full
# libopenvswitch.so and libovsdb.so shared libs) gives an overall reduction
# in rootfs size.

EXTRA_OECONF += "--enable-static --disable-shared"

CFLAGS += "-ffunction-sections -fdata-sections"
LDFLAGS += "-Wl,--gc-sections"

do_configure_prepend() {
	# Work around the for Makefile CC=$(if ....) by swapping out any
	# "-Wa," assembly directives with "-Xassembler
	CC=`echo '${CC}' | sed 's/-Wa,/-Xassembler /g'`
}

do_install_append() {
	ln -sf ${@oe.path.relative('${bindir}', '${sbindir}/ovsdb-server')} ${D}${bindir}/ovsdb-client
}

PACKAGES =+ "${PN}-libofproto ${PN}-libopenvswitch ${PN}-libovn ${PN}-libovsdb ${PN}-libsflow ${PN}-libvtep"
PACKAGES += "${PN}-misc"

# Over-ride the default packaging rules to explicitly control what goes into
# the main runtime package, currently just the ovsdb-client and ovsdb-server
# binaries. The OVS libs are packaged separately and will be pulled into the
# target rootfs automatically as required.

FILES_${PN} = "${bindir}/ovsdb-client ${sbindir}/ovsdb-server"

FILES_${PN}-libofproto = "${libdir}/libofproto${SOLIBS}"
FILES_${PN}-libopenvswitch = "${libdir}/libopenvswitch${SOLIBS}"
FILES_${PN}-libovn = "${libdir}/libovn${SOLIBS}"
FILES_${PN}-libovsdb = "${libdir}/libovsdb${SOLIBS}"
FILES_${PN}-libsflow = "${libdir}/libsflow${SOLIBS}"
FILES_${PN}-libvtep = "${libdir}/libvtep${SOLIBS}"

# Use -misc as a catchall for anything not required in the target rootfs.
# Note: this list is still under review and some things may still need to be
# moved into the main runtime package.

FILES_${PN}-misc = "${bindir}/* ${sbindir}/* ${sysconfdir} ${localstatedir} ${datadir}/${BPN} /run"

# /usr/bin/ovs-pcap etc need python and /usr/bin/ovs-docker needs bash, however
# since we don't ever intend to install or use the -misc package, silence the
# QA warnings about missing runtime dependencies.

INSANE_SKIP_${PN}-misc = "file-rdeps"

# A nativesdk variant is required in order to provide ovsdb-tool in the SDK.

BBCLASSEXTEND = "native nativesdk"
