#!/bin/bash

# source: https://git.chalmers.se/courses/dit113/2024/group-2/dit-113-ci-workshop-group-2/ & https://codeblog.dotsandbrackets.com/gitlab-ci-esp32-arduino/

apt-get update
cd ~

# Install arduino-cli
apt-get install curl -y
curl -fsSL https://raw.githubusercontent.com/arduino/arduino-cli/master/install.sh | sh
export PATH=$PATH:/root/bin
arduino-cli -version


# Install Wio Terminal core
printf "board_manager:\n  additional_urls:\n    - https://files.seeedstudio.com/arduino/package_seeeduino_boards_index.json\n" > .arduino-cli.yaml
arduino-cli core update-index --config-file .arduino-cli.yaml
arduino-cli core install Seeeduino:samd --config-file .arduino-cli.yaml

# Install correct libraries
arduino-cli lib install "Seeed Arduino rpcWiFi@1.0.6"
arduino-cli lib install "PubSubClient@2.8"
arduino-cli lib install "Seeed Arduino RTC@2.0.0"
arduino-cli lib install "ArduinoQueue@1.2.5"
arduino-cli lib install "DHT sensor library@1.4.6"

cd -
