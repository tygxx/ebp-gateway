#!/bin/bash

# profie
PROFILE_NAME=$1
if [ -z $PROFILE_NAME ]; then
  PROFILE_NAME="default"
fi

# 查找pid
PID=`cat $PROFILE_NAME.pid | awk '{print $1}'`
PID_EXISTS=`ps -ef|grep java | grep "$PID" | awk '{print $2}'`

if [ -z "$PID_EXISTS" ]; then
  echo "ERROR: The server does not started!"
  exit 1
fi

echo -e "STATUS: Stopping, PID: $PID"
kill $PID > /dev/null 2>&1
echo -e "STATUS: Stopped, PID: $PID"