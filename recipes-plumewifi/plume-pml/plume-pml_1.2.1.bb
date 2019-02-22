SUMMARY = "Plume PML"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=df3f42ef5870da613e959ac4ecaa1cb8"

DEPENDS = "jansson libev mosquitto openvswitch openvswitch-native protobuf protobuf-c"
DEPENDS += "${@'cbnapps' if os.path.isfile('${COREBASE}/meta-cbnapps/conf/layer.conf') else 'cbnlibs-prebuilt'}"

# As of 1.0.0.0, the dependency on openssl is only to enable definitions from
# openssl headers to be used when calling mosquitto_tls_opts_set(). These
# definitions are expected to be stable across all versions of openssl, so
# there's no big concern with building plume-pml against one version of openssl
# and then later running with another.

DEPENDS += "openssl"

PV .= "+git${SRCPV}"

SRCREV_pml = "5e9a15821f31ce2cb4c26fcdfcb714c2cf468b7a"
SRCREV_vendor = "9056e6293347ebcb3e10253ed93fd30621c9b302"
SRCREV_FORMAT = "pml_vendor"

SRC_URI = "${PLUME_GIT}/plume-pml.git;protocol=ssh;destsuffix=git/plume-pml;name=pml;branch=pml_1.2.1 \
           ${PLUME_GIT}/device-vendor-lgi.git;protocol=ssh;destsuffix=git/vendor/lgi;name=vendor;branch=2.0.0-track \
           file://0001-Revert-PML-1.2-Move-hadling-of-the-DHCP_leased_IP-ta.patch \
"

S = "${WORKDIR}/git/plume-pml"

inherit python3native

PARALLEL_MAKE = ""

PML_TARGET ?= "CH7465CE"

EXTRA_OEMAKE = "TARGET=${PML_TARGET} OEBUILD=1"

do_compile() {
    # Create a dummy Plume SDK top-level directory and Makefile.
    # Fixme: Needs review (it may not be required?)

    mkdir -p ${S}/../sdk/lgi/isdk-${PML_TARGET}
    touch ${S}/../sdk/lgi/isdk-${PML_TARGET}/Makefile

    oe_runmake rootfs
}

do_install() {
    cp -av --no-preserve=ownership ${B}/work/${PML_TARGET}/rootfs/* ${D}/
}

FILES_${PN} += " \
    /usr/plume \
    /usr/share/plume \
    /usr/share/openvswitch \
"

# All binaries have already been stripped

INSANE_SKIP_${PN} += "already-stripped"

# The libplume.so shared lib is in an unexpected location ( /usr/plume/lib/ ).
# Since all binaries which link with it contain a custom rpath the non-standard
# location is OK and we can ignore warnings etc from the "libdir" QA test.

INSANE_SKIP_${PN} += "libdir"

# Explicit list of non-library runtime dependencies (ie runtime dependencies
# which are not auto detected).

RDEPENDS_${PN} += "curl openvswitch"
