SUMMARY = "CBN provided link dependencies for Plume PML (prebuilt)"
LICENSE = "CLOSED"

# We don't need the full ch7465ce SDK repo (which contains Buildroot sources
# etc, etc) but it's a convenient way to fetch a well defined version of the
# CBN prebuilt libs.

SRCREV = "84842a3739237dcffcff41bd1846e7940fbc1881"

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
# run-time package (and keep them out of the -dev package).

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/*.so ${base_libdir}/*.so"

# All binaries have already been stripped

INSANE_SKIP_${PN} += "already-stripped"
