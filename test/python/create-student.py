#coding=utf8

import json
import requests
import hashlib
import time
import csv
from dataclasses import dataclass

@dataclass
class Student:
   code: str
   name: str
   gender: str
   group: str
   year: str
   mobile: str

def load_csv(csv_file):
   students = []
   try:
      with open(csv_file, 'r', encoding='utf-8') as file:
         csv_reader = csv.DictReader(file)

         for row in csv_reader:
            student = Student(
               code     = row['学号'],
               name     = row['姓名'],
               gender   = row['性别'],
               group    = row['行政班'],
               year     = row['年级'],
               mobile   = row['学生联系电话'],
            )
            students.append(student)

      print(f"Successfully loaded data from {csv_file}")
      return students
   except Exception as e:
      print(f"Error loading data from {csv_file}: {e}")
      return []

def create_student(student):
   global class_dic
   apiUrl = '/users/student/addStudent'
   gender = 1 if student.gender=='男' else 2
   class_id = 0
   class_name = student.group

   if class_name in class_dic:
      class_id = class_dic[class_name]

   param = {
      "student_name": student.name,
      "student_code": student.code,
      "gender": gender,
      "phone": student.mobile,
      "contact": "",
      "e_mail": "",
      "state": 1,
      "class_id": class_id,
      "class_name": class_name,
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
class_dic = {
    "22酒店1": 1,
    "22酒店2": 2,
    "22酒店3": 3,
}

if __name__ == '__main__':
   login()
   full_input_path = './student.csv'
   student_list = load_csv(full_input_path)
   for student in student_list:
      create_student(student)