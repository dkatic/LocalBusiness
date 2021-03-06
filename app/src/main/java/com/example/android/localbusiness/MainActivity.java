package com.example.android.localbusiness;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Locale myLocale;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    ImageView image;
    Dialog chooseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        // Dialog
        image = (ImageView) findViewById(R.id.dialogs);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDialog = new Dialog(MainActivity.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_host, null);
                String[] items = new String[]{"Hrvatski", "English"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,
                        android.R.id.text1, items);
                ListView listView = (ListView) dialogView.findViewById(R.id.list_view);
                listView.setAdapter(adapter);
                chooseDialog.setContentView(dialogView);
                chooseDialog.show();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.v("item", (String) parent.getItemAtPosition(position));
                        if (1 == position) {
                            Toast.makeText(parent.getContext(),
                                    "You have selected English", Toast.LENGTH_SHORT)
                                    .show();
                            setLocale("en");
                            chooseDialog.dismiss();
                        } else if (0 == position) {
                            Toast.makeText(parent.getContext(),
                                    "Odabrali ste Hrvatski", Toast.LENGTH_SHORT)
                                    .show();
                            setLocale("hr");
                            chooseDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    @SuppressWarnings("deprecation")
    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = res.getConfiguration();
        config.locale = myLocale;
        res.updateConfiguration(config, dm);
        finish();
        startActivity(getIntent());
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    // Map Method
    public void MapMethod(View view) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=45.2889922,18.7978083&mode=d");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    // Call
    public void makeCall(View view) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:032334473"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(Intent.createChooser(callIntent, getString(R.string.call)));
    }

    // Email
    public void sendEmail(View view) {
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "bagatelladoo@gmail.com", null));
        email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.upit));
        startActivity(Intent.createChooser(email, getString(R.string.send)));
    }

    // Browser
    public void goToUrl(View view) {
        Uri uriUrl = Uri.parse("http://www.companywall.hr/bagatella-doo/review/servis-motornih-pila-stihl-vinkovci/d-2339");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(Intent.createChooser(launchBrowser, getString(R.string.url)));
    }
}