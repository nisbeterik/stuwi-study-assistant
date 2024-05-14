# StuWi Study Assistant

<div style="text-align:center">
    <img src="/docs/images/StuWi-Transparent.png" alt="StuWi Study Assistant" width="400" />
</div>

### Table of Contents
- [StuWi Study Assistant](#stuwi-study-assistant)
    - [Table of Contents](#table-of-contents)
    - [Purpose & Benefits](#purpose--benefits)
    - [System Design](#system-design-)
    - [Dependencies & Requirements](#dependencies--requirements)
        - [Hardware Components](#hardware-components)
    - [Installation](#installation)
    - [Usage](#usage)
    - [Video Demo](#video-demo)
    - [Team Members & Contributions](#team-members--contributions)

*Study + Wio Terminal = 'StuWi'*

## Purpose & Benefits

StuWi study assistant is a system created for students or anyone engaged in cognitive work that wants to maximize focus and performance. With sensors providing details of the work environment that caters to the specific users needs, it ensures that the conditions for focused work are optimal.

StuWi’s desktop application aims to provide a user-friendly interface to monitor sensor values in real time and plan out study sessions with ease so that all effort can be put into doing valuable work. With persistent data storage to track progress and other analytics, StuWi helps the user further optimize how they work to reach their goals.

## System Design 
Last updated May 11, 2024

![StuWi Study Assistant](/docs/images/architecture_diagrams/Architecture_DiagramV5.png)


## Dependencies & Requirements

This section outlines dependencies & requirements to run StuWi Study Assistant.

1. [Seeed Wio Terminal](https://wiki.seeedstudio.com/Wio_Terminal_Intro/)
2. [Arduino IDE](https://www.arduino.cc/en/software)
3. Wio Terminal Board Library. See: [Link](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/#getting-started)
4. Following Arduino Libraries:
   - "Seeed Arduino rpcWiFi@1.0.6" by Seeed Studio (WiFi)
   - "PubSubClient@2.8" by Nick O'Leary (MQTT)
   - "Seeed Arduino RTC@2.0.0" by Seeed Studio (Built-in Real-Time CLock)
   - "ArduinoQueue@1.2.5" by Einar Arnason (Queue data structure)
   - "DHT sensor library@1.4.6" by Adafruit (DHT11 Temp/Humid)
5. Java SDK 21.0.2 [Download](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
6. [Apache Maven 4.0.0 Alpha](https://maven.apache.org/download.cgi)

Other libraries used exist natively in Arduino IDE & Seeed Wio Terminal Board library

### Hardware Components

**Microcontroller**
* [Seeed Wio Terminal](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/)
* [Wio Terminal LCD](https://wiki.seeedstudio.com/Wio-Terminal-LCD-Overview/)

**Sensors:**
* [Loudness](https://wiki.seeedstudio.com/Grove-Loudness_Sensor/)
* [Temperature + Humidity](https://wiki.seeedstudio.com/Grove-TemperatureAndHumidity_Sensor/)

**Actuators:** 
* [Real-Time Clock (RTC)](https://wiki.seeedstudio.com/Wio-Terminal-RTC/)
* [Buzzer](https://wiki.seeedstudio.com/Wio-Terminal-Buzzer/)

## Installation

Before any steps below, clone or download the repo source code onto your local machine.

#### Wio Terminal

1. Install Arduino [IDE](https://www.arduino.cc/en/software)
2. Add Wio Terminal Board library to Arduino IDE See: [Link](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/#getting-started)
3. Install required Arduino Libraries outlined in [Dependencies & Requirements](#dependencies--requirements) through searching by name in Library Manager of Arduino IDE. See how to install libraries: [Link](https://docs.arduino.cc/software/ide-v1/tutorials/installing-libraries/) 
4. Change variables 'SSID' & 'PASSWORD' in [wifi.cpp](src/wio-StuWi/src/stuwi_sketch_main/wifi.cpp) to your network SSID & Password in your local cloned/downloaded repo
6. Upload sketch to Wio Terminal. See step 5 in: [Link](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/)


TODO: Sensor plugin
#### JavaFX Desktop Application

1. Download & install SDK 21.0.2 & Maven 4.0.0 outlined in [Dependencies & Requirements](#dependencies--requirements)
2. Run _mvn --version_ & _java --version_ in your machine's terminal to verify they've been installed correctly

## Usage
1. Connect the Wio Terminal to your computer using a USB-cable. Once the screen GUI interface shows up, the Wi-Fi has successfully connected. This may take a few seconds.
2. Using the command line terminal, navigate to the top directory of the application source code (where pom.xml is located).
3. Run the following line: **mvn javafx:run**
4. For instructions on how to navigate the StuWi application and its features, please see video demo below.

## Video Demo

## Team Members & Contributions

* Erik Nisbet @eriknis
* Edvin Sanfridsson @edvsan 
### Johannes Borg @johabo
#### Contributions:
* Helped with Mqtt connection and setting up maven.
* Worked on and implementing the sensors.
* Done more work on loudness sensor together with @loveca (issue #8).
* Implemented notification feature when trespassing loudness threshold.

Love Carlander Strandäng @loveca
* Martin Lidgren @marlidg





