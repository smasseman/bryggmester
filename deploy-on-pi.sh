#!/bin/bash

jar cf bryggmester.war -C webapp .
scp bryggmester.war pi@192.168.1.220:/home/pi/jetty-distribution-9.0.4.v20130625/webapps/
