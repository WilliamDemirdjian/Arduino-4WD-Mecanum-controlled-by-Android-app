/* Initialization */

void initialization() {
	// Motors PinMode
	// Motor 1
	pinMode(MOTOR1_A, OUTPUT);
	pinMode(MOTOR1_B, OUTPUT);
	// Motor 2
	pinMode(MOTOR2_A, OUTPUT);
	pinMode(MOTOR2_B, OUTPUT);
	// Motor 3
	pinMode(MOTOR3_A, OUTPUT);
	pinMode(MOTOR3_B, OUTPUT);
	// Motor 4
	pinMode(MOTOR4_A, OUTPUT);
	pinMode(MOTOR4_B, OUTPUT);
	
	// Motors DigitalWrite
	// Motor 1
	digitalWrite(MOTOR1_A, LOW);
	digitalWrite(MOTOR1_B, LOW);
	// Motor 2
	digitalWrite(MOTOR2_A, LOW);
	digitalWrite(MOTOR2_B, LOW);
	// Motor 3
	digitalWrite(MOTOR3_A, LOW);
	digitalWrite(MOTOR3_B, LOW);
	// Motor 4
	digitalWrite(MOTOR4_A, LOW);
	digitalWrite(MOTOR4_B, LOW);
	
	// Latch PinMode
	pinMode(MOTORLATCH, OUTPUT);
	pinMode(MOTORENABLE, OUTPUT);
	pinMode(MOTORDATA, OUTPUT);
	pinMode(MOTORCLK, OUTPUT);
	
	// Latch DigitalWrite
	digitalWrite(MOTORDATA, LOW);
  digitalWrite(MOTORLATCH, LOW);
  digitalWrite(MOTORCLK, LOW);
  digitalWrite(MOTORENABLE, LOW);
}
