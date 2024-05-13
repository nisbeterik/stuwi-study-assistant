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
   - "Seeed Arduino rpcWiFi@1.0.6" (WiFi)
   - "PubSubClient@2.8" (MQTT)
   - "Seeed Arduino RTC@2.0.0" (Built-in Real-Time CLock)
   - "ArduinoQueue@1.2.5" (Queue data structure)
   - "DHT sensor library@1.4.6" (DHT11 Temp/Humid)

5. Java SDK 21.0.2 [Download](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
6. [Apache Maven 4.0.0 Alpha](https://maven.apache.org/download.cgi)

### Hardware Components

**Microcontroller**
* [Seeed Wio Terminal](https://wiki.seeedstudio.com/Wio-Terminal-Getting-Started/)

**Sensors:**
* [Loudness](https://wiki.seeedstudio.com/Grove-Loudness_Sensor/)
* [Temperature + Humidity](https://wiki.seeedstudio.com/Grove-TemperatureAndHumidity_Sensor/)

**Actuators:** 
* [Real-Time Clock (RTC)](https://wiki.seeedstudio.com/Wio-Terminal-RTC/)
* [Buzzer](https://wiki.seeedstudio.com/Wio-Terminal-Buzzer/)

## Installation

## Usage

## Video Demo

## Team Members & Contributions

* Erik Nisbet @eriknis
* Edvin Sanfridsson @edvsan
* Johannes Borg @johabo
* Love Carlander Strandäng @loveca
* Martin Lidgren @marlidg





