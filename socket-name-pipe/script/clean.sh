#!/bin/bash
for p in $(ps aux|grep SamReceiver|awk '{print $2}'); do kill $p; done
