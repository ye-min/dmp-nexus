#coding=utf8
import sys
import os

current_directory = os.path.dirname(os.path.abspath(__file__))
relative_path = os.path.join(current_directory, "../")

sys.path.append(relative_path)
from dmp import showResponse, getResponse

def post():
   apiUrl = '/archive/dataset/list/page'

   param = {
      "pageIndex": 1,
      "pageSize": 10,
   }
   showResponse(apiUrl, param)


if __name__ == '__main__':
   # 2.2.1
   post()
