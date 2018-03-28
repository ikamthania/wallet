#!/bin/bash

cd /home/ubuntu/work/livelygig/test-deployment/deployment/
chmod -R +x *
sh deployAll.sh restart

cd /home/ubuntu/work/livelygig/test-deployment/wallet/scripts/apk_build_script/
chmod -R +x *

export ANDROID_HOME=/home/ubuntu/work/tools/android_sdk/
export PATH=$PATH:$ANDROID_HOME/tools

sh create_apk.sh
sh move_apk.sh