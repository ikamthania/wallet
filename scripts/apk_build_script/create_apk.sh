#!/usr/bin/env bash


# exit to root folder
cd ../..

# create distribution
sbt "project webGateway" "dist"

# unzip the web gateway dist

unzip ./web-gateway/target/universal/webgateway*.zip -d ./temporary


# unzip the dist jar
unzip ./temporary/lib/Livelygig.webgateway-0.3.jar -d ./temporary/


# copy main stylesheet

cp ./temporary/META-INF/resources/webjars/webgateway/0.3/stylesheets/wallet/wallet-app-main.min.css -d ./wallet-native-client/android/app/src/main/assets/stylesheets

# copy theme stylesheet
cp ./temporary/META-INF/resources/webjars/webgateway/0.3/stylesheets/wallet/themes/{wallet-main-theme-default.min.css,wallet-main-theme-light.min.css} -d ./wallet-native-client/android/app/src/main/assets/stylesheets


# copy javascripts
cp ./temporary/META-INF/resources/webjars/webgateway/0.3/{wallet-client-opt-library.js,wallet-client-opt.js} -d ./wallet-native-client/android/app/src/main/assets/javascripts/

# enter wallet-native directory
cd wallet-native-client/

# create bundle
react-native bundle --platform android --dev false --entry-file index.js --bundle-output android/app/src/main/assets/index.android.bundle --assets-dest android/app/src/main/res

# enter android folder
cd android

# gradle assemble debug
#./gradlew assembleDebug

# gradle assemble release
./gradlew assembleRelease