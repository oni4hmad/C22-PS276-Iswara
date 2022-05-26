const { nanoid } = require("nanoid");
const story = require("./story");
const user = require("./user");

const addAccountHandler = (request, h) => {
    const { name, email, password, noHp} = request.payload;

    const id = nanoid(8);

    const newUser = {
        id, name, email, password, noHp,
    };

    user.push(newUser);

    const isSuccess = user.filter((user) => user.id === id).length > 0;

    if (isSuccess) {
        const response = h.response({
            status: 'success',
            message: 'Akun berhasil ditambahkan',
            data: {
                userId: id,
            },
        });
        response.code(201);
        return response;
    }
};

const loginHandler = (request, h) => {
//     const { email, password } = request.payload;

//     const { id } = request.params;

//     const isSuccess = user.filter((user) => user.id === id).length > 0;

//     if (isSuccess) {
//         const response = h.response({
//             status: 'success',
//             message: 'Akun berhasil ditambahkan',
//             data: {
//                 userId: id,
//             },
//         });
//         response.code(201);
//         return response;
//     }
};

const addStoryHandler = (request, h) => {
    const { body } = request.payload;

    const id = nanoid(16);
    const createdAt = new Date().toISOString();

    const newStory = {
        body, id, createdAt,
    };

    story.push(newStory);

    const isSuccess = story.filter((story) => story.id === id).length > 0;

    if (isSuccess) {
        const response = h.response({
            status: 'success',
            message: 'Cerita berhasil ditambahkan',
            data: {
                storyId: id,
            },
        });
        response.code(201);
        return response;
    }

    const response = h.response({
            status: 'fail',
            message: 'Cerita gagal ditambahkan',
        });
        response.code(500);
        return response;
};

const getAllStoryHandler = () => ({
    status: 'success',
    data: {
        story,
        
    },
});

const getStoryByIdHandler = (request, h) => {
    const { id } = request.params;

    const story = story.filter((story) => story.id === id)[0];

    if (story !== undefined) {
        return {
            status: 'success',
            data: {
                story,
            },
        };
    }

    const response = h.response({
        status: 'fail',
        message: 'Cerita tidak ditemukan',
    });
    response.code(404);
    return response;
};

const editStoryByIdHandler = (request, h) => {
    const { id } = request.params;

    const { body } = request.payload;
    const updateAt = new Date().toISOString();

    const index = story.findIndex((story) => story.id === id);

    if (index !== -1) {
        story[index] = {
            ...story[index],
            body,
            updateAt,
        };

        const response = h.response({
            status: 'success',
            message: 'Cerita berhasil diperbarui',
        });
        response.code(200);
        return response;
    }

    const response = h.response({
        status: 'fail',
        message: 'Gagal memperbarui cerita, Id tidak ditemukan',
    });
    response.code(404);
    return response;
};

const deleteStoryByIdHandler = (request, h) => {
    const { id } = request.params;

    const index = story.findIndex((story) => story.id === id);

    if (index !== -1) {
        story.splice(index, 1);
        const response = h.response({
            status: 'success',
            message: 'Cerita berhasil dihapus',
        });
        response.code(200);
        return response;
    }

    const response = h.response({
        status: 'fail',
        message: 'Cerita gagal dihapus. Id tidak ditemukan',
    });
    response.code(404);
    return response;
};

module.exports = { addStoryHandler, getAllStoryHandler, getStoryByIdHandler, editStoryByIdHandler, deleteStoryByIdHandler, addAccountHandler, loginHandler };