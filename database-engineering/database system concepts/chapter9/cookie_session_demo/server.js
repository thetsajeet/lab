const express = require("express");
const session = require("express-session");
const cookieParser = require("cookie-parser");
const path = require("path");

const app = express();
const PORT = 3000;

// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(
  session({
    secret: "secret-key-demo", // In production, use a secure random string
    resave: false,
    saveUninitialized: false, // Only save session when data is modified
    cookie: {
      maxAge: 60000, // 1 minute session for demo purposes
      secure: false, // Set to true if using HTTPS
    },
  })
);

// Serve static files from public directory
app.use(express.static(path.join(__dirname, "public")));

// Endpoints

// 1. /unprotected -> unprotected as json response
// goal: when user hits the unprotected request, the server must respond with the response whether or not session exists.
app.get("/unprotected", (req, res) => {
  const sessionExists = req.session && req.session.user;
  res.json({
    message: "This is an unprotected endpoint.",
    sessionExists: !!sessionExists,
    user: sessionExists ? req.session.user : null,
  });
});

// 2. /login -> to login user (fixed credentials -> admin and pass)
// for login, if session doesn't exist only then proceed, else say session already exists.
app.post("/login", (req, res) => {
  if (req.session && req.session.user) {
    return res
      .status(400)
      .json({ message: "Session already exists. You are already logged in." });
  }

  const { username, password } = req.body;

  if (username === "admin" && password === "pass") {
    req.session.user = username;
    return res.json({ message: "Login successful.", user: username });
  } else {
    return res.status(401).json({ message: "Invalid credentials." });
  }
});

// 3. /logout -> to logout user
// for logout, only if valid session exists, proceed else send an error json response and display in UI.
app.post("/logout", (req, res) => {
  if (!req.session || !req.session.user) {
    res.clearCookie("connect.sid");
    return res
      .status(400)
      .json({ message: "No active session to logout from." });
  }

  req.session.destroy((err) => {
    if (err) {
      return res.status(500).json({ message: "Could not log out." });
    }
    res.clearCookie("connect.sid"); // Clear the session cookie
    res.json({ message: "Logout successful." });
  });
});

// 4. /protected -> protected pong as json response
// for protected, only if valid session exists, proceed else send an error json response and display in UI.
app.get("/protected", (req, res) => {
  if (!req.session || !req.session.user) {
    res.clearCookie("connect.sid");
    return res
      .status(401)
      .json({ message: "Unauthorized. Please login first." });
  }

  res.json({
    message: "Pong! You are accessing a protected endpoint.",
    user: req.session.user,
  });
});

app.listen(PORT, () => {
  console.log(`Server running on http://localhost:${PORT}`);
});
