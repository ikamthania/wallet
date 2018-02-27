# Dev environment setup

### Installing dependencies

You will need Node, the React Native command line interface, a JDK, and Android Studio.

While you can use any editor of your choice to develop your app, you will need to install Android Studio in order to set up the necessary tooling to build your React Native app for Android.
Node

Follow the installation instructions for your Linux distribution to install Node 6 or newer.
The React Native CLI

Node comes with npm, which lets you install the React Native command line interface.

Run the following command in a Command Prompt or shell:

npm install -g react-native-cli

    If you get an error like Cannot find module 'npmlog', try installing npm directly: curl -0 -L https://npmjs.org/install.sh | sudo sh.

Java Development Kit

React Native requires a recent version of the Java SE Development Kit (JDK). Download and install JDK 8 or newer if needed.
Android development environment

Setting up your development environment can be somewhat tedious if you're new to Android development. If you're already familiar with Android development, there are a few things you may need to configure. In either case, please make sure to carefully follow the next few steps.
1. Install Android Studio

Download and install Android Studio. Choose a "Custom" setup when prompted to select an installation type. Make sure the boxes next to all of the following are checked:

    Android SDK
    Android SDK Platform
    Android Virtual Device

Then, click "Next" to install all of these components.

    If the checkboxes are grayed out, you will have a chance to install these components later on.

Once setup has finalized and you're presented with the Welcome screen, proceed to the next step.
2. Install the Android SDK

Android Studio installs the latest Android SDK by default. Building a React Native app with native code, however, requires the Android 6.0 (Marshmallow) SDK in particular. Additional Android SDKs can be installed through the SDK Manager in Android Studio.

The SDK Manager can be accessed from the "Welcome to Android Studio" screen. Click on "Configure", then select "SDK Manager".

    The SDK Manager can also be found within the Android Studio "Preferences" dialog, under Appearance & Behavior → System Settings → Android SDK.

Select the "SDK Platforms" tab from within the SDK Manager, then check the box next to "Show Package Details" in the bottom right corner. Look for and expand the Android 6.0 (Marshmallow) entry, then make sure the following items are all checked:

    Google APIs
    Android SDK Platform 23
    Intel x86 Atom_64 System Image
    Google APIs Intel x86 Atom_64 System Image

Next, select the "SDK Tools" tab and check the box next to "Show Package Details" here as well. Look for and expand the "Android SDK Build-Tools" entry, then make sure that 23.0.1 is selected.

Finally, click "Apply" to download and install the Android SDK and related build tools.
3. Configure the ANDROID_HOME environment variable

The React Native tools require some environment variables to be set up in order to build apps with native code.

Add the following lines to your $HOME/.bash_profile config file:

export ANDROID_HOME=$HOME/Android/Sdk
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools

    .bash_profile is specific to bash. If you're using another shell, you will need to edit the appropriate shell-specific config file.

Type source $HOME/.bash_profile to load the config into your current shell. Verify that ANDROID_HOME has been added to your path by running echo $PATH.

    Please make sure you use the correct Android SDK path. You can find the actual location of the SDK in the Android Studio "Preferences" dialog, under Appearance & Behavior → System Settings → Android SDK.

Watchman (optional)

Follow the Watchman installation guide to compile and install Watchman from source.

    Watchman is a tool by Facebook for watching changes in the filesystem. It is highly recommended you install it for better performance, but it's alright to skip this if you find the process to be tedious.

On windows follow instruction watchman download section https://facebook.github.io/watchman/docs/install.html#download-for-windows-beta

On OSX
```
brew update
brew install watchman
```
Linux requires some more setup, follow this blog post https://saintcoder.wordpress.com/2017/03/23/how-to-install-facebooks-watchman-on-linux-ubuntu-16-04-lts/ to configure it

### Note

There are some system specific requirement for watchman. https://facebook.github.io/watchman/docs/install.html#system-specific-preparation explains it.

On Linux this is related to Linux inotify Limits. Above link explains how that can be resolved. For most part, in Linux setup this command should suffice

```
sudo sysctl fs.inotify.max_user_watches=100000
```

Install sbt

Installing sbt on Mac

Homebrew

    $ brew install sbt@1

Macports

    $ port install sbt

Installing sbt on Windows

Installing from a universal package

Download [ZIP](https://github.com/sbt/sbt/releases/download/v1.1.1/sbt-1.1.1.zip) or [TGZ](https://github.com/sbt/sbt/releases/download/v1.1.1/sbt-1.1.1.tgz) package and expand it.
Windows installer

Download [msi installer](https://github.com/sbt/sbt/releases/download/v1.1.1/sbt-1.1.1.msi) and install it.

On Linux

```
echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823
sudo apt-get update
sudo apt-get install sbt
```


2. Once packages are installed in project root do `sbt runAll` This starts the microservices, web gateway, scalajs-react project compilation

3. In separate terminal `cd wallet-native-client` and do `npm install // you can use yarn as well` and `npm start // can use yarn as well`

What this command does can be found [here](/wallet-native-client/package.json#L12)

4. We have used another config file, you can see it (here)[/wallet-native-client/App.js#L3] . To use that there is a comment in header in this file . It reads `// DO NOT USE THIS FILE DIRECTLY CREATE A COPY TO config.json MODIFY REQUIRED VARIABLES`.
You might ask why would I need to put ip, can't I put just localhost, answer is you can't. Because the emulator or the expo client doesn't know what localhost server is.

6. Optionally to enable the hot reloading with scala sources there is a small watchman script
`watchman-make --run 'echo "//" > wallet-native-client/trigger.js' -p '**/*.scala'`
Use this script in your project root.
It configures watchman to read all scala sources with `**/* .scala` and once it sees a change it triggers to make trigger.js dirty with `'echo "//" > wallet-native-client/trigger.js'` notice it just add blank comment. It basically tells the watchman which is running in the wallet-native-client that something has changed in scala sources, you need to reload.

7. To debug use an android emulator.