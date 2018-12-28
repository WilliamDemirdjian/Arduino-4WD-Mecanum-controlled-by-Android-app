/* Declaration of constants and variables */

// motors
#define MOTOR1 200
#define MOTOR2 201
#define MOTOR3 202
#define MOTOR4 203

// movements
#define FORWARD 100
#define BACKWARDS 101
#define FREEWHEELS 102
#define BRAKE 103

String receivedData = "";
int move = 0, speed = 0;
static int latch_state = 0;
