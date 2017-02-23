package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
	"strings"
)

type handle struct {
	host string
	port string
}

func (h *handle) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	// 获取user_id
	query := r.URL.Query()
	if len(query["access_token"]) > 0 {
		resp, err := http.Get("http://oauth-center/user/getUser?access_token=" + query["access_token"][0])
		if err != nil {
			log.Fatal(err)
		}
		defer resp.Body.Close()
		var dat map[string]string
		body, err := ioutil.ReadAll(resp.Body)
		json.Unmarshal(body, &dat)
		fmt.Println(dat)

		// 将user_id添加到header中
		r.Header.Add("User-Id", dat["user_id"])
		r.Header.Add("Client-Id", dat["client_id"])
	}

	uri := r.RequestURI
	fmt.Println(uri)
	if strings.Index(uri[1:len(uri)], "/") <= 1 {
		return
	}

	serviceName := r.RequestURI[1 : strings.Index(uri[1:len(uri)], "/")+1]

	fmt.Println(serviceName)
	remote, err := url.Parse("http://" + serviceName)
	if err != nil {
		// panic(err)
	}
	proxy := httputil.NewSingleHostReverseProxy(remote)
	proxy.ServeHTTP(w, r)
}

func startServer() {
	//被代理的服务器host和port
	h := &handle{host: "airport.zhiweicloud.com"}
	err := http.ListenAndServe(":8888", h)
	if err != nil {
		log.Fatalln("ListenAndServe: ", err)
	}
}

func main() {
	startServer()
}
