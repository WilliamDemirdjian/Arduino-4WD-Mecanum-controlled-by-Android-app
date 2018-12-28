package comwilliamdemirci.github.meca;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {
    // Theme
    private Switch themeSwitch;
    private ConstraintLayout mainLayout;
    // Layout control
    private JoystickView joystickRight;
    private ImageView turnAroundLeftButton;
    private ImageView turnAroundRightButton;
    private SeekBar speedBar;
    // Bluetooth
    private ImageView disconnectBluetoothDeviceButton;
    private String selectedBluetoothaDeviceAdress = null;
    private ProgressDialog progressDialogConnection;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothSocket bluetoothSocket = null;
    private boolean bluetoothConnected = false;
    static UUID bluetoothDeviceUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // Control
    private int move = 0;
    private int speed = 100;
    // Logs
    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        setTheme();
        setBluetooth();
        setDisconnectBluetoothDevice();
        sendData();
    }

    private void getBluetoothAddress() { // get bluetooth address of selected device from SelectBluetoothDeviceActivity
        Intent selectBluetoothDeviceIntent = getIntent();
        selectedBluetoothaDeviceAdress = selectBluetoothDeviceIntent.getStringExtra(SelectBluetoothDeviceActivity.EXTRA_SELECTED_BLUETOOTH_DEVICE_ADDRESS);
    }

    private void setBluetooth() {
        getBluetoothAddress();

        new ConnectBluetooth().execute();
    }

    private void setDisconnectBluetoothDevice() {
        disconnectBluetoothDeviceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disconnectBluetoothDevice();
            }
        });
    }

    private void disconnectBluetoothDevice() {
        if (bluetoothSocket!=null) {
            try {
                bluetoothSocket.close();
                Toast.makeText(getApplicationContext(), "Disconnected", Toast.LENGTH_LONG).show();
            }
            catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Could not disconnect to the device", Toast.LENGTH_LONG).show();
            }
        }
        finish(); // go back to the SelectBluetoothDeviceActivity
    }

    /*private void turnOffLed() {
        if (bluetoothSocket!=null) {
            try {
                bluetoothSocket.getOutputStream().write("0".toString().getBytes());
            }
            catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Couldn't send data to the device", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void turnOnLed() {
        if (bluetoothSocket!=null) {
            try {
                bluetoothSocket.getOutputStream().write("1".toString().getBytes());
            }
            catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Couldn't send data to the device", Toast.LENGTH_LONG).show();
            }
        }
    }*/

    private class ConnectBluetooth extends AsyncTask<Void, Void, Void> {
        private boolean connected = true;

        @Override
        protected void onPreExecute() {
            progressDialogConnection = ProgressDialog.show(MainActivity.this, "Connection", "Please wait during the connection.");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) { // while onPreExecute()
            try {
                if (bluetoothSocket == null || !bluetoothConnected) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // create a bluetooth adapter
                    BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(selectedBluetoothaDeviceAdress); // connect to the bluetooth device
                    bluetoothSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(bluetoothDeviceUUID); // type of bluetooth protocol
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery(); // stop bluetooth discovery
                    bluetoothSocket.connect();
                }
            }
            catch (IOException e) {
                connected = false;
//                Toast.makeText(getApplicationContext(), "Error : + " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!connected) {
                Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_LONG).show();
                finish(); // go back to the SelectBluetoothDeviceActivity
            }
            else {
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
                bluetoothConnected = true;
            }
            progressDialogConnection.dismiss();
        }
    }

    private void sendData() {
        @SuppressLint("DefaultLocale") String data = "s" + String.format("%02d", getMove()) + String.format("%03d", getSpeed());
        if (bluetoothSocket!=null) {
            try {
                bluetoothSocket.getOutputStream().write(data.getBytes());
            }
            catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Couldn't send data to the device", Toast.LENGTH_LONG).show();
            }
        }
    }

    private int getSpeed() {
        getSpeedBarValue();

        return speed;
    }

    private void getSpeedBarValue() {
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                i++;
                Log.d("Speed", "Seekbar : " + String.valueOf(progress) + " | i = " + String.valueOf(i));
                speed = progress;
                sendData();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private int getMove() {
        getJoystickMove();
        getTurnAroundAction();

        return move;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getTurnAroundAction() {
        // on turn around left button click
        turnAroundLeftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    turnAroundLeftButton.setImageResource(R.drawable.ic_turn_around_pressed_512dp);
                    Log.d("Move", "Turn around left pressed");
//                    turnOnLed();
                    move = 9;
                    sendData();
                }
                else {
                    turnAroundLeftButton.setImageResource(R.drawable.ic_turn_around_released_512dp);
                    Log.d("Move", "Turn around left released");
//                    turnOffLed();
                    move = 0;
                    sendData();
                }
                return true;
            }
        });

        // on turn around right button click
        turnAroundRightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    turnAroundRightButton.setImageResource(R.drawable.ic_turn_around_pressed_512dp);
                    Log.d("Move", "Turn around right pressed");
                    move = 10;
                    sendData();
                }
                else {
                    turnAroundRightButton.setImageResource(R.drawable.ic_turn_around_released_512dp);
                    Log.d("Move", "Turn around right released");
                    move = 0;
                    sendData();
                }
                return true;
            }
        });
    }

    private void getJoystickMove() {
        joystickRight.setOnMoveListener(new JoystickView.OnMoveListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onMove(int angle, int strength) {
                i++;
                if((angle > 337) || (angle <= 22) && (strength > 0)) { // right
                    Log.d("Move", "Joystick : " + String.valueOf(angle) + "° - right" +  " | i = " +String.valueOf(i));
                    move = 1;
                }
                else if((angle > 22) && (angle <= 67) && (strength > 0)) { // up right
                    Log.d("Move", "Joystick : " + String.valueOf(angle) + "° - up right" +  " | i = " +String.valueOf(i));
                    move = 2;
                }
                else if((angle > 67) && (angle <= 112) && (strength > 0)) { // up
                    Log.d("Move", "Joystick : " + String.valueOf(angle) + "° - up" +  " | i = " +String.valueOf(i));
                    move = 3;
                }
                else if((angle > 112) && (angle <= 157) && (strength > 0)) { // up left
                    Log.d("Move", "Joystick : " + String.valueOf(angle) + "° - up left" +  " | i = " +String.valueOf(i));
                    move = 4;
                }
                else if((angle > 157) && (angle <= 202) && (strength > 0)) { // left
                    Log.d("Move", "Joystick : " + String.valueOf(angle) + "° - left" +  " | i = " +String.valueOf(i));
                    move = 5;
                }
                else if((angle > 202) && (angle <= 247) && (strength > 0)) { // down left
                    Log.d("Move", "Joystick : " + String.valueOf(angle) + "° - down left" +  " | i = " +String.valueOf(i));
                    move = 6;
                }
                else if((angle > 247) && (angle <= 292) && (strength > 0)) { // down
                    Log.d("Move", "Joystick : " + String.valueOf(angle) + "° - down" +  " | i = " +String.valueOf(i));
                    move = 7;
                }
                else if((angle > 292) && (angle <= 337) && (strength > 0)) { // down right
                    Log.d("Move", "Joystick : " + String.valueOf(angle) + "° - down right" +  " | i = " +String.valueOf(i));
                    move = 8;
                }
                else {
                    move = 0;
                }
                sendData();
            }
        });
    }

    private void setTheme() { // change the theme if Switch is checked
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mainLayout.setBackgroundResource(R.color.colorBackgroundDark);
                }
                else {
                    mainLayout.setBackgroundResource(R.color.colorBackgroundLight);
                }
            }
        });
    }

    private void findViewById() { // find view elements
        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        themeSwitch = (Switch) findViewById(R.id.themeSwitch);
        joystickRight = (JoystickView) findViewById(R.id.joystick);
        turnAroundLeftButton = (ImageView) findViewById(R.id.turnAroundLeftButton);
        turnAroundRightButton = (ImageView) findViewById(R.id.turnAroundRightButton);
        speedBar = (SeekBar) findViewById(R.id.speedBar);
        disconnectBluetoothDeviceButton = (ImageView) findViewById(R.id.disconnectButton);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        disconnectBluetoothDevice();
    }
}