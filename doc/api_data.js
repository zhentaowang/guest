define({ "api": [
  {
    "type": "post",
    "url": "/add",
    "title": "新增用户",
    "name": "__",
    "group": "____",
    "version": "1.0.0",
    "description": "<p>接口详细描述</p>",
    "examples": [
      {
        "title": "用户信息字段",
        "content": "{\"username\":\"admin\", \"sex\":\"男\"}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "status",
            "description": "<p>结果码</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>消息说明</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": " HTTP/1.1 200 OK\n{\n     status:0,\n     msg:'success',\n     data:{}\n }",
          "type": "json"
        }
      ]
    },
    "error": {
      "fields": {
        "Error 4xx": [
          {
            "group": "Error 4xx",
            "optional": false,
            "field": "All",
            "description": "<p>对应<code>user</code>的用户没找到</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Error-Response:",
          "content": "HTTP/1.1 200\n{\n code:-1,\n msg:'user not found',\n }",
          "type": "json"
        }
      ]
    },
    "filename": "f:/idea/guest/src/main/java/com/zhiweicloud/guest/controller/UserController.java",
    "groupTitle": "____",
    "sampleRequest": [
      {
        "url": "http://localhost:8080/roleController/add"
      }
    ]
  },
  {
    "type": "get",
    "url": "/edit",
    "title": "修改用户",
    "name": "_________",
    "group": "____",
    "description": "<p>接口详细描述</p>",
    "version": "0.0.0",
    "filename": "f:/idea/guest/src/main/java/com/zhiweicloud/guest/controller/UserController.java",
    "groupTitle": "____",
    "sampleRequest": [
      {
        "url": "http://localhost:8080/roleController/edit"
      }
    ]
  },
  {
    "type": "get",
    "url": "/detail/{id}",
    "title": "查询用户",
    "name": "__id___________",
    "group": "____",
    "description": "<p>接口详细描述</p>",
    "version": "0.0.0",
    "filename": "f:/idea/guest/src/main/java/com/zhiweicloud/guest/controller/UserController.java",
    "groupTitle": "____",
    "sampleRequest": [
      {
        "url": "http://localhost:8080/roleController/detail/{id}"
      }
    ]
  },
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "f:/idea/guest/doc/main.js",
    "group": "f__idea_guest_doc_main_js",
    "groupTitle": "f__idea_guest_doc_main_js",
    "name": ""
  }
] });
