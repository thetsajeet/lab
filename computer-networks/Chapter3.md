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
