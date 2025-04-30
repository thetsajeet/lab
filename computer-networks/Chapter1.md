# Computer Networks and the internet

## What is Internet

- Internet is a computer network that interconnects millions of computing devices throughout the world.
- The end devices are called hosts.
- End systems are connected by millions of communication links and packet switches.
- Many types of communication links exist. eg: copper wire, fiber optical, etc.
- The messages are divided into segments with some header information to be exchanged -> packets.
- Packet switch forwards packets arriving from one of its incoming comm link to one of its outgoing comm link.
- Eg: routers, link-layer switches
- Link-layer switches are used in access networks, routers in network core.
- Sequence of comm links and switches traversed by a packet is route / path.
- End systems access the internet via Internet Service Providers (ISPs)
- Lower and Higher level of ISP.
- Every internet component must communicate with each other using protocols.
- TCP/IP protocol.
- IETF creates standards for protocols conforming to what each protocol should be used for.
- These standards are called RFC -> Requests for Comments
- Internet can also be thought of as infrastructure that provides services to applications.
- End systems attached to internet provide an API that specifies how program can access the application, and how it will respond.

## Network Edge

- End systems = Host: Those that sit at the edge of the internet (eg: laptops, computers, etc.)
- Hosts can be divided into clients and servers
- Access networks: Network that physically connects an end system to the first router.
- Home Access:
  - DSL - digital subscriber line - telephone
  - Cable Internet - cable tv
  - FTTH - fibe to the home
  - Satellite
  - Dial-up access - traditional phone lines
  - Local Area Network - LANs : wired and wireless (ethernet and wifi). end systems talk to access point (wireless) and which in turns talks to wired network.
  - Wide Area Network - WANs : 3g, 4g, 5g, etc.
- Physical Media is used to transmit bits from one component to another until source reaches destination.
- Physical media can be guided or unguided media:
  - twisted pair copper wire -> telephone, residential LAN access.
  - coaxial cable -> cable television
  - fiber optics -> high speed, overseas
  - terrestrial radio channels -> signals via em spectrum.
  - satellite radio channels -> geostationary and low-earth orbiting

## Network Core

- packet switching:
  - Source breaks the messages into packets which are routed via communication links
  - If source sends at L bits/s and communication link can transmit at R bits/s then it takes L/R s to transmit all the bits via communication link
  - Store and forward transmission is adopted:
    - cause for transmission delay
    - packet switch must receive the entire bit before it can transmit.
    - a -> router -> b => L/R + L/R for the first packet. (total delay is 2L/R)
    - next bit starts at L/R (ie: after first packet has reached the router) => 3L/R second packet reaches the destination
    - and so on...
    - Total delay => N*L/R + (P - 1)*L/R for N links and P packets.
  - Queuing Delay and Packet Loss:
    - cause for queuing delay
    - each packet switch has multiple links attached to it.
    - packet switch has an output buffer (output queue) which stores packets that router is about to send into that link.
    - if link is busy, then packets are stored in queue. can lead to accumulation.
    - if buffer is full, might have to discard the packet => packet loss.
  - Forwarding tables and routing protocol:
    - every end system has an IP address.
    - router checks its forwarding table (hash table) to determine which is the next link it should send to based on packet's destination address.
    - forwarding tables are created using some routing protocols.
- circuit switching:
  - resources needed along a path (buffers, link) are reserved to provide communication between source and destination.
  - first host and desination establish a connection, send packets at a guaranteed constant rate as connection is already established (no waiting or delay)
  - connection gets 1/n of packet switching transmission speed as there are n links and each has dedicated connection reserved.
- Multiplexing in circuit networks:
  - FDM:
    - frequency division multiplexing
    - frequency of a spectrum is divided into different section / widths for different connections to use
  - TDM:
    - time division multiplexing
    - each connection is given a lot in the frame
    - every connection waits for it's turn in the order of the frame. (very small intervals, super fast)
- circuit vs packet switching:
  - packet switching is preferred because number of users > 10 that are simulataneously active are 0.1
  - packet switching allocates resources on demand, while circuit fixed resources beforehand
  - less expensive than circuit switching.

## Netowrk of Networks

- Connecting end users and content providers to ISP + connecting ISPs with one another.
- Network 1: Interconnects all access ISPs with a single global transit ISP.
- Network 2: Multiple global ISPs avaiable for access ISPs to connect to. Global ISPs themselves are interconnected.
- Network 3: Multiple access ISPs connect to regional ISPs which connect to Global ISPs (Tier-1 ISPs)
- Network 4: PoP, multi-homing, peering and IXPs along with access, regional and global ISPs
- Network 5: Content providers creating their own network and connecting to different levels of upper ISPs
- PoP: Points of Presence => a group of routers in the provider's network where customer ISPs can connect to.
- Multi-homing: Customer ISP may choose to connect to two or more provider ISPs.
- Peering: Nearby ISPs at same level can directly connect to reach without exchaning any fee (free of cost)
- IXP: Internet Exchange Points => meeting point where multiple ISPs can peer together.

## Types of delays

- Total Nodal Delay = Nodal Processing Delay + Queuing Delay + Transmission Delay + Propogation Delay
- Nodal processing delay: Examining packet's header information + sumcheck errors and deciding which link to send to. <= microseconds
- Queuing Delay: Waiting in the queue if the link is busy tranmitting other packets. >=0 to <=milliseconds
- Transmission Delay: Time taken to send the all bits in a packet to the link => L/R where is L: length of packets (number of bits), R: Tranmission Rate of switch. >=microseconds to <=milliseconds
- Propogation Delay: Time taken for all bits to reach from router a to router b => d/s => distance between two / speed of bits in the propogation medium. <=milliseconds
- Queuing delay depends on the traffic arrival. Traffic Intensity => La / R bits / s => a => average number of packets sent.
- If La / R > 1 infinite queuing delay, <= 1 queuing delay. = 0 => no delay. as the number of packets increase, queuing delay increasing drastically.
- Packet Loss: Queues have finite space. If traffic intensity approaches 1 then some packets may be dropped => lost as no space to accomodate the packets.
- If N-1 routers between source and destination => Delay end-to-end = N(Processing Delay + Transmission Delay + Propogation Delay) + K \* Queueing Delay => K is some constant multiplier to consider traffic congestion.
- Traceroute is a program that traces the route a packet takes to reach from source to destination.
- It sends special packets that contain information on header that tells the routers that it's a traceroute program. The router sends back message to the source.
- The information received contains the router address, time taken, etc.

## Throughput

- Rate at which destination receives the bits. -> (bits/s)
- Throughput depends on the transmission rate of the bottleneck link.
- If Rs is the rate between server and router, Rc is the rate between router and client => Throughput => min(Rc, Rs)
- If there are N links between server and client => Throughput => min(R1, R2, ..)
- In real world, rates of intermediate links are much higher than the rates of access networks.
- If x Routers are sending all information through a single network core of R transmission. Then throughput of core is considered as R / x which can affect the end to end throughput rate.

## Protocol Layers

- Network designers organized different protocols into different layers -> protocol stack
- Internet Protocol Stack or TCP/IP Stack:
  - Application Layer:
    - network applications and application-layer protocols reside.
    - data => message
    - Eg: HTTP, SMTP, FTP, DNS
  - Transport Layer:
    - transports application layer messages between application endpoints by breaking them into segments
    - TCP (connection-oriented) and UDP(connection-less)
    - data => segments
  - Network Layer:
    - Takes the TCP / UDP segments and breaks it down into datagrams
    - Runs the IP protocol => How the fields must be defined, how to act on these fields
    - There are many routing protocols along with IP protocol.
    - data => datagrams
  - Link Layer:
    - Takes the datagrams and transfers it to the next node as frames.
    - some protocols ensure reliable delivery from one node to another.
    - each node can employ a different protocol along the route.
    - Eg: Ethernet, WiFi
    - data => frames
  - Physical Layer:
    - Transfers eaach individual bit of a frame from one node to another.
    - Protocols depends on the type of link and the medium.
    - data => bits
- Open Systems Interconnect Stack:
  - application, presentation, session, transport, network, data link, physical
  - presentation:
    - interpret the meaning of data exchanged
    - data compression, encryption, description
  - session:
    - delimiting and synchronization of data exchange
- When data is passed from one layer to another below, it's encapsulated with additional information

## Network Security

- Malicious items - Malware - can enter and infect devices via the internet
- Compromised hosts - botnet - can be used by bad guys to control.
- Viruses - Malware that require human interaction
- Worms - Malware that don't require human interaction but can enter if the host is in vulnerable network
- DoS -> Denial of Service making service unusable for legitimate users.
- 3 types:
- vulnerability attack: crash the service
- bandwidth flooding: clog the target's access links with a lot of packets
- connection flooding: open a lot of half-open and full TCP connections, not letting other connections be established
- Bandwidth flooding can happen via DDoS attack -> Distributed Denial of Service when many compromised hosts (botnet) are controlled by the attacker to spam the service
- Packet sniffers are passive receivers that can be placed in the network, to sniff packets and decode sensitive information.
- IP spoofing is when the packet that is sent has a false IP address, masquerading as some user. => To prevent this, endpoint authentication must be established.

## History of computer networks

- telephone network in 1969, used circuit switching
- first packet switched network was ARPAnet
- first host-to-host protocol -> Network control protocol (RFC 001)
- first email by Ray Tomlinson 1972
- tim berners lee developer html, http, web server and browser
