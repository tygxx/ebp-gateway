#!/bin/bash

# jar包名称
JAR_NAME="`pwd`/ebp-gateway-0.0.1.jar"
echo "JAR: $JAR_NAME"

# profie
PROFILE_NAME=$1
if [ -z $PROFILE_NAME ]; then
	PROFILE_NAME="default"
fi
PROFILE_OPTS="--spring.profiles.active=$PROFILE_NAME"

# 记录pid的文件名
PID_FILE="$PROFILE_NAME.pid"
rm -rf $PID_FILE

# 日志目录
DIR_LOG="`pwd`/logs"
if [ ! -d $DIR_LOG ]; then
	mkdir $DIR_LOG
fi
STDOUT_FILE="$DIR_LOG/console-$PROFILE_NAME.out"
if [ ! -d $STDOUT_FILE ]; then
	STDOUT_FILE_BAK="$DIR_LOG/"console-$PROFILE_NAME-`date +%Y%m%d%H%M%S`.out" "
	mv $STDOUT_FILE $STDOUT_FILE_BAK
fi
echo "STDOUT: $STDOUT_FILE"

# 启动内存
JAVA_MEM_OPTS="-XX:MetaspaceSize=50m -XX:MaxMetaspaceSize=50m -Xms128m -Xmx128m"
echo "JAVA_MEM_OPTS: $JAVA_MEM_OPTS"
#JAVA_JMX_OPTS="-Dcom.sun.management.jmxremote -Djava.rmi.server.hostname=0.0.0.0 -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"

# 启动
nohup java $JAVA_MEM_OPTS $JAVA_JMX_OPTS -jar $JAR_NAME $PROFILE_OPTS > $STDOUT_FILE 2>&1 &

# 将pid写入文件
echo $! > $PID_FILE
echo "STATUS: runing, PID: $!"