<img align="center" src="https://i.imgur.com/7K5cAzO.png">

## Installation

**Prerequisites**

- [Make sure your device allows app installs from unknown sources.](https://www.applivery.com/docs/troubleshooting/android-unknown-sources)
- [Enable USB debugging via Developer Options on your device.](https://www.howtogeek.com/129728/how-to-access-the-developer-options-menu-and-enable-usb-debugging-on-android-4.2/)
- [Install Android File Transfer on an easily accessible laptop/desktop](https://www.android.com/filetransfer/)

**Install**

Coach Erevu is distributed as an APK file (ending in `.apk`). To install the app you need to move this
file to your Android device by downloading it from the internet. Once you have done so, you can start the
installation by double tapping the installed file. Please make sure you satisfied all pre-requisites first.

Visit [here](https://github.com/ryanwarsaw/coach-erevu/releases) to download the latest version of the APK file.

You can put a copy of this on your device by
* Going to the above URL directly with the browser on the Android device, or
* Going to the above URL on your laptop and then using **Android File Transfer** to put it on your Android device

## Content

### Custom content
By default Coach Erevu will use the content files provided to it when the app was originally compiled. These files are
embedded within the app and can't be changed. However, the app looks inside the `Download/` folder on its host device to check for updated content. To use custom content files (including videos), place files in the `Download/` folder on the target Android device. When the app is restarted, it should use those files instead.

The content file should be named `content.json` and both the content and video files should be in the root directory of the
`Download/` folder, and not within any other folders. All files must be in uncompressed form (no ZIP, etc). For example:
```
  Download/
     content.json
     video1.mp4
     video2.mp4
     video3.mp4
     video4.mp4
```

### Formatting
All content quiz questions fall under two categories: `multiple-choice` and `free-text`. `multiple-choice` question
don't need to have a correct answer, but if one is desired, set the `correct-answer` property within the question format. **Additionally, when you set the `correct-answer` property, do not use a zero-based index** (i.e. four questions = 0..1..2..3) it should be (1..2..3..4). 

Please remember to increment the `version` property with each content change you make, so that these changes are properly reflected
within the analytics file. This will not cause an error, but it will make it hard to aggregate data at a later date. See the format used for the content file [here](https://github.com/ryanwarsaw/coach-erevu/blob/master/app/src/main/res/raw/content.json).

Example:
```
{
  "version": 0.1,
  "weeks": [
    {
      "id": "0",
      "title": "Test",
      "topic": "Testing",
      "video": "test.mp4",
      "questions": [
        {
          "question": "What is your favourite animal?",
          "answer-type": "multiple-choice",
          "answers": [
            "Bear",
            "Hippo",
            "Cat"
          ]
        },
        {
          "question": "How many are in a dozen?",
          "answer-type": "multiple-choice",
          "answers": [
            "8",
            "12",
            "1200"
          ],
          "correct-answer": 1
        },        
        {
          "question": "How do you feel today?",
          "answer-type": "free-text"
        }
      ]
    }
  ]
}
```


