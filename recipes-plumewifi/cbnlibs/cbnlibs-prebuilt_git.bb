SUMMARY = "CBN provided link dependencies for Plume PML (prebuilt)"
LICENSE = "CLOSED"

PV = "0.0+git${SRCPV}"

# We don't need the full ch7465ce SDK repo (which contains Buildroot sources
# etc, etc) but it's a convenient way to fetch a well defined version of the
# CBN prebuilt libs.

SRCREV = "e2e23eb598ba40e35b838c73b1c5677bce835f32"

SRC_URI = "${PLUME_GIT}/sdk-lgi-isdk-ch7465ce.git;protocol=ssh"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    cp -av --no-preserve=ownership ${S}/contrib/libs/* ${D}/
}

# Prebuilt libs are for x86

COMPATIBLE_HOST = "(i.86|x86_64).*-linux"

# The CBN shared libs aren't versioned, so force the .so files into the
# run-time package (and keep them out of the -dev package). Doing so also
# forces the libiosf.so symlink into the run-time package, so disable the
# dev-so sanity check too.

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${base_libdir}/*.so"
INSANE_SKIP_${PN} += "dev-so"

# All binaries have already been stripped

INSANE_SKIP_${PN} += "already-stripped"
