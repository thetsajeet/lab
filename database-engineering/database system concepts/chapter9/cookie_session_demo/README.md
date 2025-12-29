# Cookie & Session Demo

A simple Express.js application demonstrating how cookies and sessions work.

## Features

- **Unprotected Endpoint**: Accessible by anyone, checks if a session exists.
- **Login**: Creates a session (User: `admin`, Pass: `pass`).
- **Protected Endpoint**: Only accessible with a valid session.
- **Logout**: Destroys the session.

## Setup

1. Install dependencies:

   ```bash
   npm install
   ```

2. Start the server:

   ```bash
   node server.js
   ```

3. Open your browser and navigate to:
   http://localhost:3000

## Tech Stack

- Backend: Express.js, express-session, cookie-parser
- Frontend: HTML, CSS, Vanilla JavaScript
