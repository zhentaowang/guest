package main

import (
	"encoding/json"
	"io/ioutil"
	"log"
	"net/http"
	"net/http/httputil"
	"net/url"
)

type handle struct {
	host string
	port string
}

func (h *handle) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	// 获取user_id
	query := r.URL.Query()
	resp, err := http.Get("http://airport.zhiweicloud.com/oauth/user/getUser?access_token=" + query["access_token"][0])
	if err != nil {
		log.Fatal(err)
	}
	defer resp.Body.Close()
	var dat map[string]string
	body, err := ioutil.ReadAll(resp.Body)
	json.Unmarshal(body, &dat)

	// 将user_id添加到header中
	r.Header.Add("User-Id", dat["user_id"])
	r.Header.Add("Client-Id", dat["client_id"])

	remote, err := url.Parse("http://" + h.host)
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
