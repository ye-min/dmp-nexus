#coding=utf8
import sys
import os

current_directory = os.path.dirname(os.path.abspath(__file__))
relative_path = os.path.join(current_directory, "../")

sys.path.append(relative_path)
from dmp import showResponse, getResponse

def post():
   apiUrl = '/users/student/studentList'

   param = {
      "student_id": -1,
      "class_id": -1,
      "student_name": '',
      "student_code": '',
      "state": -1,
      "rows": 1,
      "pageid": 1,
   }
   showResponse(apiUrl, param)


if __name__ == '__main__':
   # 2.2.1
   post()
