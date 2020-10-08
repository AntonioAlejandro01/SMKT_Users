#!/bin/bash
if [ "$#" -eq 0 ]; then
	echo "You forgot to introduce the tag name";
	exit;
else
	echo "Image tag name is $1";
fi
echo "Creating docker image"
docker build . -t "smkt_users:$1"
