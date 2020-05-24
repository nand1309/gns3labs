#!/bin/bash
clear
date
PS3='Please enter the selet the site code you want to check the connectivity: '
remotesites=("01-area-0" "02-area-65" "03-area-78" "04-area-90" "05-area-121")
select site in "${remotesites[@]}"
do

a0='A-location: street-area0\nservice provider: XXXXXXXXX\nContact number: XXXXXXXXXX \ncircuit ID: XXXXXXXX\n'
a65='Z-location: street-area65'
a78='Z-location: street-area78'
a90='Z-location: street-area90'
a111='Z-location: street-area111'
a112='Z-location: street-are112'

case "$site" in

"01-area-0")
ping -c3 -i0.2 10.51.0.1 > /dev/null
if [ $? -eq 0 ]; then
    ping -c3 -i0.2 10.51.0.2 > /dev/null
    if [ $? -eq 0 ]; then
        echo -e "Primary path is up"
    else
        echo -e "10.51.0.2: primary path is down at area0"
        echo -e "$a65"
        echo -e "$a0"
        ping -c3 -i0.2 10.41.0.1 > /dev/null
        if [ $? -eq 0 ]; then
            ping -c3 -i0.2 10.41.0.2 > /dev/null
                if [ $? -eq 0 ]; then
                    echo -e "Secondary path is up"
                else
                    echo -e "10.41.0.2: P2P is down for primary and secondary path at area0"
                    echo -e "$a65"
                    echo -e "$a0"
                fi
        else
            echo -e "10.41.0.1: P2P is down for primary and secondary path at area65"
            echo -e "$a65"
            echo -e "$a0"
        fi
    fi
else
    echo -e "10.51.0.1: primary path is down at area51\n1.open ticket with ISP\n2.check if device is powered off or site lost the power"
    echo -e "$a65"
    echo -e "$a0"
    ping -c3 -i0.2 10.41.0.1 > /dev/null
        if [ $? -eq 0 ]; then
            ping -c3 -i0.2 10.41.0.2 > /dev/null
                if [ $? -eq 0 ]; then
                    echo -e "Secondary path is up"
                else
                    echo -e "10.41.0.2: P2P is down for primary and secondary path at area0"
                    echo -e "$a65"
                    echo -e "$a0"
                fi
        else
            echo -e "10.41.0.1: P2P is down for primary and secondary path at area65"
            echo -e "$a65"
            echo -e "$a0"
        fi
fi

ping -c3 -i0.2 8.8.8.8 > /dev/null
if [ $? -eq 0 ]; then
    echo "area 8 is reachable"
else
    echo "area 8 is unreachable"
fi
traceroute 8.8.8.8

;;

*)
echo -e "invalid selection"
;;
esac
done