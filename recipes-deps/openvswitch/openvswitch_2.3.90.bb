SUMMARY = "Production Quality, Multilayer Open Virtual Switch"
DESCRIPTION = "Open vSwitch is a production quality, multilayer virtual switch \
licensed under the open source Apache 2.0 license.  It is designed to enable \
massive network automation through programmatic extension, while still \
supporting standard management interfaces and protocols (e.g. NetFlow, sFlow, \
IPFIX, RSPAN, CLI, LACP, 802.1ag)."
HOMEPAGE = "http://openvswitch.org/"
SECTION = "networking"
LICENSE = "Apache-2"
LIC_FILES_CHKSUM = "file://COPYING;md5=5973c953e3c8a767cf0808ff8a0bac1b"

DEPENDS = "openssl"

PV .= "+git${SRCPV}"

SRCREV = "58be9c9fd732b5bdd3d4c2e9b8cc2313f570094d"

SRC_URI = "git://github.com/openvswitch/ovs.git;protocol=https \
           file://0001-netdev-linux-Use-unsigned-int-for-ifi_flags.patch \
           file://0002-netdev-linux-Let-interface-flag-survive-internal-por.patch \
           file://0003-datapath-do-not-add-vlan_hwaccel_push_inside-for-ker.patch \
           file://0004-matej-dont-do-that-do-aproper-one.patch \
           file://disable_m4_check.patch \
"

S = "${WORKDIR}/git"

inherit autotools-brokensep perlnative pythonnative

EXTRA_OECONF += "--disable-static --enable-shared"

do_configure_prepend() {
	# Work around the for Makefile CC=$(if ....) by swapping out any
	# "-Wa," assembly directives with "-Xassembler
	CC=`echo '${CC}' | sed 's/-Wa,/-Xassembler /g'`
}

PACKAGES += "${PN}-libofproto ${PN}-libopenvswitch ${PN}-libovsdb ${PN}-libsflow ${PN}-misc"

# Over-ride the default packaging rules to explicitly control what goes into
# the main runtime package, currently just the ovsdb-client and ovsdb-server
# binaries. The OVS libs are packaged separately and will be pulled into the
# target rootfs automatically as required.

FILES_${PN} = "${bindir}/ovsdb-client ${sbindir}/ovsdb-server"

FILES_${PN}-libofproto = "${libdir}/libofproto${SOLIBS}"
FILES_${PN}-libopenvswitch = "${libdir}/libopenvswitch${SOLIBS}"
FILES_${PN}-libovsdb = "${libdir}/libovsdb${SOLIBS}"
FILES_${PN}-libsflow = "${libdir}/libsflow${SOLIBS}"

# Use -misc as a catchall for anything not required in the target rootfs.
# Note: this list is still under review and some things may still need to be
# moved into the main runtime package.

FILES_${PN}-misc = "${bindir}/* ${sbindir}/* ${sysconfdir} ${localstatedir} ${datadir}/${BPN} /run"

# /usr/bin/ovs-pcap needs python and /usr/bin/ovs-docker needs bash, however
# since we don't ever intend to install or use the -misc package, silence the
# QA warnings about missing runtime dependencies.

INSANE_SKIP_${PN}-misc = "file-rdeps"

# A nativesdk variant is required in order to provide ovsdb-tool in the SDK.

BBCLASSEXTEND = "native nativesdk"
