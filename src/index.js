const { nanoid } = require("nanoid")
// Initialize Firestore
const admin = require("firebase-admin");
const serviceAccount = require('./serviceAccountKey.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://iswara-project-default-rtdb.asia-southeast1.firebasedatabase.app"
});

const db = admin.firestore();

// // const { name, email, password, phoneNum } = request.payload;
    // const userId = nanoid(16);
//     const newUser = {
//         userId, name: "Oktaviavia", 
//         email: "Via@gmail.com", 
//         password: "Via1234", 
//         phoneNum: 081234,
//     };

//     db.collection("Users").doc(newUser.email.toString()).set(newUser);

//     const user = newUser.email;

//     db.collection("Users").doc(user).get().then(doc => {
//       if (!doc.exists) {
//       console.log('No such document!');
//     } else {
//       console.log('Document data:', doc.data());
//     }
// })

// const newUser = {
//         email: "Via@gmail.com", 
//         password: "Via1234", 
//     };

//     const usersRef = db.collection("Users");
//     const snapshot = usersRef.where("email", '==', newUser.email).get();
//     if (snapshot.empty) {
//       console.log('No matching documents.');
//     }

//     snapshot.forEach(doc => {
//       console.log(doc.id, '=>', doc.data());
//     });
    // db.collection("Users").doc("Kun@gmail.com").get().then(doc => {
    //   if (!doc.exists) {
    //   console.log('No such document!');
    // } else {
    //   console.log('Document data:', doc.data());
    // }
    // })

// const cityRef = ;
// const doc = db.collection('Users').doc('Account').get();
// if (doc.exists) {
//   console.log('Document data:', doc.data());
// } else {
//   console.log('No such document!');
// }

// try to get all document from collection Users
// const customerRef = db.collection("Users");
// customerRef.get().then((querySnapshot) => {
//   querySnapshot.forEach(document => {
//     console.log(document.data());
//   })
// })

// const newStory = {
//   Name: "Safitri",
//   Body: "blablablablabla"
// }

// const storyRef = db.collection('Users').doc("Okta@gmail.com").collection('Story').doc();
// storyRef.set(newStory);


// // write to firestore
// const data = {
//   Email: "Okta@gmail.com",
//   Body: "Cerita blablabla",
// }

// const data1 = {
//   Name: "Viaa",
//   Email: "Via@gmail.com",
//   Password: "Via123",
//   PhoneNum: 75434567
// }

// const data2 = {
//   Name: "Rahma",
//   Email: "Rahma@gmail.com",
//   Password: "Rahma123",
//   PhoneNum: 75434567
// }

// const data3 = {
//   Name: "Cala",
//   Email: "Cala@gmail.com",
//   Password: "Cala123",
//   PhoneNum: 75434567
// }

// const data4 = {
//   Name: "Safitri",
//   Email: "Safitri@gmail.com",
//   Password: "Safitri123",
//   PhoneNum: 75434567
// }

// const newStory = {
//   Name: "Safitri",
//   Body: "blablablablabla"
// }

// db.collection('Story').add;

// db.collection("Story").doc(data.Email.toString()).set(data);
// db.collection("Users").doc(data1.Email.toString()).set(data1);
// db.collection("Users").doc(data2.Email.toString()).set(data2);
// db.collection("Users").doc(data3.Email.toString()).set(data3);
// db.collection("Users").doc(data4.Email.toString()).set(data4);

// create a new write batch
// const batch = db.batch();

// const story = db.collection("Story").doc();
// const createdAt = new Date().toISOString();
// batch.set(story, {
//   Title: "My First Story",
//   Body: "wkwkwkwkwkwkwkw",
//   Date: createdAt
// });

// batch.commit().then(res => {
//   console.log("Cerita berhasil ditambahkan")
// })

// const customer3 = db.collection("Lapor").doc("3");
// const customer4 = db.collection("Lapor").doc("4");
// const customer5 = db.collection("Lapor").doc("5");
// const customer6 = db.collection("Lapor").doc("6");
// const customer7 = db.collection("Lapor").doc("7");
// const customer8 = db.collection("Lapor").doc("8");
// const customer9 = db.collection("Lapor").doc("2");
// const customer10 = db.collection("Lapor").doc(); //autogenerate

// batch.set(customer3, { age: 3, name: "RM"});
// batch.set(customer4, { age: 4, name: "Jin"});
// batch.set(customer5, { age: 5, name: "Suga"});
// batch.set(customer6, { age: 6, name: "J-Hope"});
// batch.set(customer7, { age: 7, name: "Jimin"});
// batch.set(customer8, { age: 7, name: "V"});
// batch.set(customer9, { age: 7, name: "Jungkook"});
// batch.set(customer10, { age: 7, name: "JJJJJ"});

// //run the batch
// batch.commit().then(res => {
//   console.log("BATCH RUN SUCCESFULLY")
// })

// delete document
// const email = "Cala@gmail.com"
// db.collection("Users").doc(email).delete().then(res => {
//   console.log("document deleted succedfully")
// })

// // get a document
// db.collection("Users").doc(userId).get().then(doc => {
//   if (!doc.exists) {
//   console.log('No such document!');
// } else {
//   console.log('Document data:', doc.data());
// }
// })

// // get all collection
// db.collection("Users").get().then(snapshot => {
//   snapshot.forEach(element => {
//     console.log(element.data());
//   });
// })

// // listen for real time changes
// db.collection("Users").doc("Account").onSnapshot(docSnapshot => {
//   console.log(docSnapshot.data());
// })

// // sorting and paginating collection
// // create 100 records
// for (let index = 0; index < Array.length; index++) {
//   const element = array[index];
// };

// for (let index = 0; index < 100; index++) {
//   db.collection("Customers").doc(index.toString()).set({
//     id: index,
//     name: faker.name.firstName() + " " + faker.name.lastName()
//   })
// };

// // read the first 10 elements sorted by name
// db.collection("Customers").orderBy("name")
//   .startAt(0).limit(10).get().then(snapshot => {
//     snapshot.forEach(element => {
//       console.log(element.data().name)
//     });
//   })

// // read the second 10 elements sorted by name
// db.collection("Customers").orderBy("name")
//   .startAt(10).limit(10).get().then(snapshot => {
//     snapshot.forEach(element => {
//       console.log(element.data().name)
//     });
//   })

// //read the rest
// db.collection("Users").orderBy("name").get().then(snapshot => {
//     snapshot.forEach(element => {
//       console.log(element.data().name)
//     });
//   })

// router.get('/data', (req, res)=>{
//     db.settings({
//         timestampsInSnapshots: true
//     })
//     var allData = []
//     db.collection('karyawan')
//     .orderBy('waktu', 'desc').get()
//     .then(snapshot => {
//         snapshot.forEach((hasil)=>{
//             allData.push(hasil.data())
//         })
//         console.log(allData)
//         res.send(allData)
//     }).catch((error)=>{
//         console.log(error)
//     })
// })

// router.post('/data', (req, res)=>{
//     db.settings({
//         timestampsInSnapshots: true
//     })
//     db.collection('karyawan').add({
//         nama: req.body.nama,
//         usia: req.body.usia,
//         kota: req.body.kota,
//         waktu: new Date()
//     })
//     res.send({
//         nama: req.body.nama,
//         usia: req.body.usia,
//         kota: req.body.kota,
//         waktu: new Date()
//     })
// })
// const citiesRef = db.collection('cities');

//  citiesRef.doc('SF').set({
//   name: 'San Francisco', state: 'CA', country: 'USA',
//   capital: false, population: 860000,
//   regions: ['west_coast', 'norcal']
// });
//  citiesRef.doc('LA').set({
//   name: 'Los Angeles', state: 'CA', country: 'USA',
//   capital: false, population: 3900000,
//   regions: ['west_coast', 'socal']
// });
//  citiesRef.doc('DC').set({
//   name: 'Washington, D.C.', state: null, country: 'USA',
//   capital: true, population: 680000,
//   regions: ['east_coast']
// });
//  citiesRef.doc('TOK').set({
//   name: 'Tokyo', state: null, country: 'Japan',
//   capital: true, population: 9000000,
//   regions: ['kanto', 'honshu']
// });
//  citiesRef.doc('BJ').set({
//   name: 'Beijing', state: null, country: 'China',
//   capital: true, population: 21500000,
//   regions: ['jingjinji', 'hebei']
// });

// const citiesRef = db.collection('cities');
// citiesRef.where('capital', '==', true).get().then((snapshot)=>{
//   if (snapshot.empty) {
//     console.log('No matching documents.');
//   }
//   else {
//     snapshot.forEach(doc => {
//     console.log(doc.id, '=>', doc.data());
//   })
//   }
// })
  
// // percobaan login
// const newUser = {
//         email: "Via@gmail.com", 
//         password: "Via1234", 
//     };

//     const usersRef = db.collection("Users");
//     usersRef.where("email", '==', newUser.email, '&&', "password", '==', newUser.password).get().then((snapshot)=>{
//       if (snapshot.empty) {
//       console.log('No matching documents.');
//     }

//     snapshot.forEach(doc => {
//       console.log(doc.id, '=>', doc.data());
//     });
//   });
    
// contoh menggunakan promise
// const hasil = await usersRef.where("email", '==', email, '&&', "password", '==', password).get().then((snapshot) => {
    //     return new Promise((resolve, reject) => {
    //         if (snapshot.empty) {
    //             reject("Password yang anda masukkan salah");
    //             // const response = h.response({
    //             //     error: true,
    //             //     message: 'Password yang anda masukkan salah, harap coba lagi',
    //             // }) 
    //             // response.code(500);
    //             // return response;
    //         } snapshot.forEach(doc => {
    //             resolve(doc.data());
    //             // const response = h.response({
    //             //     error: false,
    //             //     message: 'Anda berhasil masuk',
    //             //     data: doc.data(),
    //             //     token: token
    //             // })
    //             // response.code(201);
    //             // return response;
    //         })
    //     })
    //     // return snapshot.response;
    // });

    // const handleSuccess = resolvedValue => {
    // console.log(resolvedValue);
    // }
    
    // const handleFailure = rejectionReason => {
    //     console.log(rejectionReason);
    // }
    // return hasil.then(handleSuccess, handleFailure);


    const { name, email, password, phoneNum } = request.payload;
    // const userId = nanoid(16);
    const newUser = {
        name, email, password, phoneNum,
    };
    db.collection("Users").doc(newUser.email.toString()).set(newUser);
    
    const user = newUser.email;
    const hasil = db.collection("Users").doc(user).get().then(doc => {
        if (doc.exists) {
            const response = h.response({
                error: true,
                message: 'Akun berhasil ditambahkan',
                data: doc.data()
            })
            response.code(201);
            return response;
        } else {
            const response = h.response({
                error: true,
                message: 'Akun gagal ditambahkan'
            })
            response.code(500);
            return response;
        }
    })
    return hasil;



    
    // const isSuccess = story.filter((story) => story.storyId === storyId).length > 0;
    // const index = user.findIndex((user) => user.userId === userId);

    // if (index !== undefined) {
    //     if (isSuccess) {
    //     const response = h.response({
    //         error: false,
    //         message: 'Cerita berhasil ditambahkan',
    //         data: {
    //             storyId: storyId,
    //         },
    //         userId: userId
    //     });
    //     response.code(201);
    //     return response;
    //     }

    //     const response = h.response({
    //             error: true,
    //             message: 'Cerita gagal ditambahkan',
    //         });
    //         response.code(500);
    //         return response;
        
    // }

//     const getReportByIdHandler = (request, h) => {
//     const { reportId } = request.params;

//     const index = report.filter((r) => r.reportId === reportId)[0];

//     if (index !== undefined) {
//         return {
//             error: false,
//             data: {
//                 index
//             },
//         };
//     }

//     const response = h.response({
//         error: true,
//         message: 'Laporan tidak ditemukan',
//     });
//     response.code(404);
//     return response;

// };


