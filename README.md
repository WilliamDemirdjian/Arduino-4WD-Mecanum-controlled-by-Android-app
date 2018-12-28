# Arduino 4WD Mecanum controlled by Android app
Arduino 4WD Mecanum controlled by an Android app

## Application
The APK of the application can be downloaded here [APK][1].

### How to use the application?
1. Switch on the Arduino and the HC-05 Bluetooth module.

2. On your smartphone, scan the available Bluetooth devices and associate with the HC-05 Bluetooth module with the default password "1234".

<p align="center"><img src="https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/blob/master/Android/Screenshots/associate%20bluetooth%20module%20HC-05%20to%20your%20smartphone.png" width="280"></p>

3. Install and start the application. [Link of the APK][1]

4. Select the HC-05 Bluetooth module.

<p align="center"><img src="https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/blob/master/Android/Screenshots/mecanum%20wheel%20app%20select%20bluetooth%20module.png" width="500"></p>

5. Have fun!

The application contains:
- A joystrick and two buttons to control the robot
- A seekbar to control speed
- A switch in the top left to switch to night mode
- A button to disconnect from the robot

<p align="center"><img src="https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/blob/master/Android/Screenshots/mecanum%20wheel%20app.png" width="500"></p>

<p align="center"><img src="https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/blob/master/Android/Screenshots/mecanum%20wheel%20app%20night%20mode.png" width="500"></p>

## Arduino

### Bluetooth wiring

<p align="center"><img src="https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/blob/master/Arduino/Wiring/bluetooth.png" width="600"></p>

<strong>Don't forget to cross RX and TX!</strong>

### Motor shield wiring

<p align="center"><img src="https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/blob/master/Arduino/Wiring/L293D_motor_shield.png" width="600"></p>

### How is the robot controlled ?

<p align="center"><img src="https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/blob/master/Arduino/Movements/mecanum%20wheel%20movement.png" width="600"></p>

## Author
[WilliamDemirci][9]

## License
MIT License. See the [LICENSE][10] file for details.

## Other
Feel free to contact me if I can optimize the code or if there is an errorr. I'm a newbie. Thank you!


[1]: https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/blob/master/Apk/meca.apk
[2]: 
[3]: 
[4]: 
[5]: 
[9]: https://github.com/WilliamDemirci
[10]: https://github.com/WilliamDemirci/Arduino_4WD_Mecanum_controlled_by_Android_app/blob/master/LICENSE
