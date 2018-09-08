# meta-plumewifi

This layer provides OpenEmbedded recipes to build [plume-pml](https://github.com/plume-design/plume-pml) and its dependencies:

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

This meta layer is intended to be compatible with ALL releases of OpenEmbedded from 1.6 (Daisy) onwards.

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
ar -xv tmp-glibc/deploy/ipk/i586/plume-pml_1.0.0.0-r0_i586.ipk
tar -tvf data.tar.xz

drwxrwxrwx root/root         0 2018-09-07 16:51 ./
drwxr-xr-x root/root         0 2018-09-07 16:51 ./usr/
drwxr-xr-x root/root         0 2018-09-07 16:51 ./usr/share/
drwxr-xr-x root/root         0 2018-09-07 16:51 ./usr/share/openvswitch/
-rw-r--r-- root/root    110645 2018-09-07 16:51 ./usr/share/openvswitch/plume.ovsschema
drwxr-xr-x root/root         0 2018-09-07 16:51 ./usr/plume/
drwxr-xr-x root/root         0 2018-09-07 16:51 ./usr/plume/tools/
-rwxr-xr-x root/root     26824 2018-09-07 16:51 ./usr/plume/tools/ovsh
-rwxr-xr-x root/root      2189 2018-09-07 16:51 ./usr/plume/tools/lm_log_pull.sh
-rwxr-xr-x root/root        71 2018-09-07 16:51 ./usr/plume/tools/restart.sh
-rwxr-xr-x root/root       128 2018-09-07 16:51 ./usr/plume/tools/delayed-restart.sh
drwxr-xr-x root/root         0 2018-09-07 16:51 ./usr/plume/certs/
-rw-r--r-- root/root      5867 2018-09-07 16:51 ./usr/plume/certs/client.pem
-rw-r--r-- root/root      1758 2018-09-07 16:51 ./usr/plume/certs/awsca.pem
-rw-r--r-- root/root      2411 2018-09-07 16:51 ./usr/plume/certs/ca.pem
-rw-r--r-- root/root      5509 2018-09-07 16:51 ./usr/plume/certs/upload.pem
-rw-r--r-- root/root      3243 2018-09-07 16:51 ./usr/plume/certs/client_dec.key
drwxr-xr-x root/root         0 2018-09-07 16:51 ./usr/plume/lib/
-rwxr-xr-x root/root    403884 2018-09-07 16:51 ./usr/plume/lib/libplume.so
drwxr-xr-x root/root         0 2018-09-07 16:51 ./usr/plume/bin/
-rwxr-xr-x root/root     63240 2018-09-07 16:51 ./usr/plume/bin/sm
-rwxr-xr-x root/root     13880 2018-09-07 16:51 ./usr/plume/bin/lm
-rwxr-xr-x root/root     18048 2018-09-07 16:51 ./usr/plume/bin/qm
-rwxr-xr-x root/root     26244 2018-09-07 16:51 ./usr/plume/bin/wm
-rwxr-xr-x root/root      9660 2018-09-07 16:51 ./usr/plume/bin/qm_cli
-rwxr-xr-x root/root     22240 2018-09-07 16:51 ./usr/plume/bin/cm
-rwxr-xr-x root/root     13892 2018-09-07 16:51 ./usr/plume/bin/nm
-rwxr-xr-x root/root     22304 2018-09-07 16:51 ./usr/plume/bin/dm
-rwxr-xr-x root/root     46676 2018-09-07 16:51 ./usr/plume/bin/wifihal_test
drwxr-xr-x root/root         0 2018-09-07 16:51 ./etc/
drwxr-xr-x root/root         0 2018-09-07 16:51 ./etc/openvswitch/
-rw-r--r-- root/root     37839 2018-09-07 16:51 ./etc/openvswitch/conf.db.bck
drwxr-xr-x root/root         0 2018-09-07 16:51 ./etc/init.d/
-rwxr-xr-x root/root      2409 2018-09-07 16:51 ./etc/init.d/managers
```

