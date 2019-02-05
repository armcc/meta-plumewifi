# meta-plumewifi

This meta layer provides OpenEmbedded recipes to build [plume-pml](https://github.com/plume-design/plume-pml) and its dependencies:

```
jansson 2.7
libev 4.19
mosquitto 1.4.8
openvswitch 2.3.90
protobuf 2.6.1
protobuf-c 1.1.1
curl <latest release>
openssl <latest 1.0.2 release>
```

This layer is intended to be compatible with ALL releases of OpenEmbedded from 1.6 (Daisy) onwards.

# Getting Started

```shell
git clone git://github.com/openembedded/openembedded-core.git -b sumo
git clone git://github.com/openembedded/bitbake.git openembedded-core/bitbake -b 1.38
git clone git://github.com/armcc/meta-plumewifi.git

source ./openembedded-core/oe-init-build-env
```

Add meta-plumewifi to the set of active meta layers:

```shell
bitbake-layers add-layer ../meta-plumewifi
```

Define persistent downloads and sstate-cache directories in local.conf (optional):

```shell
mkdir -p ${HOME}/oe
echo 'DL_DIR = "${HOME}/oe/downloads"' >> conf/local.conf
echo 'SSTATE_DIR = "${HOME}/oe/sstate-cache"' >> conf/local.conf
```

Build:

```shell
bitbake plume-pml
```

The result of building the plume-pml recipe will be an installable .ipk package. Normally plume-pml would be included in a rootfs image as the result of building an OpenEmbedded image recipe, however during development it may be useful to extract and examine the contents of the plume-pml .ipk manually, e.g.:

```shell
ar -xv tmp-glibc/deploy/ipk/i586/plume-pml_*_i586.ipk
tar -tvf data.tar.*

drwxrwxrwx root/root         0 2018-09-21 14:58 ./
drwxr-xr-x root/root         0 2018-09-21 14:58 ./usr/
drwxr-xr-x root/root         0 2018-09-21 14:58 ./usr/plume/
drwxr-xr-x root/root         0 2018-09-21 14:58 ./usr/plume/tools/
-rwxr-xr-x root/root     35004 2018-09-21 14:58 ./usr/plume/tools/ovsh
-rwxr-xr-x root/root      2354 2018-09-21 14:58 ./usr/plume/tools/lm_log_pull.sh
-rwxr-xr-x root/root        71 2018-09-21 14:58 ./usr/plume/tools/restart.sh
-rwxr-xr-x root/root       128 2018-09-21 14:58 ./usr/plume/tools/delayed-restart.sh
drwxr-xr-x root/root         0 2018-09-21 14:58 ./usr/plume/certs/
-rw-r--r-- root/root      5867 2018-09-21 14:58 ./usr/plume/certs/client.pem
-rw-r--r-- root/root      1758 2018-09-21 14:58 ./usr/plume/certs/awsca.pem
-rw-r--r-- root/root      2411 2018-09-21 14:58 ./usr/plume/certs/ca.pem
-rw-r--r-- root/root      5509 2018-09-21 14:58 ./usr/plume/certs/upload.pem
-rw-r--r-- root/root      3243 2018-09-21 14:58 ./usr/plume/certs/client_dec.key
drwxr-xr-x root/root         0 2018-09-21 14:58 ./usr/plume/lib/
-rwxr-xr-x root/root    565484 2018-09-21 14:58 ./usr/plume/lib/libplume.so
-rw-r--r-- root/root       202 2018-09-21 14:58 ./usr/plume/.versions
drwxr-xr-x root/root         0 2018-09-21 14:58 ./usr/plume/bin/
-rwxr-xr-x root/root     67372 2018-09-21 14:58 ./usr/plume/bin/sm
-rwxr-xr-x root/root     17948 2018-09-21 14:58 ./usr/plume/bin/lm
-rwxr-xr-x root/root     26212 2018-09-21 14:58 ./usr/plume/bin/qm
-rwxr-xr-x root/root     50936 2018-09-21 14:58 ./usr/plume/bin/wm
-rwxr-xr-x root/root     13728 2018-09-21 14:58 ./usr/plume/bin/qm_cli
-rwxr-xr-x root/root     51148 2018-09-21 14:58 ./usr/plume/bin/cm
-rwxr-xr-x root/root     22128 2018-09-21 14:58 ./usr/plume/bin/nm
-rwxr-xr-x root/root     30484 2018-09-21 14:58 ./usr/plume/bin/dm
-rwxr-xr-x root/root     50728 2018-09-21 14:58 ./usr/plume/bin/wifihal_test
drwxr-xr-x root/root         0 2018-09-21 14:58 ./usr/plume/etc/
-rw-r--r-- root/root    125642 2018-09-21 14:58 ./usr/plume/etc/plume.ovsschema
-rw-r--r-- root/root     42206 2018-09-21 14:58 ./usr/plume/etc/conf.db.bck
-rw-r--r-- root/root        58 2018-09-21 14:58 ./usr/plume/.version
drwxr-xr-x root/root         0 2018-09-21 14:58 ./etc/
drwxr-xr-x root/root         0 2018-09-21 14:58 ./etc/init.d/
-rwxr-xr-x root/root       250 2018-09-21 14:58 ./etc/init.d/target_hooks
-rwxr-xr-x root/root      2071 2018-09-21 14:58 ./etc/init.d/managers
```

# Alternative instructions for building with OE 1.6 (Daisy)

OE 1.6 was released in Apr 2014. It is no longer maintained upstream and requires various fixes to build cleanly. The steps described below are based on the unofficial lgirdk "daisy-next" branch of openembedded-core which contains these fixes.

Note that building on recent host distro may not work (upto Ubuntu 16.04 should be OK).

```shell
git clone git://github.com/lgirdk/openembedded-core.git -b daisy-next
git clone git://github.com/openembedded/bitbake.git openembedded-core/bitbake -b 1.22
git clone git://github.com/armcc/meta-plumewifi.git

source ./openembedded-core/oe-init-build-env
```

Add meta-plumewifi to the set of active meta layers (use sed as OE 1.6 lacks "bitbake-layers add-layer"):

```shell
sed '/meta-plumewifi /d; /meta /a\  '$(cd ../meta-plumewifi && pwd)' \\' -i conf/bblayers.conf
```

Break an unnecessary default dependency on libsdl from the host:

```shell
echo 'PACKAGECONFIG_pn-qemu-native_remove = "sdl"' >> conf/local.conf
echo 'PACKAGECONFIG_pn-nativesdk-qemu_remove = "sdl"' >> conf/local.conf
sed '/libsdl-native/d' -i conf/local.conf
```

Define persistent downloads and sstate-cache directories in local.conf (optional):

```shell
mkdir -p ${HOME}/oe
echo 'DL_DIR = "${HOME}/oe/downloads"' >> conf/local.conf
echo 'SSTATE_DIR = "${HOME}/oe/sstate-cache"' >> conf/local.conf
```

Build:

```shell
bitbake plume-pml
```

