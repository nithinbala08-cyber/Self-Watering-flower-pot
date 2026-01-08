# Self-Watering Flower Pot

An automated plant watering system that monitors soil moisture levels and automatically waters your plants when needed. The system uses an Arduino microcontroller, a moisture sensor, a water pump, and an OLED display to provide real-time monitoring and control.

## Overview

This project implements a smart irrigation system that:
- Continuously monitors soil moisture levels
- Automatically activates a water pump when moisture drops below a threshold
- Displays real-time moisture percentage and pump status on an OLED screen
- Provides a live graph visualization of moisture levels over time

## Components

### Hardware
- **Arduino** (with Firmata firmware) - Main microcontroller
- **Soil Moisture Sensor** (Analog) - Connected to pin 17
- **Water Pump** - Controlled via MOSFET on pin 3
- **OLED Display** (SSD1306, 128x64) - I2C address 0x3C
- **MOSFET** - For controlling the water pump

### Software
- **Java** - Main programming language
- **Firmata4j** - Library for Arduino communication
- **JFreeChart** - For real-time graph visualization
- **Swing** - For GUI components

## How It Works

### Main Application (`FinalPart1.java`)

1. **Initialization**
   - Connects to Arduino via serial port (default: COM3)
   - Initializes the OLED display
   - Configures the moisture sensor (analog pin 17)
   - Configures the MOSFET/pump control (digital pin 3)
   - Creates a real-time graph window

2. **Main Loop**
   - Reads moisture sensor value every 0.5 seconds
   - Converts analog reading (0-1023) to moisture percentage (0-100%)
   - Checks moisture level every 50 iterations (~25 seconds)
   - If moisture < 50% (wet threshold), activates the pump
   - Updates OLED display with:
     - Current moisture percentage
     - Pump status (ON/OFF)
   - Updates the real-time graph with current moisture level
   - Stores moisture values in an ArrayList for tracking

3. **Watering Logic**
   - Threshold: 50% moisture level
   - When moisture drops below threshold, pump turns ON
   - Pump automatically turns OFF when not needed
   - Pump check occurs every 50 sensor readings to prevent rapid cycling

### Graph Visualization (`GraphTest.java`)

- Creates a time series chart showing moisture percentage over time
- Updates in real-time as new readings are taken
- X-axis: Time (formatted as HH:mm:ss.SS)
- Y-axis: Moisture percentage (0-100%)
- Window shows last 10 seconds of data

### Unit Test (`UnitTest.java`)

A test program that validates:
- Moisture sensor readings are within valid range (0-100%)
- MOSFET/pump control functionality
- Error handling for out-of-range values

## Setup Instructions

1. **Arduino Setup**
   - Upload Firmata firmware to your Arduino board
   - Connect components:
     - Moisture sensor → Analog pin 17
     - MOSFET gate → Digital pin 3
     - OLED display → I2C (address 0x3C)

2. **Java Dependencies**
   - Firmata4j library
   - JFreeChart library
   - Swing (included with Java)

3. **Configuration**
   - Update the serial port in `FinalPart1.java` (line 25) if not using COM3
   - Adjust moisture threshold (line 18) if needed (default: 50%)

4. **Running**
   - Compile: `javac -cp "path/to/libraries/*" src/*.java`
   - Run: `java -cp "path/to/libraries/*:src" FinalPart1`

## Features

- ✅ Automatic watering based on soil moisture
- ✅ Real-time OLED display showing moisture and pump status
- ✅ Live graph visualization of moisture trends
- ✅ Configurable moisture threshold
- ✅ Prevents pump cycling with intelligent timing
- ✅ Error handling and graceful shutdown

## Technical Details

- **Sensor Reading Frequency**: Every 500ms (0.5 seconds)
- **Pump Check Frequency**: Every 25 seconds (every 50 readings)
- **Moisture Calculation**: `percent = 100 - (value * 100 / 1023)`
- **Display Update**: Real-time, every sensor reading
- **Graph Window**: Shows last 10 seconds of data

## Notes

- Make sure your Arduino is connected and Firmata is uploaded before running
- The program runs indefinitely until stopped (Ctrl+C)
- The graph window must remain open for visualization
- Adjust the moisture threshold based on your plant's needs

