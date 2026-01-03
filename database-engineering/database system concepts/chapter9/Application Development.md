- Very few users interact directly with the DB using queries. Mostly users of database interact through application programs.
- 2 types of application programs:
  - web browsers
  - mobile applications

## Web Fundamentals

- URL -> Uniform Resource Locator that identifies a document uniquely in the web. Can take in arguments as well
- HTML is used by browsers to render document information

## Web Sessions

- Applications typically restrict access of certain information based on the user details.
- So authentication of users are required and should be done only once per session.
- HTTP is stateless so storing session information is required at both client and server end.
- This is done in the client side with the help of cookies.
- Cookies are small piece of text that contains information sent by the server such as `theme: dark` in it.
- Websites are restricted to send cookies that are set by the servers to which request is sent. Eg: google's cookies can't be send to yahoo, but only to google.
- Servers can generate a unique identifier (session-id) and store in the application server. Send this id to be stored in the cookie of the client.
- When requests are sent, this information is also shared to the server which server reads it. If session has expired or not valid, it will indicate browser to clear cookie and authenticate again. Else server processes the request from the browser.
- For applications with high security, server may indicate to drop cookies after time-out or log out, for less secure cookies can be stored permanently.

## Java Servlets

- Java Servlets are Java programs that run on the server and handle requests from the client.
- They act as inteface between application server and application program.
- `HttpServlet` class in Java implements the servlet API.
- Each servlet request results in a new thread being created, so multiple requests can be handled in parallel.
- An request values pass through the `HttpServletRequest` class that is created for the request and all resopnse values are passed through the `HttpServletResponse` class.
- Servlet API provides methods to track a session and store information in it.
- `getSession(false)` returns the current session if it exists, else returns null.
- `getSession(true)` returns the current session if it exists, else creates a new session.
- to create a new session, server would set a cookie with session id in it, create a session object and set the id in that so it can manage in server and let browser send the session id via cookies
- `setAttribute` method is used to store information in the session.
- `getAttribute` method is used to retrieve information from the session.

## Application Server vs. Servlets

- **Application Server (Web Container)**: Provides the runtime environment for servlets. It manages the lifecycle of servlets (loading, initialization, destruction), handles network connections, maps URLs to specific servlets, and manages thread pools for concurrent requests. Examples include Apache Tomcat, Jetty, and GlassFish.
- **Servlets**: Java classes that define how to process specific requests and generate responses. They contain the business logic and rely on the application server to provide the underlying infrastructure and HTTP protocol handling.

## Server side scripting

- Server side scripting is a technology that allows to embed server side code in HTML documents.

```jsp
<html>
<head>
    <title>Hello World JSP</title>
</head>
<body>
    <%
        out.println("Hello World!");
    %>
</body>
</html>

```

- Eg: JSP, PHP, etc.

## Web application framework

- Web application framework is a set of tools and libraries that provide a structured way to build web applications.
- Unlike server-side scripting which embeds code directly within HTML, frameworks provide a structured architecture (such as MVC), pre-built libraries, and design patterns to manage complex application logic, routing, and security more efficiently.
- Eg: Django in Python, Ruby on Rails

## Client side scripting

- Client side scripting is a technology that allows to embed client side code in HTML documents.
- Useful for users interacting with HTML code such as button clicks, form validation in UI, etc.
- Eg: JavaScript

## Web services

- Backend services that run functions when invoked by client side code are called web services.
- Web services are used to exchange data between different applications through REST APIs
  using JSON data format

## When clients are offline

- Browsers allow client data to be stored locally using localStorage. Upto 5MB of data can be stored.
- IndexedDB is a database that can be used to store data locally in the browser.
- **localStorage**: Synchronous, key-value pair storage (strings only), limited to approximately 5MB.
- **IndexedDB**: Asynchronous, transactional, object-oriented database that supports larger storage capacities and complex data types.

```javascript
/**
 * Database configuration and initialization
 */
const dbName = "TodoDB";
let db;

// Open (or create) the database
const request = indexedDB.open(dbName, 1);

// Handle database schema upgrades (e.g., first time creation)
request.onupgradeneeded = (event) => {
  db = event.target.result;
  if (!db.objectStoreNames.contains("todos")) {
    // Create an object store with 'id' as the primary key
    db.createObjectStore("todos", { keyPath: "id", autoIncrement: true });
  }
};

// Handle successful database connection
request.onsuccess = (event) => {
  db = event.target.result;
};

/**
 * Adds a new todo item to the database
 * @param {string} task - The task description
 */
function addTodo(task) {
  const transaction = db.transaction(["todos"], "readwrite");
  const store = transaction.objectStore("todos");
  store.add({ task, createdAt: new Date() });
}

/**
 * Deletes a todo item by its ID
 * @param {number} id - The ID of the todo to delete
 */
function deleteTodo(id) {
  const transaction = db.transaction(["todos"], "readwrite");
  const store = transaction.objectStore("todos");
  store.delete(id);
}

/**
 * Retrieves all todo items from the database
 */
function getAllTodos() {
  const transaction = db.transaction(["todos"], "readonly");
  const store = transaction.objectStore("todos");
  const request = store.getAll();

  request.onsuccess = () => {
    console.log("All todos:", request.result);
  };
}

/**
 * Retrieves a single todo item by its ID
 * @param {number} id - The ID of the todo to retrieve
 */
function getTodo(id) {
  const transaction = db.transaction(["todos"], "readonly");
  const store = transaction.objectStore("todos");
  const request = store.get(id);

  request.onsuccess = () => {
    console.log("Todo item:", request.result);
  };
}
```

## Mobile Application Platforms

- react native / flutter to build mobile applications independent of the platform.
- Web Apps can be downloaded called PWA (Progressive Web Apps).
- Enabling feature for PWA is:
  - html5 support for local data storage
    -support for compilation of JS code.
  - PWA make use of HTML5 service workers, that can run in the background in the browser separate from a webpage. Used for sync between local and remote data, notifications, etc.
- **Progressive Web Apps (PWAs)** are web applications built with web technologies (HTML, CSS, JavaScript) that provide a user experience similar to native mobile applications.
- **Core Features**:
  - **Installable**: Users can add the app to their home screen directly from the browser without an app store.
  - **Offline Access**: Use Service Workers to cache resources, allowing the app to function without an internet connection.
  - **Push Notifications**: Can engage users with system-level notifications even when the browser is closed.
  - **Responsive**: The UI automatically adapts to various screen sizes and orientations.
- **Web App Manifest**: A JSON file (e.g., `manifest.json`) that provides metadata about the application, such as its name, icons, and theme colors, allowing the browser to treat the website as an installed application.

## Application Architecture

- 3 layers:
  - presentation / user interface layer:
    - responsible for user interaction and display of data.
    - broken into 3 sublayers called MVC architecture.
    - Model: Data and business logic.
    - View: Presentation of data.
    - Controller: Handles user input and updates model and view.
  - business logic layer:
    - responsible for processing business logic and data.
  - data-access layer:
    - responsible for accessing and managing data.

## Application Performance

- to improve application performance:
  - caching
  - parallel processing
  - tuning database
- Reduce overhead by caching:
  - connection pooling method: app code for servicing user request has to have a connection to the JDBC driver to access the database. Creating a new connection everytime can be expensive. So, connection pooling is used to reuse connections. A pool of open JDBC / ODBC connections are opened by connection pool manager. When a request comes, it is assigned a connection from the pool. When the request is completed, the connection is returned to the pool. If the pool has no unused connections, a new connection is opened. If many connections have been unused for a while, they are closed to free up resources.
  - cache database queries and the results
  - cache web pages
  - use memcached / redis to cache data for specific key. (kv store) cache invalidation should be done by the client
- Parallel processing:
  - multiple instances of application servers processing requests.
  - multiple instances of database servers processing queries.

## Application Security

### SQL injection

- SQL injection is a type of attack where an attacker injects malicious SQL code into an input field of a web application.
- The attacker can then execute arbitrary SQL code on the database.
- To prevent SQL injection, use parameterized queries.
- Use prepared statements to prevent SQL injection.
- What are prepared statements?
  - A prepared statement is a SQL statement that is precompiled and stored in the database. It is used to execute the same SQL statement multiple times with different parameters.
  - Example:
    ```java
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setString(1, username);
    ps.setString(2, password);
    ResultSet rs = ps.executeQuery();
    ```
  - How does it prevent SQL injection?
    - The SQL statement is precompiled and stored in the database. The parameters are not part of the SQL statement. So, the SQL statement is not executed until the parameters are set. If the parameters are set to malicious SQL code, it will not be executed.
  - Instead of prepared statements, a function that adds such escape characters to the SQL statement can be used to prevent SQL injection.
  - Eg:
    ```java
    String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
    ```
    - If the username is ' OR '1'='1' and the password is ' OR '1'='1', the SQL statement will be:
    ```java
    SELECT * FROM users WHERE username = ' OR '1'='1' AND password = ' OR '1'='1'
    ```
    - This will return all users in the database.
    - To prevent this, use prepared statements.

### Cross-site Scripting (XSS) & Cross-site Request Forgery (CSRF)

- XSS is a type of attack where an attacker injects malicious code written in JS to be stored in the database through UI. When another user views the page, the malicious code is executed in their browser.
- CSRF is when a script in the background is performing "valid" actions on behalf of the user without their knowledge. (transfering money is a valid action but user is not aware of it).
- To prevent against these attacks:
  - prevent website from such attacks by disallowing any HTML tags in text input fields.
  - protect website by using:
    - http protocol allows server to check referer of a page access. if referer is not from the same domain, the request is blocked.
    - use IP address along wtih cookies to identify user session.
    - never use GET to perform actions that modify data.

### Password Security

- Store encrypted passwords in the database
- DBMS can whitelist IP addresses to allow access to the database.

### Authentication

- Authentication is the process of verifying the identity of a user through credentials (username & password)
- Passwords are easily compromised by guessing / packet sniffing if passwords are not encrypted.
- 2 factor authentication is used to verify the identity of a user.
  - 1 factor (password)
  - 2 factor (password + something else) that can uniquely identify a user. Eg: biometrics, OTPs, OTPs through devices that generate a pseudo-random number and server also has to generate that number to verify the user. Clocks must be in sync close enough for this
- man-in-the-middle-attacks are those where an attacker intercepts the communication between the client and the server and steals the credentials.
- To prevent these attacks, use SSL/TLS to encrypt the communication between the client and the server.
- When users access multiple websites, they need to auth separately for each website. Often with different passwords on each site. Instead there are systems that have central auth service; the same password can be used to access multiple websites through LDAP protocol. LDAP stands for Lightweight Directory Access Protocol. It is a protocol for accessing directory services. It is used to store user information and credentials. It is used to authenticate users and authorize access to resources.
- A SSO (Single Sign On) further allows users to auth once and multiple applications can verify the user's identity through an auth service without requiring re-authentication.
- SAML (Security Assertion Markup Language) is a standard for exchanging authentication and authorization information between secure domains. When you login to udemy, if you have a university account, you can login using your university credentials. The university will verify your credentials and send a SAML assertion to udemy. Udemy will verify the assertion and allow you to login.
- OpenID protocol is an alternative to SAML. It is a protocol for exchanging authentication information between secure domains. It is used to authenticate users and authorize access to resources.
- OAuth (Open Authorization) is a protocol for exchanging authorization information between secure domains. It is used to authorize access to resources.

### Authorization

- Must be done best at application level instead of database level.
- Can be done in database level by some DBMSes but not recommended. Eg: Oracle uses VPD, postgresql, etc. Can change the meaning of the query.

### Audit Trails

- Track all changes to the data (insert, update, delete) of the application and store it separately in a database - audit trail
- useful for tracking changes to the data and for debugging and forensics.
- if malicious data was captured, or user's data got changed without user's knowledge, audit trail can be used to track the changes and restore the data to its original state.
- can be done at application level or database level.
- at application level, it is done by logging the changes to the data.
- at database level, it is done by using triggers.
- preferred at application level since it is more flexible and can be done at any level of the application.
- audit trails itself can be manipulated. so must store copies of audit trail in another machine / use blockchain techniques to store audit trails.

### Privacy of data

- Some data must be private and public to some users. Eg: health details of a user must be visible to doctor and user but not nurse. Hide some attributes such as date-of-birth and name for analytics, etc.
- Website must ask users for privacy preferences and it must adhere to the privacy preferences of users.

## Encryption

- Encryption is the process of converting data into a form that cannot be read by unauthorized users.
- Decryption is the process of converting encrypted data back into its original form.
- Encryption is done using encryption key and decryption is done using decryption key.
- Encryption key and decryption key can be the same or different.
- Good encryption technique contains:
  - relatively simple for auth users to encrypt and decrypt
  - doesn't depend on secrecy of algorithm but rather on secrecy of encryption key
  - even if data is accessible, decryption must be extremely difficult to determine
- symmetric key vs asymmetric key encryption:
  - symmetric key encryption: same key is used for encryption and decryption
  - asymmetric key encryption: different keys are used for encryption and decryption
- AES:
  - Advanced Encryption Standard
  - symmetric key encryption
  - 128 bit block size of data
  - key can be of 128, 192, or 256 bits
  - Weakness is that key must be safely transferred to the user in order to decrypt the data
- Public-key encryption:
  - Asymmetric key encryption
  - Public key is used for encryption and private key is used for decryption
  - Public key can be safely transferred to the user in order to decrypt the data
  - Weakness is that private key must be safely stored on the user's device in order to decrypt the data
- Public key encryption is secure, but computation is more expensive than symmetric key encryption.
- hybrid is used to combine both symmetric and asymmetric key encryption.
- encryption of small values such as names / date-of-birth is complicated because of dictionary attacks.
- Dictionary attacks are those where an attacker uses a dictionary of common inputs to guess the encryption key and the final value.
- To prevent dictionary attacks, use salting / initialization vectors in AES. Adding extra bits to the input data makes it more secure.
- Add extra bits before encryption and remove them after decryption: Salting / Initialization Vectors in AES
- encryption can be performed at different levels in a database system:
  - at application level
  - at database level
- alternative to encryption is to store encrypted data and decrypt before sending to users

### Encryption and Authn

- password-based authn is common, but can be packet-sniffed, eavesdropped, etc.
- challenge-response authn is more secure where db systems sends a challenge string to the user. User encrypts the challenge string with their password and sends it back to the db system. Db system decrypts the challenge string with the user's password and compares it with the challenge string it sent. If they match, the user is authenticated. This way password is only sent during registeration but never later exchanged. Downside is that passwords must be stored securely on the db.
- public-key system can be used for encryption in challenge-response authn. DB encrypts challenge string using user's public key and sends it to the user. User decrypts the challenge string using their private key and sends it back to the db system. DB checks the result, if it's good, the user is authenticated. DB has added benefit of not storing the password in the database. But risk of storing private key in user's device, can be compromised.
- smart cards are alternate solution where private key is stored on the smart card and public key is stored on the db system. User inserts the smart card into the reader and the smart card decrypts the challenge string using the private key and sends it back to the db system. DB checks the result, if it's good, the user is authenticated.
- Digital Signatures:
  - verify authenticity of data
  - sign using private key, verify using public key.
  - can only be signed if private key was available to the user.
  - norepudiation of data: can't deny having sent the data
- Digital Certificates:
  - used by web browsers to verify authenticity of website
  - also used by users to authn.
  - // TODO: Read more about the process
