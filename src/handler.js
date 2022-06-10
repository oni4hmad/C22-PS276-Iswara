const { nanoid } = require("nanoid");
const jwt = require("jsonwebtoken");
const report = require("./report");
const story = require("./story");
const user = require("./user");
const mysql = require("mysql");
// Initialize Firestore
const admin = require("firebase-admin");

const serviceAccount = require('./serviceAccountKey.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const db = admin.firestore();

//register
const addAccountHandler = (request, h) => {
    const { name, email, password, phoneNum } = request.payload;

    const userId = nanoid(16);

    const newUser = {
        userId, name, email, password, phoneNum,
    };

    user.push(newUser); //fs

    const isSuccess = user.filter((user) => user.userId === userId).length > 0;

    if (isSuccess) {
        const response = h.response({
            error: false,
            message: 'Akun berhasil ditambahkan',
            data: {
                userId: userId,
                nama: name,
                email: email,
            },
        });
        response.code(201);
        return response;
    }

    const response = h.response({
        error: true,
        message: 'Akun gagal ditambahkan',
    });
    response.code(500);
    return response;
};

//login
const loginHandler = (request, h) => {
    const { email, password } = request.payload;
    
    const token = jwt.sign({
        aud: 'urn:audience:test',
        iss: 'urn:issuer:test',
        sub: false,
        maxAgeSec: 14400,
        timeSkewSec: 15,
    }, 'some_shared_secret');
    const index = user.findIndex((user) => { user.name === email, user.password = password });

    if (index !== undefined) {
        if (email || user.password === password) {
        const response = h.response({
            error: false,
            message: 'Anda berhasil masuk',
            data: {
                // index
            },
            token: token
        });
        response.code(200);
        return response;
        } else {
            const response = h.response({
            error: false,
            message: 'Anda belum memiliki akun',
        });
        response.code(200);
        return response;
        }
    }
    
        const response = h.response({
            error: true,
            message: '',
        });
        response.code(500);
        return response;
};

const getAllUserHandler = () => ({
    error: false,
    data: {
        user,
    },
});

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
        // tanggapanCount, 
        // supportCount
    };

    story.push(newStory);

    const isSuccess = story.filter((story) => story.storyId === storyId).length > 0;
    const index = user.findIndex((user) => user.userId === userId);

    if (index !== undefined) {
        if (isSuccess) {
        const response = h.response({
            error: false,
            message: 'Cerita berhasil ditambahkan',
            data: {
                storyId: storyId,
            },
            userId: userId
        });
        response.code(201);
        return response;
        }

        const response = h.response({
                error: true,
                message: 'Cerita gagal ditambahkan',
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

const getAllStoryHandler = () => ({
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

const getReportByIdHandler = (request, h) => {
    const { reportId } = request.params;

    const index = report.filter((r) => r.reportId === reportId)[0];

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
        message: 'Laporan tidak ditemukan',
    });
    response.code(404);
    return response;

};

//profile
const getProfileHandler = (request, h) => {
    const { userId } = request.params;

    const index = user.filter((u) => u.userId === userId)[0];

    if (index !== undefined) {
        return {
            error: false,
            message: '',
            data: {
                index
            },
        };
    }

    const response = h.response({
        error: true,
        message: 'Akun tidak ditemukan',
    });
    response.code(404);
    return response;
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

const pool = mysql.createPool({
                    user: process.env.DB_USER,
                    password: process.env.DB_PASS,
                    database: process.env.DB_NAME,
                    socketPath: `/cloudsql/${process.env.INSTANCE_CONNECTION_NAME}`
                });

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
    getReportByIdHandler, 
    getProfileHandler,
    putProfileHandler,
};