/* Arduino 4WD Mecanum controlled by Android app
 *  
 *  Hardware :
 *  Arduino Mega 2560
 *  L293D Motor Shield to control 4 DC motors
 *  HC-05 Bluetooth Module
 *  
 *  Free to use (MIT License)
 *  
 *  See the code on Git :
 *  https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app
 *  Download the application :
 *  https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/Apk/meca.apk
 *  See the wiring :
 *  https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/Arduino/Wiring
 *  /!\ The Bluetooth module can be powered with 5 Volts but the RX pin pf HC-05 must be 3.3 Volts.
 *      Because we only have 5 Volts free, we have to make a voltage divider to reduce the voltage by one third (5 * 2/3 = 3.33).
*/

#include "io.h" // Declaration of inputs and outputs
#include "var.h" // Declaration of constants and variables
#include "initialization.h" // Motor and latch first setup
#include "motor.h" // Motor functions
#include "command.h" // Receives the command and processes it

void setup() {
  initialization(); // setup latch and motors
  Serial1.begin(9600); // to receive data by Bluetooth with TX(18) and RX(19)

  // logs : to show logs on serial monitor
  Serial.begin(9600); // opens serial port, sets data rate to 9600 bps
  Serial.println("4WD Arduino Mecanum with omnidirectional wheels controlled with an Android application");
}

void loop() {
  if(Serial1.available() > 0) { // if we receive data
    // split the received frame
    // step 1 : to know when a frame starts, split received data into frames delimited by a 's'
    // step 2 : split the frame into two integer. The first two digits correspond to movement value and the last three to speed value
    // 
    // example : 
    //            - raw data received : s03200
    //            - step 1 : split data delimited by a 's' : 03200
    //            - step 2 : splitted data : 03 (move) and 200 (speed)
    receivedData = Serial1.readStringUntil('s'); // split received data into frames delimited by a 's'
    move = receivedData.substring(0,2).toInt(); // split two first digits to get movement value
    speed = receivedData.substring(2,5).toInt(); // split three last digits to get speed value

    // logs : show splitted data on serial monitor
    Serial.println(receivedData);
    Serial.println(move);
    Serial.println(speed);
    Serial.println(latch_state);
    // execute the command
    receivedCommand(move, speed);
  }
}
