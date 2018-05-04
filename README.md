<img align="center" src="https://github.com/ryanwarsaw/coach-erevu/blob/master/app/src/main/res/drawable/logo.png">

### Installation

**Prerequisites**

- [Make sure your device allows app installs from unknown sources.](https://www.applivery.com/docs/troubleshooting/android-unknown-sources)
- [Enable USB debugging via Developer Options on your device.](https://www.howtogeek.com/129728/how-to-access-the-developer-options-menu-and-enable-usb-debugging-on-android-4.2/)
- [Install Android File Transfer on an easily accessible laptop/desktop](https://www.android.com/filetransfer/)

**Install**

Coach Erevu is distributed as an APK file (ending in `.apk`), to install the app you need to move this
file to your Android device by downloading it from the internet. Once you have done so, you can start the
installation by double tapping the installed file. Please make sure you satisfied all pre-requisites first.

Visit [here](https://github.com/ryanwarsaw/coach-erevu/releases) to download the latest version of the APK file.

### Content

By default Coach Erevu will use the content files provided to it when the app was originally compiled, these files are
embedded within the app and can't be changed. To use a custom content file (including videos) you must place these files
into the `Download/` folder on the target Android device. Then restart the app, and it should default to the new file instead.

The content file should be named `content.json` and both the content and video files should be in the root directory of the
`Download/` folder, and not within any other folders. All files must be in uncompressed form (no ZIP, etc).

All content quiz questions fall under two categories: `multiple-choice` and `free-text`. To add a `multiple-choice` question
without a correct/incorrect answer, don't set the `correct-answer` property within the question format. Additionally, when you
set the `correct-answer` property, do not use a zero-based file (i.e. four questions = 0..1..2..3) it should be (1..2..3..4).

Please remember to increment the `version` property with each content change you make, so that these changes are properly reflected
within the analytics file. This will not cause an error, but it will make it hard to aggregate data at a later date. See the format used for the content file [here](https://github.com/ryanwarsaw/coach-erevu/blob/master/app/src/main/res/raw/content.json).




