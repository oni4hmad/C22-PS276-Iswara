const { handler } = require("@hapi/hapi/lib/cors");
const { response } = require("@hapi/hapi/lib/validation");
const Joi = require("joi");
// const mysql = require("mysql")

const { 
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
} = require("./handler");

const routes = [
    {
        method: 'GET',
        path: '/',
        config: {
            auth: false,
            handler: () => {
                return `Selamat Datang di Aplikasi Iswara`;
            },
        }
    },

    //regiser
    {
        method: 'POST',
        path: '/register',
        config: {
            auth: false,
            handler: addAccountHandler,
            validate: {
                payload: Joi.object ({
                    name: Joi.string()
                    .min(4)
                    .required(),
                    email: Joi.string()
                    .min(4)
                    .required(),
                    password: Joi.string()
                    .min(6)
                    .required(),
                    phoneNum: Joi.string()
                    .min(10)
                })
            } 
        }
    },

    //login
    {
        method: 'POST',
        path: '/login',
        config: { 
            auth: false,
            handler: loginHandler,
            validate: {
                payload: Joi.object ({
                    email: Joi.string()
                    .min(4)
                    .required(),
                    password: Joi.string()
                    .min(6)
                    .required()
                })
            } 
        },
    },
    {
        method: 'GET',
        path: '/user',
        config: {
            auth: false,
            handler: getAllUserHandler,
        }
    },

    //confession room
    {
        method: 'POST',
        path: '/story',
        config: {
            auth: false,
            handler: addStoryHandler,
        }
    },
    {
        method: 'GET',
        path: '/confession',
        config: {
            auth: false,
            handler: getAllStoryHandler,
        }
    },
    {
        method: 'GET',
        path: '/confession/{userId}',
        config: {
            auth: false,
            handler: getStoryByuserIdHandler,
        }
    },
    {
        method: 'GET',
        path: '/story/{storyId}',
        config: {
            auth: false,
            handler: getStoryByIdHandler,
        }
    },
    {
        method: 'PUT',
        path: '/story/{storyId}',
        config: {
            auth: false,
            handler: editStoryByIdHandler,
        }
    },
    {
        method: 'DELETE',
        path: '/story/{storyId}',
        config: {
            auth: false,
            handler: deleteStoryByIdHandler,
        }
    },

    //laporan
    {
        method: 'POST',
        path: '/report',
        config: {
            auth: false,
            handler: addReportHandler,
        }
    },
    {
        method: 'GET',
        path: '/report',
        config: {
            auth: false,
            handler: getAllReportHandler,
        }
    },

    //profile
    {
        method: 'GET',
        path: '/profile',
        config: {
            auth: false,
            handler: getProfileHandler,
        }
    },
    {
        method: 'PUT',
        path: '/profile/userId',
        config: {
            auth: false,
            handler: putProfileHandler,
        }
    },
];

module.exports = routes;