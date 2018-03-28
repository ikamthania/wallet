#!/bin/bash

cd /home/ubuntu/work/livelygig/test-deployment/deployment/
chmod -R +x *
sh deployAll.sh restart

cd /home/ubuntu/work/livelygig/test-deployment/wallet/scripts/apk_build_script/
chmod -R +x *
sh create_apk.sh