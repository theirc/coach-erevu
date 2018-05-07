<img align="center" src="https://i.imgur.com/7K5cAzO.png">

## Setup

- [Make sure your device allows app installs from unknown sources.](https://www.applivery.com/docs/troubleshooting/android-unknown-sources) This will let you install this app without the Google Play Store.
- [Install Android File Transfer on an easily accessible laptop/desktop.](https://www.android.com/filetransfer/) This will let you update content without rebuilding the app.
- Optionally, [enable USB debugging via Developer Options on your device.](https://www.howtogeek.com/129728/how-to-access-the-developer-options-menu-and-enable-usb-debugging-on-android-4.2/)

## Installation

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

#### Changing content
If you need to adjust the language in a certain question, add a question, change a title, etc., all of that can be done be editing `content.json`. If you don't yet have a copy of this file, go [here](https://github.com/ryanwarsaw/coach-erevu/blob/master/app/src/main/res/raw/content.json) and download it.

##### To change `content.json`
1. Open `content.json` in a text editor (Notepad, or TextEdit will work).
2. Find the text you want to change, change it, and save the file. (See the **Formatting** section below for how to do so.)
3. If you made any structural changes, you may want to paste the entirety of the file into https://jsonlint.com/ and click "Validate JSON". JSON is a way to format data so that a machine can read it, so it needs to be very specifically crafted. Validators like the one aforementioned help make sure.
4. On your laptop, and open **Android File Transfer** (see above for download link).
5. In Android File Transfer, find the `Download` folder.
6. On your laptop, find the `content.json` file.
7. Drag `content.json` from your laptop and into the `Download` folder **on Android File Transfer**.
8. Restart the app on your Android device.

##### To add or change a video
The video for each week is referenced within `content.json`. And, by default, most weeks use `week1.mp4`. So, if you are adding or changing a video that is not `week1.mp4`, check `content.json` to make sure the app will know about your change. For instance, if you upload `week3.mp4`, make sure the `Activity 3` section of `content.json` refernces `week3.mp4` and not week1.mp4.

1. On your laptop, and open **Android File Transfer** (see above for download link).
3. In Android File Transfer, find the `Download` folder.
4. On your laptop, find the video file you want to upload.
5. Drag the video file from your laptop and into the `Download` folder **on Android File Transfer**.
6. If you need to update `content.json` as a result of the change you made, see the above section.
7. Restart the app on your Android device.

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


