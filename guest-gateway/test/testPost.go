package main

import (
	"fmt"
	"log"
	"net/http"
	"strings"
)

func main() {
	resp, err := testPost()
	if err != nil {
		log.Fatal(err)
	}
	defer resp.Body.Close()
	fmt.Println(resp)
}

func testPost() (*http.Response, error) {
	resp, err := http.Post("http://localhost:8080/get-user-permission?userId=40&airportCode=LJG", "application/json", strings.NewReader("{userId: 40, client_id: \"LJG\", urls:[\"guest-service\", \"abc\"]}"))
	return resp, err
}

func testClient() *http.Response {
	client := &http.Client{}
	req, err := http.NewRequest("POST", "http://localhost:8080/update-role-permission", strings.NewReader("urls=http://www.baidu.com"))
	req.Header.Set("Content-Type", "application/json")
	resp, _ := client.Do(req)
	if err != nil {
		log.Fatal(err)
	}
	defer resp.Body.Close()
	fmt.Println(resp)
	return resp
}
