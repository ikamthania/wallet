#!/usr/bin/env bash


# exit to root folder required for code deploy service as it does not operate on project root but on folder root
cd ../..

# create distribution
sbt "project webGateway" "dist"

# unzip the web gateway dist

yes | unzip ./web-gateway/target/universal/webgateway*.zip -d ./temporary


# unzip the dist jar
yes | unzip ./temporary/lib/Livelygig.webgateway-0.3.jar -d ./temporary/

rm -rf ./wallet-native-client/android/app/src/main/assets/javascripts/
# copy main stylesheet

yes | cp ./temporary/META-INF/resources/webjars/webgateway/0.3/stylesheets/wallet/wallet-app-main.min.css -d ./wallet-native-client/android/app/src/main/assets/stylesheets

# copy theme stylesheet
yes | cp ./temporary/META-INF/resources/webjars/webgateway/0.3/stylesheets/wallet/themes/{wallet-main-theme-default.min.css,wallet-main-theme-light.min.css} -d ./wallet-native-client/android/app/src/main/assets/stylesheets


# copy javascripts
yes | cp ./temporary/META-INF/resources/webjars/webgateway/0.3/{wallet-client-opt-library.js,wallet-client-opt.js} -d ./wallet-native-client/android/app/src/main/assets/javascripts/

# enter wallet-native directory
cd wallet-native-client/

# npm install
yarn install

# create bundle
react-native bundle --platform android --dev false --entry-file index.js --bundle-output android/app/src/main/assets/index.android.bundle --assets-dest android/app/src/main/res

# enter android folder
cd android

# gradle assemble debug
./gradlew assembleDebug

# gradle assemble release
#./gradlew assembleRelease