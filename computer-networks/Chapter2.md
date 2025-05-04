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

- http and smtp both use TCP, persistent connections
- http is a pull protocol, smtp is push
- smtp has 7 bit ascii limit
- http uses references of objects in document, while smtp has all objects in the message
- smtp header lines include

```bash
# From:
# To:
# Subject:
```

- access agents can run locally, while fetching from mailbox running on the cloud mail server
- a sends mail to b via smtp. a sends to his mail server -> that is sent to b's mail server. -> since smtp is push protocol, b's user agent has to adopt pull protocol to get messages from b's mail server to his local access agent
- pull protocols used could be POP3, IMAP, HTTP

### POP3

- Post Office Protocol - Version 3
- simple mail access protocol
- user agent opens a tcp connection w mail server on port 110
- pop3 progresses through 3 stages: authorization, transaction, update
- authorization is done by passing username and password
- transaction phase user retrieves messages, marks the messages to delete, get mail stats, etc. (not yet deleted)
- after quit command, session is closed, mail server deletes the messages marked for deletion
- for user agent commands, pop3 responds with +OK or -ERR to indicate success or failure

```bash
# telnet mailserver 110
# +OK POP3 server ready
# user bob
# +OK
# pass pass
# +OK user logged in
```

- pop3 session maintains state, but not after session is closed.

### IMAP

- Internet Mail Access Protocol
- Allows users to create folders, move mails, delete, etc on remote server via IMAP
- All messages fall in the INBOX by default, and user can perform commands
- IMAP maintains state information across sessions
- allows to obtain parts of a message - header

### Web-based Email

- uses HTTP as well
- to receive mails from mail server to mailbox in browser
- to send mails from browser to his mail server
- SMTP is used to send mails from mail server to mail server

## DNS

- hostname and IP addresses to identify servers
- IP address -> 4 bytes -> 32 bits -> 0 to 255 -> left to right hierarchical
- Domain Name System is a directory service that translate domain names to IP addresses
- distributed machine implemented in a hierarchy of dns servers.
- dns protocol allows hosts to query distributed database.
- dns server are UNIX machines running the Berkley Internet Name Domain (BIND) software
- uses UDP on port 53
- employed by other protocols
- when browser searches a domain, it checks w the DNS server the ip address of the domain and routes the request
- DNS adds a delay in request-response
- desired IP addresses are cached in nearby DNS server
- services:
  - host aliasing: have a canonical (actual) and multiple alias names
  - mail server aliasing: mail server can also be complex, so have alias. MX records permit it to have same alias for both mail server, and hosts
  - load distribution: if servers are replicated, DNS has the list of all IP addresses mapped to the name. when sending it to client with rotating one of the addresses ordering -> so all load can be equally distributed when client makes a request (as they check the first IP address)

### DNS working

- application invokes DNS to query the IP address based on hostname
- responds with IP address
- if a single centralized DNS is used:
  - single point of failure
  - huge traffic
  - distant centralized database
  - maintenance difficulty
- 3 classes of DNS servers:
  - root dns servers: multiple root dns servers each replicated => n \* m root dns servers globally
  - TLD: top level domain servers consists of .edu, .fr, etc
  - authoritative dns servers: org's publicaly accessible hosts must provide publicaly accessible dns records
- there's a local dns server provided by ISP which the client accesses's first.
- host reaches to local dns with the query
- local dns checks with root level -> checks with tld -> checks with authoritative -> returns the IP addresses back to host
- iterative query when manually checks are made (host -> dns iterative). recursive when on it's own it decides where to fetch (local dns recursive)
- dns caches in the servers to avoid redundant calls
- with dns cache, can skip multiple root / tld level calls

### DNS Format

- Resource Records (RR) are stored in DNS database
- format: (Name, Value, Type, TTL)
- Name, value depends on the type
- Type = A => Name = hostname, Value = ip addr
- Type = NS => Name = domain name, value = hostname of authoritative dns server
- Type = CNAME => Name = alias, value = canonical host name
- Type = MX => Name = alisa, value = canonical host of mail server
- If DNS is authoritative for hostname it contains type A, else Type NS and Type A for the value of NS
- both query and reply have same format

```bash
# first 12 bytes -> header section
# first field (16 bits) -> query identifier (copied to reply)
# flags -> <query/reply> (0/1) <authoritative flag> <recursion desired flag> <does dns support recursrion>
# 4 number-of fields => questions, answers, authority, additional section
```

- To insert records in DNS is done by registrars with domain name and IP address
- provide name and address of authoritative dns servers to registrar, he will add them in the TLD (eg .com)
- type ns, a are added. when tld is queried for the domain
- alice requests for tsajeet.vercel.app in browser
- browser contacts local / regional dns
- local contacts root level (if not cached) which sends records of the .app TLD
- local contacts TLD for tsajeet.vercel.app and gets type ns and a records
- uses value of ns, to check value of a and contacts the authoritative dns server for ip address (type a record)
- receives type a from authoritative dns
- local dns sends this ip address back to browser
- browser makes a http request to ip address

## Peer to peer applications

- peers don't rely on infrastructure servers. they directly communicate with peers
- distribution time is the time it takes for each peer to get a copy.
- client-server => Distribution Time = max(N\*F/Us, F/dmin)
- p2p => DT = max(F/Us, F/dmin, NF/(Us + U1 + U2 ... UN))
- N => number of peers, F => file bits, Us => upload speed of server, dmin => lowest peer download rate, U1 => upload speed of peer 1
- client-server grows linearly with N. but p2p will bend as number of peers grow (3rd eq)
- p2p is self scalable

### Bit Torrent

- BitTorrent is a p2p protocol
- collection of all peers participating -> torrent
- when a peer first joins, it has no chunks.
- once it accumulates chunks, it can upload and download to peers
- peer may leave and enter at any point of time
- each torrent has an infrastructure node called the tracker which tracks the number of peers participating
- each torrent informs its tracker that it is still participating
- when new peer joins the torrent, tracker randomly selects a subset of peers and sends them the IP to new user
- new user attempts to establish tcp connection with all peers in the list -> neighboring peers
- each peer will have different set of chunks
- it will try to find the rarest first and store it in it
- 2 important decisions:
- what chunks to request -> rarest first algorithm
- what neighbors to respond to -> top 4 highest bandwidth (unchoked) + randomly next highest bandwidth (optimistically unchoked)
- many alogrithms are also used.

### DHT

- distributed hash table in p2p allows any peer to query, insert key-value pairs.
- every peer will hold a small subset of k-v pairs
- assign each peer an identifier in the range O to 2n-1
- each key to be stored in DHT also to be in the range 0 to 2n-1
- convert any key to hash key using hash function that outputs in the range
- to find a particular key-value pair in the system => convert the key to hash key and find the closest successor of the peer
- eg: 0, 1, 2, 4, 6, 11 and key -> 3 => closest founda at 4
- how to determine the closest key in the available set of keys? => circular DHT
- one way is to store all peers list (Mesh overlay) but O(N) to track
- another way is to store the successor and predecessor of each peer => O(N) to send messages - circular overlay
- another way is to have shortcuts in circular overlay -> O(logN)
- peer churn: peer can come and leave abruptly.
- each peer maintains two successor and periodically verifies if the successors are in the torrent. if not it updates
- to join torrent, peer will send info to the first peer and it checks the position of the peer by sending the message to successor until it finds a position

## Socket Programming
