# G-Shop eCommerce Platform

> eCommerce platform built with the MERN stack & Redux.

## Live Demo https://g-shop-jq40.onrender.com/
![Screenshot from 2023-12-19 16-11-52](https://github.com/amitpanwar789/G-Shop/assets/64302444/2827909c-97de-479a-ac05-00dfdb333d73)


![Screenshot from 2023-12-19 16-09-21](https://github.com/amitpanwar789/G-Shop/assets/64302444/3376abf1-cdba-4859-bf2e-c391fa015b85)


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
