
#!/bin/bash
cd /home/ubuntu/work/livelygig/test-deployment/wallet

array=("walletImpl:walletService" "webGateway:webGateway")

#For loop in order to create binaries of services

for i in "${array[@]}";
do
        impl=${i%%:*}
        sbt "project $impl" "dist"
done
cp -a -fr  /home/ubuntu/work/livelygig/test-deployment/wallet/scripts/deployment-scripts/.  /home/ubuntu/work/livelygig/test-deployment/deployment/
find . -name '*.zip' -exec mv --target-directory='./' '{}' +

for i in "${array[@]}";
do
        impl=${i%%:*}
        service=${i#*:}
        (unzip -o  ${impl,,}*.zip   -d /tmp/deployment)
        folderName=${impl,,}*

if [ "$impl" == "webGateway" ];
then
cp -Rf /tmp/deployment/bin  /home/ubuntu/work/livelygig/test-deployment/deployment/$service
cp -Rf /tmp/deployment/lib  /home/ubuntu/work/livelygig/test-deployment/deployment/$service
else
cp -a -fr   /tmp/deployment/$folderName/.  /home/ubuntu/work/livelygig/test-deployment/deployment/$service
fi
done

#Clearing  tmp folder and distribution binaries

rm -rf /tmp/deployment/*

rm *.zip