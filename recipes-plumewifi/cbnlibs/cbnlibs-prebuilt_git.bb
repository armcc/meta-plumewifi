SUMMARY = "CBN provided link dependencies for Plume PML (prebuilt)"
LICENSE = "CLOSED"

PV = "0.0+git${SRCPV}"

# We don't need the full ch7465ce SDK repo (which contains Buildroot sources
# etc, etc) but it's a convenient way to fetch a well defined version of the
# CBN prebuilt libs.

# Warning: Builds created from recipes using AUTOREV are not reproducible
# and so should be used for development only (not for creating releases).

SRCREV = "${AUTOREV}"

SRC_URI = "${PLUME_GIT}/sdk-lgi-isdk-ch7465ce.git;protocol=ssh"

S = "${WORKDIR}/git"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    cp -av --no-preserve=ownership ${S}/contrib/libs/* ${D}/

    # Remove any prebuilt libiosf.so libs since they clash with the ones
    # from the Intel SDK. Nothing in Opensync links directly with them
    # anyway.

    rm -f ${D}/lib/libiosf.so*
}

# Prebuilt libs are for x86

COMPATIBLE_HOST = "(i.86|x86_64).*-linux"

# The CBN shared libs aren't versioned, so force the .so files into the
# run-time package (and keep them out of the -dev package).

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${base_libdir}/*.so"

# All binaries have already been stripped

INSANE_SKIP_${PN} += "already-stripped"
