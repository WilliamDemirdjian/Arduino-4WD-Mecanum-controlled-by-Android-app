/* Motor functions */

void latchPulse() { // generate a latch pulse
  digitalWrite(MOTORLATCH, HIGH);
  digitalWrite(MOTORLATCH, LOW);  
}

void shiftMotor(int motor, int state) {
  // use bitWrite to modify a bit of latch_state. Position of the bit is motor value. New value of the bit is state value (HIGH or LOW = 1 or 0)
  bitWrite(latch_state, motor, state);

  // when all the data has been sent, generate a new clock pulse by reversing MOTORCLK
  shiftOut(MOTORDATA, MOTORCLK, MSBFIRST, latch_state);
  latchPulse();
}

void moveMotor(int motor, int state, int speed) {
  int motorPWM;

  switch(motor) {
    case MOTOR1_A:
    case MOTOR1_B:
      motorPWM = MOTOR1_PWM;
      break;
    case MOTOR2_A:
    case MOTOR2_B:
      motorPWM = MOTOR2_PWM;
      break;
    case MOTOR3_A:
    case MOTOR3_B:
      motorPWM = MOTOR3_PWM;
      break;
    case MOTOR4_A:
    case MOTOR4_B:
      motorPWM = MOTOR4_PWM;
      break;
    default:
      break;
  }
  shiftMotor(motor, state);
  analogWrite(motorPWM, speed);
}

// move forward/move backwards/freewheels/brake a motor
void commandMotor(int motor, int move, int speed) {
  int motorA, motorB, motorPWM;

  // choose the motor
  switch(motor) {
    case MOTOR1:
      motorA = MOTOR1_A;
      motorB = MOTOR1_B;
      break;
    case MOTOR2:
      motorA = MOTOR2_A;
      motorB = MOTOR2_B;
      break;
    case MOTOR3:
      motorA = MOTOR3_A;
      motorB = MOTOR3_B;
      break;
    case MOTOR4:
      motorA = MOTOR4_A;
      motorB = MOTOR4_B;
      break;
    default:
      break;
  }

  // choose the move
  switch(move) {
    case FORWARD:
      moveMotor(motorA, HIGH, speed);
      moveMotor(motorB, LOW, -1);
      break;
    case BACKWARDS:
      moveMotor(motorA, LOW, speed);
      moveMotor(motorB, HIGH, -1);
      analogWrite(MOTOR2_PWM, speed);
      break;
    case FREEWHEELS:
      moveMotor(motorA, LOW, 0);
      moveMotor(motorB, LOW, -1);
      analogWrite(MOTOR3_PWM, speed);
      break;
    case BRAKE:
      moveMotor(motorA, LOW, 255);
      moveMotor(motorB, LOW, -1);
      analogWrite(MOTOR4_PWM, speed);
      break;
    default:
      break;
  }
}

void right(int speed) {
  commandMotor(MOTOR1, FORWARD, speed);
  commandMotor(MOTOR2, BACKWARDS, speed);
  commandMotor(MOTOR3, FORWARD, speed);
  commandMotor(MOTOR4, BACKWARDS, speed);
}

void upRight(int speed) {
  commandMotor(MOTOR1, FORWARD, speed);
  commandMotor(MOTOR2, FREEWHEELS, speed);
  commandMotor(MOTOR3, FORWARD, speed);
  commandMotor(MOTOR4, FREEWHEELS, speed);
}

void up(int speed) {
  commandMotor(MOTOR1, FORWARD, speed);
  commandMotor(MOTOR2, FORWARD, speed);
  commandMotor(MOTOR3, FORWARD, speed);
  commandMotor(MOTOR4, FORWARD, speed);
}

void upLeft(int speed) {
  commandMotor(MOTOR1, FREEWHEELS, speed);
  commandMotor(MOTOR2, FORWARD, speed);
  commandMotor(MOTOR3, FREEWHEELS, speed);
  commandMotor(MOTOR4, FORWARD, speed);
}

void left(int speed) {
  commandMotor(MOTOR1, BACKWARDS, speed);
  commandMotor(MOTOR2, FORWARD, speed);
  commandMotor(MOTOR3, BACKWARDS, speed);
  commandMotor(MOTOR4, FORWARD, speed);
}

void downLeft(int speed) {
  commandMotor(MOTOR1, BACKWARDS, speed);
  commandMotor(MOTOR2, FREEWHEELS, speed);
  commandMotor(MOTOR3, BACKWARDS, speed);
  commandMotor(MOTOR4, FREEWHEELS, speed);
}

void down(int speed) {
  commandMotor(MOTOR1, BACKWARDS, speed);
  commandMotor(MOTOR2, BACKWARDS, speed);
  commandMotor(MOTOR3, BACKWARDS, speed);
  commandMotor(MOTOR4, BACKWARDS, speed);
}

void downRight(int speed) {
  commandMotor(MOTOR1, FREEWHEELS, speed);
  commandMotor(MOTOR2, BACKWARDS, speed);
  commandMotor(MOTOR3, FREEWHEELS, speed);
  commandMotor(MOTOR4, BACKWARDS, speed);
}

void turnAroundLeft(int speed) {
  commandMotor(MOTOR1, BACKWARDS, speed);
  commandMotor(MOTOR2, FORWARD, speed);
  commandMotor(MOTOR3, FORWARD, speed);
  commandMotor(MOTOR4, BACKWARDS, speed);
}

void turnAroundRight(int speed) {
  commandMotor(MOTOR1, FORWARD, speed);
  commandMotor(MOTOR2, BACKWARDS, speed);
  commandMotor(MOTOR3, BACKWARDS, speed);
  commandMotor(MOTOR4, FORWARD, speed);
}

void freewheels() {
  commandMotor(MOTOR1, FREEWHEELS, 0);
  commandMotor(MOTOR2, FREEWHEELS, 0);
  commandMotor(MOTOR3, FREEWHEELS, 0);
  commandMotor(MOTOR4, FREEWHEELS, 0);
}

void brake() {
  commandMotor(MOTOR1, BRAKE, 0);
  commandMotor(MOTOR2, BRAKE, 0);
  commandMotor(MOTOR3, BRAKE, 0);
  commandMotor(MOTOR4, BRAKE, 0);
}
