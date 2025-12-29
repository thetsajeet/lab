- Very few users interact directly with the DB using queries. Mostly users of database interact through application programs.
-  2 types of application programs:
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