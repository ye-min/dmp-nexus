#coding=utf8

import json
import requests
import hashlib
import time

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
   #url = 'https://testrent.infoecho.cn:8002/dmp/api/v1/users'
   global url
   global app_id
   global user_name
   global password

   loginUrl = url + '/users/login'

   clientId = '000000'
   userId = '00000000'
   token = hashlib.sha1(password.encode('utf-8')).hexdigest()
   timestamp = int(round(time.time() * 1000))

   requestBody = {"username":user_name,"type":1,"usertype":0}
   bodyValue = json.dumps(requestBody, separators=(',', ':'))
   body = json.loads(bodyValue)
   print(bodyValue)
   print(type(body))

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
      print(jsonOutput)
      output = json.loads(jsonOutput)
      #print(output['data']['clientID'])
      #print(output['data']['token'])
      #print(output['data']['userInfo']['userID'])
      with open("dmp-user.json","w") as f:
         json.dump(text, f)
         print(jsonOutput)
         print("Write user.json...")
      
      # menuOutput = json.dumps(output['data']['menus'], sort_keys=True, indent=2, separators=(',', ':'), ensure_ascii=False)
      # print(menuOutput)

   else:
      print(ret.status_code)


app_id = 'DMPPC'
url = 'http://47.105.110.157:9999/dmp/api/v1'
# url = 'http://192.168.3.165:8307'
# url = 'http://localhost:8308'
# url = 'http://8.142.169.69:8002/dmp/api/v1/users'
user_name = 'admin'
password = '123456'

if __name__ == '__main__':
   login()