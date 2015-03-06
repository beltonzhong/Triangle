#!/bin/bash
rm testData.txt
touch testData.txt
counter=0;
while [ $counter -lt 28 ]
do
	echo $RANDOM >> testData.txt
	((counter+=1));
done
cp testData.txt ./out/production/Triangle/testData.txt
cd out
cd production
cd Triangle
java Triangle
rm testData.txt
