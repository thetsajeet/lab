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
