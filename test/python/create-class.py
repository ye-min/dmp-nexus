#coding=utf8

import json
import requests
import hashlib
import time
import csv
from dataclasses import dataclass

def create_class1():
   global teacherId
   apiUrl = '/users/class/addClass'

   param = {
      "class_code": "C2201",
      "class_name": "22酒店1",
      "class_year": 2025,
      "teacher_id": teacherId,
      "state": 1,
   }
   showResponse(apiUrl, param)

def create_class2():
   global teacherId
   apiUrl = '/users/class/addClass'

   param = {
      "class_code": "C2202",
      "class_name": "22酒店2",
      "class_year": 2025,
      "teacher_id": teacherId,
      "state": 1,
   }
   showResponse(apiUrl, param)

def create_class3():
   global teacherId
   apiUrl = '/users/class/addClass'

   param = {
      "class_code": "C2203",
      "class_name": "22酒店3",
      "class_year": 2025,
      "teacher_id": teacherId,
      "state": 1,
   }
   showResponse(apiUrl, param)

def showResponse(apiUrl, param):
   
   response = invokeAPI(apiUrl, param)
   jsonOutput = json.dumps(response, sort_keys=True, indent=2, separators=(',', ':'), ensure_ascii=False)
   print(jsonOutput)

def getResponse(apiUrl, param):
   response = invokeAPI(apiUrl, param)
   return response

def invokeAPI(apiUrl, param,):

   userAuthority = getAuthorityData()

   apiUrl = url + apiUrl

   timestamp = int(round(time.time() * 1000))

   bodyValue = json.dumps(param, separators=(',', ':'))

   clientId = userAuthority["data"]["clientID"]
   token = userAuthority["data"]["token"]
   userId = int(userAuthority["data"]["userInfo"]["userID"])

   value = app_id + str(userId) + str(timestamp) + clientId + bodyValue + token

   sign = hashlib.sha1(value.encode('utf-8')).hexdigest()

   header = {
      'Content-Type': 'application/json;charset=utf8',
      'appID': app_id,
      'clientID': clientId,
      'userID': str(userId),
      'timestamp': str(timestamp),
      'Sign': sign
   }

   print(header)
   print(apiUrl)
   print(param)

   ret = requests.post(apiUrl, data=bodyValue, headers=header)
   if ret.status_code == 200:
      response = json.loads(ret.text)
      return response
   else:
      return ret.status_code

def getAuthorityData():
   user = { }
   with open('dmp-user.json', 'r') as userFile:
      userJson = json.load(userFile)
      user = userJson
   return user

def login():
   global url
   global app_id
   global user_name
   global password

   loginUrl = url + '/users/login'

   clientId = '000000'
   userId = '00000000'
   # userName = 'admin'
   # password = 'wisdomsky'
   token = hashlib.sha1(password.encode('utf-8')).hexdigest()
   timestamp = int(round(time.time() * 1000))

   requestBody = {"username": user_name, "type": 1, "usertype": 0}
   bodyValue = json.dumps(requestBody, separators=(',', ':'))
   body = json.loads(bodyValue)
   value = app_id + userId + str(timestamp) + clientId + bodyValue + token
   sign = hashlib.sha1(value.encode('utf-8')).hexdigest()

   print(loginUrl)
   print(token)
   print(app_id)
   print(clientId)
   print(userId)
   print(timestamp)
   print(value)
   print(sign)

   header = {
      'Content-Type': 'application/json;charset=utf8',
      'appID': app_id,
      'clientID': clientId,
      'userID': userId,
      'timestamp': str(timestamp),
      'Sign': sign
   }
   print(header)
   print(body)
   ret = requests.post(loginUrl, data=bodyValue, headers=header)
   if ret.status_code == 200:
      text = json.loads(ret.text)
      jsonOutput = json.dumps(text, sort_keys=True, indent=2, separators=(',', ':'), ensure_ascii=False)
      # print(jsonOutput)
      output = json.loads(jsonOutput)
      print(output['data']['clientID'])
      print(output['data']['token'])
      print(output['data']['userInfo']['userID'])
      with open("dmp-user.json","w") as f:
         json.dump(text, f)
         print(jsonOutput)
         print("Write user.json...")

   else:
      print(ret.status_code)

app_id = 'DMPPC'
url = 'http://47.105.110.157:9999/dmp/api/v1'
user_name = 'admin'
password = '123456'
teacherId = 2

if __name__ == '__main__':
   login()
   create_class1()
   create_class2()
   create_class3()