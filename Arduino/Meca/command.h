/* Receives the command and processes it */

void receivedCommand(int command, int speed) {
  switch (command) {
    case 1: // right
      right(speed);
      break;
    case 2: // up right
      upRight(speed);
      break;
    case 3: // up
      up(speed);
      break;
    case 4: // up left
      upLeft(speed);
      break;
    case 5: // left
      left(speed);
      break;
    case 6: // down left
      downLeft(speed);
      break;
    case 7: // down
      down(speed);
      break;
    case 8: // down right
      downRight(speed);
      break;
    case 9: // turn around left
      turnAroundLeft(speed);
      break;
    case 10: // turn around right
      turnAroundRight(speed);
      break;
    case 11: // brake // not implemented in the app because the robot stops itself instantly when it does not receive a command
      brake();
      break;
    default: // freewheels
      freewheels();
      break;
  }
}
