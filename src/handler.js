const { nanoid } = require("nanoid");
const jwt = require("jsonwebtoken");
const report = require("./report");
const story = require("./story");
// const user = require("./user");
const mysql = require("mysql");

// Initialize Firestore
const admin = require("firebase-admin");
const serviceAccount = require('./serviceAccountKey.json');
const { response } = require("@hapi/hapi/lib/validation");
const user = require("./user");
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://iswara-project-default-rtdb.asia-southeast1.firebasedatabase.app"
});
const db = admin.firestore();

//register
const addAccountHandler = (request, h) => {
    const { name, email, password, phoneNum } = request.payload;
    // const userId = nanoid(16);
    const newUser = {
        name, email, password, phoneNum,
    };

    const usersRef = db.collection("Users").doc(newUser.email.toString());
    usersRef.set(newUser);
    
    const hasil = usersRef.get().then(doc => {
        const cekhasil = () => {
            return new Promise ((resolve, reject) => {
                if (doc.exists) {
                    resolve(doc.data())
                } else {
                    reject("Akun gagal ditambahkan")
                }
            })
        }
        const handleSuccess = resolvedValue => {
            const response = h.response({
                error: true,
                message: 'Akun berhasil ditambahkan',
                data: resolvedValue
            })
            response.code(201);
            return response;
        }

        const handleFailure = rejectionReason => {
            const response = h.response({
                error: true,
                message: rejectionReason
            })
            response.code(500);
            return response;
        }
        return cekhasil().then(handleSuccess,handleFailure);
    })
    return hasil;
};

//login
const loginHandler = async (request, h) => {
    const { email, password } = request.payload;
    
    const token = jwt.sign({
        aud: 'urn:audience:test',
        iss: 'urn:issuer:test',
        sub: false,
        maxAgeSec: 14400,
        timeSkewSec: 15,
    }, 'some_shared_secret');

    const usersRef = db.collection("Users");
    const hasil = usersRef.where("email", '==', email, "password", '==', password).get().then((snapshot)=>{
        const cekhasil = () => {
            return new Promise((resolve, reject) => {
                if (snapshot.empty) {
                    reject("Password yang anda masukkan salah")
                } snapshot.forEach(doc => {
                    resolve(doc.data())
                })
            })
        }
        const handleSuccess = resolvedValue => {
        const response = h.response({
            error: false,
            message: 'Anda berhasil masuk',
            data: resolvedValue,
            token: token
        })
        response.code(500);
        return response;
        }
        
        const handleFailure = rejectionReason => {
            const response = h.response({
                    error: true,
                    message: rejectionReason,
                }) 
                response.code(500);
                return response;
        }
        return cekhasil().then(handleSuccess,handleFailure);
    })
    return hasil;
    
};

const getAllUserHandler = () => {
    const usersRef = db.collection("Users");
    const data = usersRef.get().then((querySnapshot) => {
        querySnapshot.forEach(document => {
            return document.data();
        })
    })
    return data;
};
// ({
//     error: false,
//     data: {
//         user,
//     },
// });

//confession room
const addStoryHandler = (request, h) => {
    const { userId } = request.params;
    const { body } = request.payload;
    const storyId = nanoid(16);
    const createdAt = new Date().toISOString();

    const newStory = {
        storyId, 
        userId, 
        createdAt, 
        body,
    };
    const storyRef = db.collection('Users').doc(newStory).collection('Story').doc(newStory.storyId.toISOString());
    storyRef.set(newStory);
    
    // const story = newStory.storyId;
    const hasil = storyRef.get().then(doc => {
        if (doc.exists) {
            const response = h.response({
                error: true,
                message: 'Cerita berhasil ditambahkan',
                data: doc.data()
            })
            response.code(201);
            return response;
        } else {
            const response = h.response({
                error: true,
                message: 'Cerita gagal ditambahkan'
            })
            response.code(500);
            return response;
        }
    })
    return hasil;
};

const getAllStoryHandler = () => (
    {
    error: false,
    message: '' ,
    data: {
        story,
    },
});


const getStoryByuserIdHandler = (request, h) => {
    const { userId } = request.params;

    const index = user.filter((u) => u.userId === userId)[0];

    if (index !== undefined) {
        return {
            error: false,
            data: {
                story
            },
        };
    }

    const response = h.response({
        error: true,
        message: 'user tidak ditemukan',
    });
    response.code(404);
    return response;
};

const getStoryByIdHandler = (request, h) => {
    const { storyId } = request.params;

    const index = story.filter((s) => s.storyId === storyId)[0];

    if (index !== undefined) {
        return {
            error: false,
            data: {
                index
            },
        };
    }

    const response = h.response({
        error: true,
        message: 'Cerita tidak ditemukan',
    });
    response.code(404);
    return response;
};

const editStoryByIdHandler = (request, h) => {
    const { storyId } = request.params;

    const { body } = request.payload;
    const updateAt = new Date().toISOString();
    const createdAt = updateAt;

    const index = story.findIndex((story) => story.storyId === storyId);

    if (index !== -1) {
        story[index] = {
            ...story[index],
            body,
            createdAt,
        };

        const response = h.response({
            error: false,
            message: 'Cerita berhasil diperbarui',
        });
        response.code(200);
        return response;
    }

    const response = h.response({
        error: true,
        message: 'Gagal memperbarui cerita, Id tidak ditemukan',
    });
    response.code(404);
    return response;
};

const deleteStoryByIdHandler = (request, h) => {
    const { storyId } = request.params;

    db.collection("Story").doc(storyId).delete().then(res => {
        const response = h.response({
            error: false,
            message: "Cerita berhasil dihapus"
        });
        response.code(200);
        return response;
    })

    const index = story.findIndex((story) => story.storyId === storyId);

    if (index !== -1) {
        story.splice(index, 1);
        const response = h.response({
            error: false,
            message: 'Cerita berhasil dihapus',
        });
        response.code(200);
        return response;
    }

    const response = h.response({
        error: true,
        message: 'Cerita gagal dihapus. Id tidak ditemukan',
    });
    response.code(404);
    return response;
};

//laporan
const addReportHandler = (request, h) => {
    const { userId } = request.params;
    // const {  } = request.payload;
    
    const reportId = nanoid(16);
    const date = new Date().toISOString();

    const newReport = {
        reportId,
        date, 
    };

    report.push(newReport);

    const isSuccess = report.filter((report) => report.reportId === reportId).length > 0;
    const index = user.findIndex((user) => user.userId === userId);

    if (index !== undefined) {
        if (isSuccess) {
        const response = h.response({
            error: false,
            message: 'Laporan berhasil ditambahkan',
            data: {
                reportId: reportId,
                date: date,
            },
        });
        response.code(201);
        return response;
        }

        const response = h.response({
                error: true,
                message: 'Laporan gagal ditambahkan',
            });
            response.code(500);
            return response;
        
    }

    const response = h.response({
        error: true,
        message: 'User tidak ditemukan',
    });
    response.code(500);
    return response;

};

const getAllReportHandler = () => ({
    error: false,
    data: {
        report,
    },
});

//profile
const getProfileHandler = (request, h) => {
    const { userId } = request.params;

    const user = db.collection("Users").doc(userId).get().then(
        doc => {
            if (!doc.exists) {
                return {
                    error: true,
                    message: 'Akun tidak ditemukan',
                }                
            }
            const response = h.response({
                error: false,
                data: doc.data()
            })
            response.code(201);
            return response;
        }
    )
    return user;
};

const putProfileHandler = (request, h) => {
    const { userId } = request.params;

    const { name, email, phoneNum } = request.payload;

    const index = user.findIndex((u) => u.userId === userId);

    if (index !== -1) {
        user[index] = {
            ...user[index],
            name,
            email,
            phoneNum,
        };

        const response = h.response({
            error: false,
            message: 'Data berhasil diperbarui',
        });
        response.code(200);
        return response;
    }

    const response = h.response({
        error: true,
        message: 'Gagal memperbarui data, akun tidak ditemukan',
    });
    response.code(404);
    return response;

};

module.exports = { 
    addAccountHandler, 
    loginHandler,
    getAllUserHandler, 
    addStoryHandler, 
    getAllStoryHandler,
    getStoryByuserIdHandler,
    getStoryByIdHandler, 
    editStoryByIdHandler, 
    deleteStoryByIdHandler,
    addReportHandler,
    getAllReportHandler,
    getProfileHandler,
    putProfileHandler,
};