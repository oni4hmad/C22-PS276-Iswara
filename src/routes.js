const { handler } = require("@hapi/hapi/lib/cors");
const { addStoryHandler, getAllStoryHandler, getStoryByIdHandler, editStoryByIdHandler, deleteStoryByIdHandler, addAccountHandler, loginHandler } = require("./handler");

const routes = [
    {
        method: 'POST',
        path: '/register',
        handler: addAccountHandler,

    },
    {
        method: 'POST',
        path: '/login',
        handler: loginHandler,

    },
    {
        method: 'POST',
        path: '/confession',
        handler: addStoryHandler,
    },
    {
        method: 'GET',
        path: '/confession',
        handler: getAllStoryHandler,
    },
    {
        method: 'GET',
        path: '/confession/{id}',
        handler: getStoryByIdHandler,
    },
    {
        method: 'PUT',
        path: '/confession/{id}',
        handler: editStoryByIdHandler,
    },
    {
        method: 'DELETE',
        path: '/confession/{id}',
        handler: deleteStoryByIdHandler,
    },

];

module.exports = routes;