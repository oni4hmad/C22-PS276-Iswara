// untuk membuat mysql

const mysql = require('mysql');

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'admin-iswara276',
    database: 'iswara'
});

connection.connect(function(err) {
  if (err) {
    return console.error('error: ' + err.message);
  }

  console.log('Connected to the MySQL server.');
});

const pool = mysql.createPool({
    user: process.env.DB_USER,
    passsword: process.env.DB_PASS,
    database: process.env.DB_NAME,
    socketPath: `/cloudsql/${process.env.INSTANCE_CONNECTION_NAME}`,
});

pool.getConnection(function(err, connection) {
  // execute query
  // ...
});

var mysql = require('mysql')

var con = mysql.createConnection({
  host: 'localhost',
  user: 'yourusername',
  password: 'yourpassword',
})

con.connect(function (err) {
  if (err) throw err
  console.log('Connected!')
  con.query('CREATE DATABASE mydb', function (err, result) {
    if (err) throw err
    console.log('Database created')
  })
})