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

## HTTP message format

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
