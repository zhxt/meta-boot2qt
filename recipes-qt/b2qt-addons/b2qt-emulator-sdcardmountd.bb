##############################################################################
##
## Copyright (C) 2016 The Qt Company Ltd.
## Contact: http://www.qt.io/licensing/
##
## This file is part of the Boot to Qt meta layer.
##
## $QT_BEGIN_LICENSE:COMM$
##
## Commercial License Usage
## Licensees holding valid commercial Qt licenses may use this file in
## accordance with the commercial license agreement provided with the
## Software or, alternatively, in accordance with the terms contained in
## a written agreement between you and The Qt Company. For licensing terms
## and conditions see http://www.qt.io/terms-conditions. For further
## information use the contact form at http://www.qt.io/contact-us.
##
## $QT_END_LICENSE$
##
##############################################################################

DESCRIPTION = "SD-Card mount daemon for Emulator"
LICENSE = "QtEnterprise"
LIC_FILES_CHKSUM = "file://sdcardmountd.h;md5=709ec9e3c40137fa6068a903c57acdc1;beginline=1;endline=17"

inherit qmake5

SRC_URI = " \
    git://codereview.qt-project.org/tqtc-boot2qt/emulator;branch=${BRANCH};protocol=ssh \
    file://sdcardmountd.sh \
    file://sdcardmount.service \
    "

SRCREV = "ab4a9f13831f44f3dcd375fa17ed7e102647febe"
BRANCH = "master"

S = "${WORKDIR}/git/src/helperlibs/sdcarddaemon"

DEPENDS = "qtbase qtsimulator"

do_install_append() {
    install -m 0755 -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/sdcardmountd.sh ${D}${sysconfdir}/init.d/

    install -m 0755 -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/sdcardmount.service ${D}${systemd_unitdir}/system/
}

INITSCRIPT_NAME = "sdcardmountd.sh"
INITSCRIPT_PARAMS = "defaults 97 10"

SYSTEMD_SERVICE_${PN} = "sdcardmount.service"

inherit update-rc.d systemd
