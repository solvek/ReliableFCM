const functions = require("firebase-functions");

const admin = require('firebase-admin');
admin.initializeApp();

function sendPush(token){
    const message = {
        android: {
            priority: "high"
        },
        data: {
            'reliableId': new Date().getTime().toString()
          },
        token: token
    };

    const response = admin.messaging().send(message)
        .then((response) => {
          functions.logger.log('Notification has been sent and tokens cleaned up. '+response);
        })
        .catch((error) => {
            functions.logger.log('Error sending message:'+error);
        });
}

exports.sendPush = functions.https.onRequest((request, response) => {
  functions.logger.info("Hello logs!", {structuredData: true});
  const token = request.query.token
  sendPush(token)
  response.send("Hello from Firebase!");
});

exports.periodicPush = functions.pubsub.schedule('every 30 minutes').onRun((context) => {
 console.log('Tick');
 sendPush("cpyYtqXmSJqJjm_c1vprhW:APA91bGtPnC5JK7XWhCSkBKSl0PNUuAEkqVKU_gwneuArmKO9Bdh6qR1kMyCPg6CvPNeRumMCXjLdpBDIWh3iCzkdbUS2R3CmK3HQDC5gOmaReP37jbPNdwTDuxQTVh9W5RbFDYfoNIk")
 return null;
});