# Transport Layer

- Transport layer is used to transport the messages from one system to another using the underlying infrastructure of the network, other layers
- 2 protocols - TCP and UDP
- Network: logical communication between hosts, Transport: Logical communication between processes
- messages are segments (tcp - segment, udp - datagram)
- extending host-host communication to process-process communication is called transport-layer multiplexing and demultiplexing
- UDP provides:
  - provides multiplexing and demultiplexing
  - provides integrity checking
- TCP provides:
  - what UDP provides +
  - congestion control
  - reliable data transfer
  - and more...

## Multiplexing and Demultiplexing

- Multiplexing: converting appication layer messages to transport layer segment by adding segment headers
- Demultiplexing: converting transport layer messages to application layer messages by removing segment headers

```bash
# source port <sp> destination port
# headers
# data
```

- port numbers are 16 bit numbers: 0 to 65535 (2^16 - 1)
- 0 - 1023 (2^10 - 1): well known port numbers (for http, ftp) don't use for app dev
- UDP sockets are identified by 2 pair tuple (destination ip, port)
- TCP sockets are identified by 4 pair tuple (source ip, port, destination ip, port)
- a server spawns a new processs for each connection. Today they also spawn a new thread within the same process for each new connection

## UDP

- Multiplexing/ Demultiplexing + Error checks
- UDP is preferred if:
  - finer application control
  - no connection establishment
  - no connection state
  - small packet header

```bash
# source port destination port
# length checksum
# data
```

- udp header has four fields of 2 bytes each.
- source and destination port
- length of segment (headers + data)
- checksum for data integrity
- application data
- UDP checksum for error detection:
  - bits within udp segment can be altered because of noise in links / when stored in router
  - 1s compliment of sum of all 16 bit words in the segment and overflow is wrapped
  - Eg: 3 words -> 0110, 0101, 0100 -> sum -> 1111 -> 1s compliment -> 0000
  - all 4 are sent to receiver and added => sum + compliment = 1111
  - if sum of all the bits aren't 1 then error occured. else fine

## Reliable Data Transfer

### Stop and wait protocol

- reliable data transfer protocols v1 -> consider underlying channel is reliable. no extra operations
- v2 -> channel with bit errors -> Automatic Repeat reQuest protocols used in reliable data transfer protocols for retransmission.
- 3 steps: Error detection, Receiver Feedback (ACK / NAK) and Retransmission by sender
- also called stop-and-wait (sends packets, waits to hear if ack/nack)
- one issue is what if ack/ nak packets are corrupted.
- to address this, have sender send packet + 1 bit sequence number -> recevier send ack/nak w the sequence number -> if the sender got corrupted ack/nak packet -> it sends packet with same sequence number -> and process goes on until it knows if it's ack/nak
- v3 -> can lose packets sent
- assume sender's responsibilty to handle packet losses (one approach)
- either data packet is lost / ack is lost. either case wait for enough time and retransmit it w sequence number
- but there could be premature timeouts
- sender creates a packet, starts timer, sends, waits until receives something, once received stops timer else retransmits and stop and start timer again.
- Reliable data transfer => checksum, sequence numbers, timers, ack

### Pipelining protocol

- stop and wait could be very slow for each packet.
- sends multiple packets without waiting for acknowledgements: pipelining
- consequences:
  - range of sequence numbers go up
  - buffer more than one packet in sender and receiver
  - error recovery will change -> go-back-n and selective repeat

#### Go Back N

- Sender sends multiple packets without waiting for acknowledgement but is constrained to have no more than N unack packets
- 4 intervals of data => 0 to base - 1 (ack), base to nextseqnum - 1 (sent wait for ack), nextseqnum to base + N - 1 (ready to send), base + N, inf (not yet ready to send)
- sliding window technique
- GBN responds to:
  - invocation from above -> window full / empty inform above
  - receipt of an ack -> cumululative ack (upto n seq numbers are ack)
  - timeout event -> timeout restarted for all unack packets (resent in order)
- GBN receiver accepts in-order packets and sends ACK, else sends ACK for most recent packet received
- send pkt 0, 1, 2, 3 and wait for ack. get's ack 0, 1 but not 2. so 2, 3 is resent.

#### Selective Repeat

- Sender sends packets like before, but also marks those that received ack
- receiver gets in order. if a packet is lost, higher packets are bufferred and ack is sent for them
- when sender timesout that packet not sent, it only sends those without ack and retransmits it.
- when receiver receives it, it sends all the packets from the current packet and buffered to the upper layer
- this way both receiver and sender must maintain buffer and which received ack respectively
- same as gbn there is a window size (sliding window technique) + selective repeat packets sent

### Important mechanisms in Reliable Data Transfer

- checksum - detect bit errors
- timer - timeout / retransmit a packet because packet is lost / ack is lost / premature timeout
- seq number - sequential numbering of packets to identify duplicates, ordering, detect lost packets
- ack - tell if packet is received correctly
- nak - packet has not been received correctly
- window, pipelining - packets w certain sequence number at one time.

## TCP

- 3 way handshake
- both ends maintain a buffer

```bash
source port destination port
seq number
ack number
header length + unused + urg + ack + psh + rst + syn + fin + receive window
internet checksum + urgent data pointer
options
data
```

- ports for multiplexing and demultiplexing
- 32bit seq & ack for sender and receiver side reliable data transfer
- 16 bit receive window for flow control
- 4 bit header length -> length of header
- options field -> negotiate the MSS (length of segment)
- flag => ack (ack), rst, syn, fin (connection setup and teardown), psh (send to upper layer immediately), urg (urgent) location of last byte of urgent data is pointed by 16 bit urgent data pointer sequence numbers are numbered from 0 -> 1000 -> 2000 when sent. no numbered by the segment but rather by bytes transmitted. if MSS = 1000
- ack number -> seq number of next byte the host expects from receiver. eg: if 500 bytes are received, wait for 501 it sends that to the ack number in the next packet it sends
- cumululative acknowledgements
- if gets out of order packets -> discard or buffer it
- Telnet works on top of TCP (but preferred is SSH because telnet is not encrypted)
- client and server communicates back and forth by client sending, server echoing back the same with ack
- RTT => segment sent and ack received
- prefered a single timer for all tcp segments
- timeouts: 3 major events -> data from above, event timeout (restart), ack from receiver
- tcp is a hybrid of gbn and sr -> selective acknowledgement
- tcp provides flow control using receive window flag that tells how much space is available in the buffer maintained by receiver.
- since both ends maintain buffer, we don't want to overflow the buffer.
- subtle difference with congestion control

### How connection is established

- first client sends segment with only the syn flag set to 1 => syn segment with a random initial client sequence number
- once the server receives and server responds with syn set to 1, ack number client seq + 1, server's seq number. before sending server allocates variables, buffers to this connection. called synack segment
- client receives, sends the ack back to receiver with client data. syn is set to 0. client also allocates buffers, variables for this connection.
- hence 3 way handshake
- to close client sends close connections via fin bit set to 1. waits for ack (fin_wait_1 state). once ack is received waits for fin from server (fin_wait_2) sends ack to server once received. both connection is closed after some timeout.
- Client: CLOSED -> SYN_SENT -> ESTABLISHED -> FIN_WAIT_1 -> FIN_WAIT_2 -> CLOSED
- Server: CLOSED -> LISTEN -> SYN_RCVD -> ESTABLISHED -> CLOSE_WAIT -> LAST_ACK -> CLOSED
- if the 3rd hanshake isn't sent from the client (ack) then the connection is open and resources are allocated and with a large number of such half-open connections, other clients will not be able to create connections. (Denial of Service Attacks - SYN flood attack)
- so server waits for an ack for sometime, if it doesn't receive it closes the connection, also uses syn cookies.
- when tcp segment is sent to a wrong port -> it gets a packet with RST flag set 1 for that segment
- if udp segment is sent to a wrong port, host gets a ICMP datagram
- nmap is used to check for applications running on ports:
  - if source receives a tcp synack from target host, then application is running
  - if source receives a rst segment, then application is not running but port is also not blocked by firewall
  - if source receives nothing, port is blocked by a firewall
- tcp also might use fast retransmit -> when 3 duplicate ACKs are received for the same segment, it immediately recognises a packet loss and resends that particular segment without waiting for timer to timeout for that segment

### Priciples of Congestion Control

- congestion basically leads to queuing delays, packet losses so retransmissions, etc
- 2 ways: e2e provided by transport layers and network assisted congestion control
- TCP layers uses e2e as IP layer doesn't provide any feedback
- 3 parts:
  - how tcp limits rate -> by having another variable congestion window to throttle the rate at which it sends
  - how congestion is detected -> when loss of tcp segment / 3 duplicate acks from receiver
  - which algorithm to use to send segments -> tcp congestion control algorithm
- tcp congestion control algorithm:
  - slow start: starts by sending w a mss of 1, doubles until there are no losses, timeouts, 3 duplicate acks
  - congestion avoidance: once it has a threshold set for congestion (or found), instead of increasing x 2 -> increases 1 MSS
  - fast recovery: once packets are lost, tries to identify which is lost, once finds out goes back to congestion state
- tcp is fair (divides bandwidth equally), udp isn't
