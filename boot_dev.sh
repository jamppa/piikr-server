#!/bin/bash
sudo docker stop mongodb
sudo docker rm mongodb
sudo docker run -d -p 27017:27017 -v `pwd`/code/db:/data/db --name mongodb dockerfile/mongodb
sudo docker run -ti --rm --name piikr_server -v `pwd`/code:/home/piikr/code -v ~/.m2:/home/piikr/.m2 --link mongodb:db -p 3000:3000 piikr/server