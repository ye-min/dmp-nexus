#coding=utf8
import sys
import os

current_directory = os.path.dirname(os.path.abspath(__file__))
relative_path = os.path.join(current_directory, "../")

sys.path.append(relative_path)
from dmp import showResponse, getResponse

def post():
   apiUrl = '/users/student/addStudent'

   param = {
      "student_name":"张三1",
      "student_code":"202301002",
      "gender":"1",
      "phone":"",
      "contact":"",
      "e_mail":"",
      "state":1,
      "class_id":3,
      "class_name":"",
      "serialNo":"2023092216091188WIpGJ17"
   }
   showResponse(apiUrl, param)


if __name__ == '__main__':
   # 2.2.1
   post()
