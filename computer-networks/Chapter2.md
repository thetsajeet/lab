# Chapter 2

## Client-Server Architecture

- Server services requests from clients.
- IP addresses are known before
- Server is always running

## P2P Architecture

- No dedicated servers / reliance on servers.
- Peers can directly communicate with each other
- Self-scalable
- Challenges:
- Residential ISPs are designed for downstream and not much of upstream traffic.
- Security
- Incentives for peers to join

## Process Communication

- Process vs Program:
  - Program is the software
  - Process is the running instance of the program within an end system
  - Internally processes communicate with each other using inter-process communication
- Process that initiates a contact: client, receives: server
- Process sends and receives messages via the network through a software interface called the socket.
- Socket is the interface between application layer and transport layer

## Addressing

- To identify the receiving process:
  - address of destination
  - port of the receiving process in destination

## Transport Services

- 4 broad categories:
  - reliable data transfer vs unreliable data transfer
  - throughput: bandwidth sensitive vs elastic
  - timing
  - security
- TCP / UDP doesn't provide security -> a TCP enhanced SSL provides encryption, integrity, etc. It's used in the application layer.
- TCP: Connection-oriented and Reliable data transfer service. Also has congestion-control mechanism that throttles the sending process when network is congested.
- UDP: Connection-less and unreliable data transfer. lightweight and no additional services.
- no timing or throughput guarantees

## Application Layer Protocol

- Protocol defines how application's process running on different systems pass messages to each other.
- Application Protocol:
  - type of messages exchanged
  - syntax of message types
  - semantics of the fields
  - rules to determine how and when processes sends and responds to messages

## HTTP

- HyperText Transfer Protocol built on top of TCP
- 4 components of the web: browser, server, documents (html), http
- web page: base html + references to objects. objects are files
- http client establishes a tcp connection first (handshaking).
- once connection is established, the client sends a request to http server
- the server responds with the response to http client.
- http doesn't woory about data transfer or reliability. that's the job of underlying TCP
- http is stateless
- persistent vs non-persistent connections
- http uses three way handshake
- http client sends a tcp segment to server, server acknowledges and responds with tcp segment, client acknowledges connection established back to server, server sends a html file
- RTT: Round Trip Time -> time taken to go from client -> server -> client
- total time taken in handshake -> RTT + RTT + transmission of html file
- http uses persistent connections with pipelining by default, but can be configured to use non-persistent
- http server has a timeout interval, that closes the connection after it's inactive
- multiple web pages can be sent without waiting for replies of pending requests (pipelining)

### HTTP message format

- request

```bash
# method sp URL sp Version cr lf
# headerfieldname: sp value cr lf
# .
# .
# cr lf
# <body>
```

- Common methods: GET, POST, PUT, HEAD, DELETE
- response

```bash
# version sp statuscode sp phrase cr lf
# headerfieldname: sp value cr lf
# .
# .
# cr lf
# <body>
```

### Cookies

- cookies are used to track user activity by server.
- since http is stateless, cookies add as a session layer on top of HTTP which can help in tracking requests (login, etc)
- cookie has 4 components: in request header, in response header, backend storage, browser storage
- when server sends a cookie -> set-cookie: 1678
- browser reads it and appends to the cookie file
- when client sends requests it adds necessary cookies from the file -> Cookie: 1678
- server checks database to understand the cookie

### Caching

- Web caches or Proxy servers are intermediary components that store object references in it.
- Reduces the traffic on origin servers.
- client sends a request to cache, if cache has the object returns it, else fetches from origin server and stores it in the cache and returns the response from cache to client.
- cache helps in speed and also reduce traffic + cost on origin server
- What if the object copied locally is stale?
- Conditional GET mechanism: When cache recieves a request, it will send a If-Modified-Since header with Get request to the origin server.
- If the origin server responds with 304 => content is not updated. so keep the same
- if modified it will send the response status code (2xx) with the content. now cache will be updated

## FTP

- File Transfer Protocol
- User sitting in front of one host wants to transfer files to/from another host
- FTP runs on top of TCP
- FTP uses 2 parallel connections.
- Port 21 for control connection (auth, cd, put, get)
- Port 20 for data connectoin (file)
- FTP uses separate control connection (port 21) => out-of-band. while http, smtp => in-band (request headers)
- user starts an FTP session via FTP agent with a remote host.
- client initiates TCP control connection on port 21 => user identification and password
- when server receives command for file transfer, it creates a data connection (port 20) and sends it.
- sends only one file and closes it. to send again (open it) non persistent
- same with client sending a file
- FTP server must maintain state of user.
- restriction on number of ftp connections.
- 1-1 correspondance between user command issued and ftp reply in the control connection
- user commands are of this format: COMMANDNAME arg

```bash
# USER username
# PASS password
# RETR filename
# STOR filename
```

- command sent -> ftp server replies. (1 at a time)

## Email

- electronic mail
- 3 components: user agents, mail server, SMTP
- other components: message queue, mailbox
- sender creates a mail to recipient and sends the mail via user agent.
- user agent puts the mail in message queue of sender, sends it on it's turn.
- creates a smtp handshake
- if it fails, it retries by putting it in the queue again. if fails multiple times, it notifies the sender
- once it reaches the recipient, it is stored in the recipient's mailbox
- when recipient logs in, the mail is read using the mail agent from the mailbox
- Simple Mail Transfer Protocol, works on top of TCP
- no intermediary mail servers are present. it's a direct 1-1 communication
- port: 25
- 7 bit ASCII char limit
- sample exchange

```bash
# after smtp connection established
#s -> 220 domain
#c -> HELO host
#s -> 250 Hello host, pleased to meet you
#c -> MAIL FROM: <sender address>
#s -> 250 ... ok
#c -> RCPT TO: <receiver address>
#s -> 250 ... ok
#c -> DATA
#s -> 354 enter mail, end with '.' on a separate line
#c -> hey
#c -> i'm good
#c -> .
#s -> 250 accepted
#c (repeats mail from if more messages to the same domain) # persistent connection
#c QUIT
#s 221 closing
```
