# ReliableFCM
Android app for testing reliable push notifications via Firebase

# Integrate with your Firebase project

1. Create a Firebase project in [Google Firebase Console](https://console.firebase.google.com/)
2. Open Project Settings
3. Add Android App
4. Download file `google-services.json`
5. Store it into directory `app` of the android project

![Copy google-services.json](doc/googleservices.png)

# Send push notification using a python script

[More details in documentation](https://firebase.google.com/docs/cloud-messaging/send-message)

## Add a service account

Do this only once

1. Open `Project settings` of your project on the Firebase Console
2. Open `Service accounts` tab 
3. Tap on `Generate new private key'
4. Save the file `doc` directory of the project and rename it to `serviceacc.json`

## Find registration token from your android app installation

Usually you need do this once for each single installation however server can change the registration token

1. Start the app
2. In logcat find message `Firebase token:...`
3. Get the token value

## Run python notebook using Google Colab

1. [Open the notebook](https://colab.research.google.com/drive/1qqSnWrt0X4iti273Y-XBSkh33V6hJdS6?usp=sharing)
2. Upload `doc/serviceacc.json` to `/content` directory
3. Set `registration_token` to actual value
4. Run script to send the notification (`Ctrl-F9`)
