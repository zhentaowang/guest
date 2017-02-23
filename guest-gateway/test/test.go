package main

import (
	"fmt"
	"net/http"
)

func main() {
	http.Handle("/css/", http.FileServer(http.Dir("template")))
	http.Handle("/js/", http.FileServer(http.Dir("template")))

	http.HandleFunc("/institution-client/list", adminHandler)
	http.ListenAndServe(":8080", nil)
}

func adminHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Println("hello")
	fmt.Println(r)
}
