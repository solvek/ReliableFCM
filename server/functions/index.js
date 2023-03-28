const functions = require("firebase-functions");

const admin = require('firebase-admin');
admin.initializeApp();

function sendPush(token, type){
    const message = {
        android: {
            priority: "high"
        },
        data: {
            'reliableId': new Date().getTime().toString(),
            'type': type
          },
        token: token
    };

    functions.logger.log('Sending push to '+token);
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
  sendPush(token, "Manual")
  response.send("Hello from Firebase!");
});

exports.periodicPush = functions.pubsub.schedule('every 30 minutes').onRun((context) => {
    functions.logger.log('Periodic task started');
    var db = admin.firestore();

    const tokens = db.collection('tokens');
    const snapshot = citiesRef.get()
        .then((snapshot) => {
             if (snapshot.empty) {
              functions.logger.log('Not found any tokens');
              return;
            }

            snapshot.forEach(doc => {
                sendPush(doc.token, "Automatic");
            });
        })
        .catch((error) => {
            functions.logger.log('Could not get tokens:'+error);
        });
    return null;
});