//Name: DUNG LY         ID: N01327929
package dung.ly.n01327929;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DungActivity extends AppCompatActivity
{

    private AppBarConfiguration mAppBarConfiguration;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.dungtoolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.dungdrawer_layout);
        NavigationView navigationView = findViewById(R.id.dungnav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_dowload, R.id.nav_service, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.dungnav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.dungaction_help:
                Intent itent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.dota_url)));
                startActivity(itent);
                break;
            case R.id.action_location:
                getCurrentLocation();
                break;
            case R.id.action_sms:
                sendSMS();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                getCurrentLocation();
            } else
            {
                Toast.makeText(this, R.string.Permission_denied, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        NavController navController = Navigation.findNavController(this, R.id.dungnav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void sendSMS()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
            {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(getString(R.string.phone_num), null, getString(R.string.sms_test), null, null);
                Snackbar.make((DrawerLayout) findViewById(R.id.dungdrawer_layout), R.string.sms_sent, Snackbar.LENGTH_SHORT).show();
            } else
            {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);


            }
        }
    }

    private void getCurrentLocation()
    {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(DungActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        } else
        {
            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setInterval(1000);
            locationRequest.setFastestInterval(3000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            LocationServices.getFusedLocationProviderClient(DungActivity.this).requestLocationUpdates(locationRequest, new LocationCallback()
            {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult)
                {
                    super.onLocationResult(locationResult);
                    LocationServices.getFusedLocationProviderClient(DungActivity.this).removeLocationUpdates(this);
                    if (locationRequest != null && locationResult.getLocations().size() > 0)
                    {
                        int lastlocationint = locationResult.getLocations().size() - 1;
                        double latitude = locationResult.getLocations().get(lastlocationint).getLatitude();
                        double longitude = locationResult.getLocations().get(lastlocationint).getLongitude();
                        String resut = getString(R.string.Latitude) + latitude +
                                getString(R.string.Longitude) + longitude;
                        Snackbar.make((DrawerLayout) findViewById(R.id.dungdrawer_layout), resut, Snackbar.LENGTH_SHORT).show();
                    }
                }
            }, Looper.getMainLooper());
        }
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dung_ly_n01327929)
                .setCancelable(false)
                .setMessage(R.string.are_you_quit)
                .setPositiveButton(R.string.yes, (dialog, which) -> finish())
                .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel())
                .create()
                .show();
    }


}