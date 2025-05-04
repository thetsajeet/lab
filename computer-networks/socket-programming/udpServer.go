package socket_programming

import (
	"fmt"
	"log"
	"net"
	"strings"
)

func udpServer() {
	log.Default().Println("UDP Server running")

	serverAddr := net.UDPAddr{
		Port: 12000,
		IP: net.ParseIP("127.0.0.1"),
	}

	conn, err := net.ListenUDP("udp", &serverAddr)
	if err != nil {
		log.Fatalf("Unable to create udp server connection: %v\n", err)
	}

	defer conn.Close()

	log.Default().Printf("UDP server listening on %s\n", serverAddr.String())

	buffer := make([]byte, 1024)
	for {
		n, clientAddr, err := conn.ReadFromUDP(buffer)
		if err != nil {
			log.Fatalf("Error reading: %v\n", err)
		}

		fmt.Printf("Received from %v: %s\n", clientAddr, string(buffer[:n]))

		response := []byte(strings.ToUpper(string(buffer[:n])))
		_, err = conn.WriteToUDP(response, clientAddr)
		if err != nil {
			log.Fatalf("Error writing: %v\n", err)
		}
	}
}
