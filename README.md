# G-Shop eCommerce Platform

> eCommerce platform built with the MERN stack & Redux.

![Screenshot from 2023-01-11 11-52-41](https://user-images.githubusercontent.com/64302444/211733284-415c67b2-78aa-42b1-83c0-5f4f6895e045.png)
![Screenshot from 2023-01-11 11-52-52](https://user-images.githubusercontent.com/64302444/211733280-16958031-8d07-4312-acb3-440463d37d2d.png)

![Screenshot from 2023-01-11 11-53-00](https://user-images.githubusercontent.com/64302444/211733270-f7e1f23d-b0df-46ef-ad37-e7293dabadbd.png)


## Features

- Full featured shopping cart
- User profile with orders
- Admin product management
- Admin user management
- Admin Order details page
- Database seeder (products & users)

## Usage

### ES Modules in Node

We use ECMAScript Modules in the backend in this project. Be sure to have at least Node v14.6+ or you will need to add the "--experimental-modules" flag.

Also, when importing a file (not a package), be sure to add .js at the end or you will get a "module not found" error

You can also install and setup Babel if you would like

### Env Variables

Create a .env file in then root and add the following

```
NODE_ENV = development
PORT = 5000
MONGO_URI = your mongodb uri
JWT_SECRET = 'abc123'
```

### Install Dependencies (frontend & backend)

```
npm install
cd frontend
npm install
```

### Run

```
# Run frontend (:3000) & backend (:5000)
npm run dev

# Run backend only
npm run server
```

## Build & Deploy

```
# Create frontend prod build
cd frontend
npm run build
```

### Seed Database

You can use the following commands to seed the database with some sample users and products as well as destroy all data

```
# Import data
npm run data:import

# Destroy data
npm run data:destroy
```

```
Sample User Logins

admin@example.com (Admin)
123456

john@example.com (Customer)
123456

jane@example.com (Customer)
123456
```
