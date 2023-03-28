# ReliableFCM
Android app for testing reliable push notifications via Firebase

# Integrate with your Firebase project

1. Create a Firebase project in [Google Firebase Console](https://console.firebase.google.com/)
2. Open Project Settings
3. Add Android App
4. Download file `google-services.json`
5. Store it into directory `app` of the android project

![Copy google-services.json](doc/googleservices.png)

# Determine the *registration token* for your android app installation

Usually you need do this once for each single installation however server can change the registration token

1. Start the app
2. In logcat find message `Firebase token:...`
3. Get the token value

# Send push notifications using Firebase server

[Cloud Functions details](https://firebase.google.com/docs/functions/get-started)

1. [Install firebase Firebase CLI](https://firebase.google.com/docs/functions/get-started#set-up-node.js-and-the-firebase-cli)
2. CD to directory `server`
3. Log into your firebase: `firebase login`
4. Deploy cloud functions: `firebase deploy --only functions`
5. In Firebase Console, section Functions you'll see url to `sendPush` function
6. You can use url in web browser to perform request the request should be like this `https://<YOUR_PROJECT>.cloudfunctions.net/sendPush?token=<REGISTRATION_TOKEN>`
7. Set valid values for `<YOUR_PROJECT>` and `<REGISTRATION_TOKEN>`

## Periodic push notifications

Cloud function `periodicPush` sends push notification to a device. The registration token is hardcoded for now.

# Send push notification using a python script

[More details in documentation](https://firebase.google.com/docs/cloud-messaging/send-message)

## Add a service account

1. Open `Project settings` of your project on the Firebase Console
2. Open `Service accounts` tab
3. Tap on `Generate new private key'
4. Save the file `doc` directory of the project and rename it to `serviceacc.json`

## Run python notebook using Google Colab

1. [Open the notebook](https://colab.research.google.com/drive/1qqSnWrt0X4iti273Y-XBSkh33V6hJdS6?usp=sharing)
2. Upload `doc/serviceacc.json` to `/content` directory
3. Set `registration_token` to actual value
4. Run script to send the notification (`Ctrl-F9`)
