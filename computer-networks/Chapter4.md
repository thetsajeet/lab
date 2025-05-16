# Network Layer

- Network layer involves hosts directly communicating with one another, while transport was processes communicating
- 3 important functions:
  - forwarding -> transfering from one link to another link within the router
  - routing -> based on routing algorithms, which route / path should packet take to reach destination (uses routing tables)
  - connection setup -> how routers must be setup
- packet switches can be link-layer (layer2) and routers (layer3) devices based on the layer, and what value it reads from the packet

## Network Service Model

- different services that the network layer can provide.
- Many internet architectures: internet, ATM
- service models: best-effort, constant bit rate, available bit rate
- best-effort service: timing not guaranteed, packets are not guaranteed to receive in order.
- network layer also provides connection (virtual circuit) and connectionless (datagram) services at network layer.
- different from transport layer connectionless service.
- network layer can only provide one type of service: connection vs connectionless

### Virtual Circuit Networks

- Connection oriented service at network layer implemented in ATM, frame relay networks.
- consists of path, forwarding tables and VC number.
- VC setup -> Data Transfer -> VC teardown
- Routers maintain connection state information
- Data transfered from host to router, router checks vc number and determines which link to pass for the next router and so on.

### Datagram Networks

- Connection-less service used in Internet
- no state information is maintained. but forwarding information is.
- datagrams are sent to the router with destination address.
- routers use the longest matching prefix of the destination address from the forwarding table to determine which path.
- every 1-5 minutes, the forwarding tables could be updated based on routing algorithms. so packets can reach in different order and different path.

## Router components

- 4 components:
  - Input Ports
    - terminating a physical link layer at the router.
    - interoperates with the datalink layer
    - performs lookup on forwarding table and routes to the appropriate output port via switch fabric
  - Output ports:
    - store packets received from switch fabric
    - interoperates with datalink layer
  - Switch fabric:
    - connects input port to its output port
  - routing processor:
    - executes routing protocols
    - maintains forwarding table
    - maintains attached link state information
- input, output ports and switch fabric -> forwarding -> router forwarding plane
- routing processor -> managing routing protocol, tables -> router control plane

### Input Ports

- terminates a physical link layer -> performs data link layer functions -> finds out the output port from forwarding tables -> sends into the switch fabric
- copies of forwarding tables are sent from control plane to each input port to avoid bottleneck during lookups
- in some designs, packets may be stored before sending into switch fabric
- match (lookup) + action (forwarding) design abstraction

### Switching

- multiple ways:
  - switch via memory:
    - routing processor decides from forwarding tables the destination based on source, in CPU
  - switch via bus:
    - all input ports prepend a label that would match the output port and is sent to all output ports.
    - the output port that matches the label sends it forward after removing the label, rest ignores the packet.
    - all done without the intervention of a CPU
  - switch via interconnection network:
    - crossbar networks are used to forward packets across ports. 2n buses that connect n input and n output ports
    - packets can sent in parallel, but only if input and output ports are different.

### Output Processing

- Packets from switch fabric are pushed to the queue
- data link layer functions
- line termination

### Queuing

- queuing may be done in input ports, done in output ports
- Head of line blocking phenomena is when the head input port packets block the other input port packets in the same port from being routed due to some waiting.
- because of queue full, packets may be dropped

### Routing Control Plane

- network wide routing control plane decentralized with different pieces at each routers and interacting with each other by sending control messages

## Internet Protocol (IP)

- 3 major components of network layer
  - IP
  - routing protocol - path datagram follows from source to destination
  - ICMP
- datagram:

```bash
# version headerlength type of service datagram length
# 16-bit-identifier flags 13-bit-fragmentation-offset -> ip fragmentation
# ttl upper-layer-protocol headerchecksum
# 32 bit source ip address
# 32 bit destination ip address
# options
# data
```

### IP Fragmentation

- Not all link layer protocols can carry the same network layer packet sizes
- Maximum amount of data that a link layer frame can carry is called MTU Maximum Transmission Unit
- fragment the data in the IP datagram into two or more smaller fragments to send to link layer
- fragments are reassembled at the receiver network layer and sent to upper layer
- fragments are reassembled using identification flag and offset fields. identification flag to determine which datagram the fragment belongs to and offset to indicate where the fragment should be inserted to create the original datagram
- flag bit set to 1 indicates that this is not the last fragment, set to 0 indicates last fragment
- if one or more fragments are missing, the datagram is discarded.
- vulnerabilities of IPv4 fragmentation:
  - DoS attack with not sending flag bit 0 at all. so always checking for offset 0
  - overlapping IP fragments
  - complicates routers and end systems
- soln: IPv6

### IPv4 Addressing

- host and router exchange information to/from the links via an interface.
- interface is the boundary between the links and the host/ router.
- this interface is given an address -> IP address
- IPv4 is 32 bits long (4 bytes) -> 2^32 possible addresses
- network interconnecting some host interfaces and router interface form a subnet
- subnet addressing => xxx.xxx.xxx.xxx/y => /y subnet mask indicates that leftmost y bits fo the 32 bits define the subnet address
- eg: 223.1.1.0/24 => 223.1.1.xxx => part of a subnet
- to determine subnets -> detach each interface from host / router forming islands of isolated networks => subnet
- Internet's address assignment strategy -> CIDR => Classless Interdomain Routing (CIDR)
- CIDR addressing follows the a.b.c.d/x format where x of the address in the packet determines if the router should route it within the org or external based on some rules set.
- Before CIDR it was Classful addressing where 1,2,3 bytes of subnets were formed (class a, b, c) -> over/under utilization of addresses
