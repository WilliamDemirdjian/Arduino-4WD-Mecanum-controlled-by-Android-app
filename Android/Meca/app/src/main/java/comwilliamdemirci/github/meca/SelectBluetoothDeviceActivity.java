package comwilliamdemirci.github.meca;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class SelectBluetoothDeviceActivity extends AppCompatActivity {
    private ListView pairedBluetoothDevicesListView;
    private BluetoothAdapter bluetoothAdapter = null;
    private Set<BluetoothDevice> pairedBluetoothDevices;
    public static String EXTRA_SELECTED_BLUETOOTH_DEVICE_ADDRESS = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bluetooth_device);

        findViewById();
        setBluetooth();
        getPairedBluetoothDevices();
    }

    private void setBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(bluetoothAdapter == null) { // if there is no bluetooth device
            Toast.makeText(getApplicationContext(), "There is no bluetooth device.", Toast.LENGTH_LONG).show();
            finish();
        }
        else if(!bluetoothAdapter.isEnabled()) { // if bluetooth is not enabled
            Intent askBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(askBluetoothIntent,1);
        }
    }

    private void findViewById() {
        pairedBluetoothDevicesListView = (ListView)findViewById(R.id.pairedBluetoothDevicesListView);
    }

    private void getPairedBluetoothDevices() {
        pairedBluetoothDevices = bluetoothAdapter.getBondedDevices();

        // display all paired bluetooth devices
        ArrayList pairedBluetoothDevicesList = new ArrayList();
        if (pairedBluetoothDevices.size() > 0) { // if there is at least one paired device
            for(BluetoothDevice device : pairedBluetoothDevices) {
                pairedBluetoothDevicesList.add(device.getName() + "\n" + device.getAddress());
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, pairedBluetoothDevicesList);
        pairedBluetoothDevicesListView.setAdapter(adapter);
        pairedBluetoothDevicesListView.setOnItemClickListener(myListClickListener);
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3) {
            // get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // send address to the MainActivity
            Intent mainActivityIntent = new Intent(SelectBluetoothDeviceActivity.this, MainActivity.class);
            mainActivityIntent.putExtra(EXTRA_SELECTED_BLUETOOTH_DEVICE_ADDRESS, address);
            startActivity(mainActivityIntent);
        }
    };
}