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
- When host sends a datagram on 255.255.255.255, it broadcasts message to all hosts on the same subnet

### Obtain a block of address

- When org wants to obtain a block of IP address for its org, it reaches out to ISP
- ISP allocates a block from its address (eg: 200.23.16.0/20) to the org (eg: 200.23.16.0/23)
- ISP gets its address from ICANN - Internt Corporation for Assigned Names and Numbers
- ICANN responsible for managing DNS root servers and allocating IP addresses

### DHCP

- A host used to obtain an IP address by manual configuration
- For routers, system admins manually configure IP address via network management tool
- For hosts, addresses can be dynamically allocated using DHCP protocol
- Dynamic Host Control Protocol -> allows hosts to receive an IP address from the subnet it's connected to.
- Also called plug-and-play protocol
- As clients come and leave the internet it allocates and deallocates IP address from a pool of IP addresses available
- Each subnet will have a DHCP server (if not then use router as DHCP relay agent that knows the DHCP server for that network)
- 4 step processes for new arriving DHCP client:
  - DHCP server discovery:
    - client sends a DHCP discover message as UDP packet to port 67
    - but since address is not known, it will broadcast to 255.255.255.255 (this sends a packet to all connected hosts / routers in the subnet)
  - DHCP server offers:
    - DHCP servers recieve a discover message, and sends a DHCP offer message that is sent to all nodes in the subnet
    - Many servers can offer a client, so client can choose from among one
    - Each message will have transaction id, proposed ip address, network mask, ip address lease time
    - ip address lease time - amount of time client can use the ip address in the subnet
  - DHCP request:
    - Client chooses one offer and responds to one with a DHCP request message echoing back the parameter
  - DHCP ACK:
    - server responds with a DHCP ACK
- DHCP also provides a mechanism to renew the lease time
- For mobile hosts, each time a new DHCP address is given in new subnet => difficult to maintain a TCP connection with remote application
- soln: mobile IP

### NAT

- If more addresses are to be allocated within a subnet, or newer devices to be managed within the subnet, more address blocks are required
- Instead for address allocation - Network Address Translation (NAT) is used in home routers
- x.x.x.x/8 => one of the three portions of IP address space that is reserved for private network whose addresses have meaning only inside the network
- host <==> nat-router <==> external router
- NAT enabled router doesn't look like a router to the outside world, rather it looks like an entity requesting
- host sends packets from its port to destination via nat-enabled router
- nat-enabled router intercepts it, gives it's source IP address and new port which is available in the packet and sends to the destination
- It also adds a new row in the NAT table
- When it receives response, it checks it NAT table for the right source that requested from the destination and sends to it.
- NAT router get IP address from ISP's DHCP server and home devices get it's IP within from router run DHCP server
- IETF objects NAT router:
  - port numbers are for processes and not hosts
  - routers are supposed to be layer 3 only
  - nat violates end-to-end agreement (destination talks to nat instead of host)
  - use IPv6 instead of NAT
  - P2P applications behind NAT can't talk directly. Work around is to have a separate peer c and a peer b that is not behind nat. Establish a tcp connection to peer a via peer c and talk.
  - it's called -> connection reversal

### UPnP

- Universal plug-and-play protocol that lets hosts in private network create a mapping in NAT
- Eg: Peer behind a NAT can request the NAT to create a mapping between its private address and port to public

### ICMP

- Internet Control Message Protocol
- Used by hosts and routers to communicate network layer informationa with each other.
- ICMP -> type, code, header and first 8 bytes of IP datagram
- Traceroute is implemented using TTL ICMP type.
- Send multiple UDP segments with a random port number from source to destination by incrementing the TTL value in each packet by 1.
- After packet reaches the nth router, the TTL with n value is discarded as per IP protocol and the router sends back name of router and IP address (type 11 code 0) back to host to inform where it's discarded
- One of them will make it to the destination port but destination will throw a type 3 code 3 error because the port number is likely wrong and send a response to the host.
- This way the route taken by packets can be traced easily.

### Firewalls and IDS

- Firewalls are installed between network and internet.
- Inspect datagrams and deny suspicious segments into the network
- Can also block addresses, ports, to be entered / used.
- Can be configured to block all ICMP echo types, etc.
- IDS -> Intrusion Detection System is used to create an alert if a malicious packet enters based on the packet signature stored in db
- IPS -> Intrusion Prevention System is used to block a malicious packet from entering

### IPv6

- IPv6 was created because people suspected IPv4 will be used up completely (not yet occurred)
- IPv6 is 128 bit IP address (IPv4 was 32)
- IPv5 was supposed to be ST-2 protocol but was later dropped
- IPv6 allows anycast address -> allow a datagram to be delivered to any one of group of hosts

#### Format

- Version: 6 to indicate IPv6
- Traffic Class: similar to TOS in IPv4 (priority)
- Flow label: Label packets belonging to a flow for special handling
- Payload length: Data length without headers
- Next header: All information about header. Basically where routing header is, where protocol is, where data is in the same packet
- Hop Limit: Contents of field are decremented by 1 every time it reaches a router. when becomes 0 -> discarded
- Source and destination address: 128 bit
- data

#### Changes from IPv4

- No fragmentation / reassembly. Responsibility of source and destination => faster forwarding. If size too much not able to send to the link, router throws an ICMP error message to host
- No header checksum: Since transport layer performs header checksum, it's dropped in IPv6. => faster forwarding
- Options: No options header. But fixed 40byte Next Header (contains header + protocol + some informations)

#### Transition from IPv4

- Dual stack approach
  - New nodes must be both v6 and v4 compatible.
  - when requested v4 send v4, else v6
  - Problem, even if 2 end systems can communicate in v6, if intermediate is v4 then entire process becomes v4.
  - converting v4 to v6 by router is not possible, as some fields are different in v4 and v6 => leads to information lose
- Tunneling:
  - Wrap the v6 packets in the v4 packet's data field and pass to v4 routers
  - when received by a v6 entity, it will extract the data from the packet and use forward as v6
  - Since it creates an imaginary tunnel, wraps entire datagram inside v4 packet and sends, it's considered tunneling approach

### IPsec

- IPsec is protocol designed to be backward compatible with v4 and v6
- Implemented on source and destination
- For secure communication
- Services:
  - Cryptographic agreement (cryptographic algo + keys)
  - Encryption and Decryption
  - Data integrity -> header checks
  - Origin authentication
- used in VPNs, secure channels, etc

## Routing Algorithms
