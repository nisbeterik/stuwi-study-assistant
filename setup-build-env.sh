#!/bin/bash

apt-get update
cd ~

# Install arduino-cli
apt-get install curl -y
curl -L -o arduino-cli.tar.bz2 https://downloads.arduino.cc/arduino-cli/arduino-cli-latest-linux64.tar.bz2
tar xjf arduino-cli.tar.bz2
rm arduino-cli.tar.bz2
mv `ls -1` /usr/bin/arduino-cli

# Install Wio Terminal core
printf "board_manager:\n  additional_urls:\n    - https://files.seeedstudio.com/arduino/package_seeeduino_boards_index.json\n" > .arduino-cli.yaml
arduino-cli core update-index --config-file .arduino-cli.yaml
arduino-cli core install Seeeduino:samd --config-file .arduino-cli.yaml

arduino-cli lib install "Seeed Arduino rpcWiFi@1.0.6"
arduino-cli lib install "PubSubClient@2.8"
arduino-cli lib install "Seeed Arduino RTC@2.0.0"
arduino-cli lib install "ArduinoQueue@1.2.5"
arduino-cli lib install "DHT sensor library@1.4.6"

cd -
