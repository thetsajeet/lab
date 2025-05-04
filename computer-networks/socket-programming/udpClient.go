package socket_programming

import (
	"bufio"
	"fmt"
	"log"
	"net"
	"os"
	"strings"
)

func udpClient() {
	log.Default().Println("UDP Client running")

	serverAddr := net.UDPAddr{
		Port: 12000,
		IP: net.ParseIP("127.0.0.1"),
	}

	conn, err := net.DialUDP("udp", nil, &serverAddr)
	if err != nil {
		log.Fatalf("Unable to connect to server udp connection: %v\n", err)
	}

	defer conn.Close()

	reader := bufio.NewReader(os.Stdin)
	fmt.Println("enter a sentence in lowercase: ")
	sent, _ := reader.ReadString('\n')
	sent = strings.ToLower(strings.TrimSpace(sent))

	fmt.Printf("the sentence you entered: %s\n", sent)

	_, err = conn.Write([]byte(sent))
	if err != nil {
		log.Fatalf("Unable to write to server udp connection: %v\n", err)
	}
	
	buffer := make([]byte, 1024)
	n, _, err := conn.ReadFromUDP(buffer)
	if err != nil {
		log.Fatalf("Unable to read from server udp %v\n", err)
	}

	fmt.Printf("Received message from server: %s\n", string(buffer[:n]))
	log.Default().Println("UDP Client exitting")
}
