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

# ----------------------------------------------------------------------------
#
# Currently we need to use a non-standard set of branches to build plume-pml:
#
# - We can't use device-vendor-lgi master since the master branch of plume-pml
#   can only be built using the 2.0.4 branch of device-vendor-lgi.
#
# - We can't use the 2.0.4 branch of device-vendor-lgi directly since it is
#   missing OE build support + gcc 7 and gcc 8 fixes. Therefore use a temporary
#   fork of device-vendor-lgi ( 2.0.4_lgi ) which has these changes backported
#   from master.
#
# - We can't use the master branch of plume-pml directly since it is missing
#   gcc 7 and gcc 8 fixes (these have been submitted upstream but not yet
#   merged). Therefore use a temporary fork of plume-pml ( master_lgi ) which
#   contains these gcc 7 and gcc 8 build fixes.
#
# THIS IS A TEMPORARY SOLUTION - WE SHOULD SWITCH TO THE OFFICIAL UPSTREAM REPOS SOON!
#
# ----------------------------------------------------------------------------

PLUME_GIT = "git://git@github.com/armcc"

SRCREV_pml = "42b680a5efa57a59676e2c0788fc175bcb994ca3"
SRCREV_vendor = "f8085649763730dc94ac9a8c0c01ac2a6781e998"
SRCREV_FORMAT = "pml_vendor"

SRC_URI = "${PLUME_GIT}/plume-pml.git;protocol=ssh;destsuffix=git/plume-pml;name=pml;branch=master_lgi \
           ${PLUME_GIT}/device-vendor-lgi.git;protocol=ssh;destsuffix=git/vendor/lgi;name=vendor;branch=2.0.4_lgi \
"

S = "${WORKDIR}/git/plume-pml"

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

# The /usr/plume/tools/lm_log_pull.sh script uses the curl command line tool.

RDEPENDS_${PN} = "curl"
