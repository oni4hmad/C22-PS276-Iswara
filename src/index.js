// Initialize Firestore
const admin = require("firebase-admin");
const serviceAccount = require('./serviceAccountKey.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://iswara-project-default-rtdb.asia-southeast1.firebasedatabase.app"
});

const db = admin.firestore();

// try to get all document from collection Users
const customerRef = db.collection("Users");
customerRef.get().then((querySnapshot) => {
  querySnapshot.forEach(document => {
    console.log(document.data());
  })
})

// write to firestore
const data = {
  Email: "Okta@gmail.com",
  Body: "Cerita blablabla",
}

const data1 = {
  Name: "Viaa",
  Email: "Via@gmail.com",
  Password: "Via123",
  PhoneNum: 75434567
}

const data2 = {
  Name: "Rahma",
  Email: "Rahma@gmail.com",
  Password: "Rahma123",
  PhoneNum: 75434567
}

const data3 = {
  Name: "Cala",
  Email: "Cala@gmail.com",
  Password: "Cala123",
  PhoneNum: 75434567
}

const data4 = {
  Name: "Safitri",
  Email: "Safitri@gmail.com",
  Password: "Safitri123",
  PhoneNum: 75434567
}

const newStory = {
  Name: "Safitri",
  Body: "blablablablabla"
}

db.collection('Story').add;

db.collection("Story").doc(data.Email.toString()).set(data);
db.collection("Users").doc(data1.Email.toString()).set(data1);
db.collection("Users").doc(data2.Email.toString()).set(data2);
db.collection("Users").doc(data3.Email.toString()).set(data3);
db.collection("Users").doc(data4.Email.toString()).set(data4);

// create a new write batch
const batch = db.batch();

const customer3 = db.collection("Customers").doc("3");
const customer4 = db.collection("Customers").doc("4");
const customer5 = db.collection("Customers").doc("5");
const customer6 = db.collection("Customers").doc("6");
const customer7 = db.collection("Customers").doc("7");
const customer8 = db.collection("Customers").doc("8");
const customer9 = db.collection("Customers").doc("2");

batch.set(customer3, { age: 3, name: "RM"});
batch.set(customer4, { age: 4, name: "Jin"});
batch.set(customer5, { age: 5, name: "Suga"});
batch.set(customer6, { age: 6, name: "J-Hope"});
batch.set(customer7, { age: 7, name: "Jimin"});
batch.set(customer8, { age: 7, name: "V"});
batch.set(customer9, { age: 7, name: "Jungkook"});

//run the batch
batch.commit().then(res => {
  console.log("BATCH RUN SUCCESFULLY")
})

delete document

db.collection("Users").doc("Calala").delete().then(res => {
  console.log("document deleted succedfully")
})

// get a document
db.collection("Users").doc("Account").get().then(doc => {
  console.log(doc.data());
})

// get all collection
db.collection("Users").get().then(snapshot => {
  snapshot.forEach(element => {
    console.log(element.data());
  });
})

// listen for real time changes
db.collection("Users").doc("Account").onSnapshot(docSnapshot => {
  console.log(docSnapshot.data());
})

// sorting and paginating collection
// create 100 records
for (let index = 0; index < Array.length; index++) {
  const element = array[index];
};

for (let index = 0; index < 100; index++) {
  db.collection("Customers").doc(index.toString()).set({
    id: index,
    name: faker.name.firstName() + " " + faker.name.lastName()
  })
};

// read the first 10 elements sorted by name
db.collection("Customers").orderBy("name")
  .startAt(0).limit(10).get().then(snapshot => {
    snapshot.forEach(element => {
      console.log(element.data().name)
    });
  })

// read the second 10 elements sorted by name
db.collection("Customers").orderBy("name")
  .startAt(10).limit(10).get().then(snapshot => {
    snapshot.forEach(element => {
      console.log(element.data().name)
    });
  })

//read the rest
db.collection("Users").orderBy("name").get().then(snapshot => {
    snapshot.forEach(element => {
      console.log(element.data().name)
    });
  })

router.get('/data', (req, res)=>{
    db.settings({
        timestampsInSnapshots: true
    })
    var allData = []
    db.collection('karyawan')
    .orderBy('waktu', 'desc').get()
    .then(snapshot => {
        snapshot.forEach((hasil)=>{
            allData.push(hasil.data())
        })
        console.log(allData)
        res.send(allData)
    }).catch((error)=>{
        console.log(error)
    })
})

router.post('/data', (req, res)=>{
    db.settings({
        timestampsInSnapshots: true
    })
    db.collection('karyawan').add({
        nama: req.body.nama,
        usia: req.body.usia,
        kota: req.body.kota,
        waktu: new Date()
    })
    res.send({
        nama: req.body.nama,
        usia: req.body.usia,
        kota: req.body.kota,
        waktu: new Date()
    })
})


