#!/bin/
src="/home/svnproject/ebp-gateway"
dist="/home/yy/ebp-gateway/"

echo $src
echo $dist

cd $src
svn up
echo  "svn up is over"

sleep 5;
mvn clean package
echo  "mvn package over"

rm -rf $dist/*.jar
rm -rf $dist/lib
rm -rf $dist/config

cp -rf $src/target/*.jar   $dist
cp -rf $src/target/lib     $dist
cp -rf $src/target/config  $dist

echo "deploy successful"
sleep 3;
