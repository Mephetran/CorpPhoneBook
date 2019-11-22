package com.m_shport.corpphonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DetailsActivity extends AppCompatActivity {

    private TextView surname, name, department, jobtitle, mobile, callText, smsText, whatsappText;
    private ShareActionProvider shareActionProvider;
    private FloatingActionButton fab, fab1, fab2, fab3;
    private Animation showFab, hideFab;
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        fab = findViewById(R.id.fab);
        fab1 = findViewById(R.id.sel_1);
        fab2 = findViewById(R.id.sel_2);
        fab3 = findViewById(R.id.sel_3);

        hideFab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_hide);
        showFab = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_show);

        callText = findViewById(R.id.call_text);
        smsText = findViewById(R.id.sms_text);
        whatsappText = findViewById(R.id.whatsapp_text);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    callText.setVisibility(View.INVISIBLE);
                    smsText.setVisibility(View.INVISIBLE);
                    whatsappText.setVisibility(View.INVISIBLE);
                    fab3.startAnimation(hideFab);
                    fab2.startAnimation(hideFab);
                    fab1.startAnimation(hideFab);
                    fab3.setClickable(false);
                    fab2.setClickable(false);
                    fab1.setClickable(false);
                    isOpen = false;
                } else {
                    callText.setVisibility(View.VISIBLE);
                    smsText.setVisibility(View.VISIBLE);
                    whatsappText.setVisibility(View.VISIBLE);
                    fab3.startAnimation(showFab);
                    fab2.startAnimation(showFab);
                    fab1.startAnimation(showFab);
                    fab3.setClickable(true);
                    fab2.setClickable(true);
                    fab1.setClickable(true);
                    isOpen = true;
                }
            }
        });

        Intent intent = getIntent();
        String surnameText = intent.getStringExtra("surname");
        String nameText = intent.getStringExtra("name");
        String departmentText = intent.getStringExtra("department");
        String jobtitleText = intent.getStringExtra("jobtitle");
        final String mobileText = intent.getStringExtra("mobile");

        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setTitle(surnameText);
        setSupportActionBar(toolbar);

        surname = findViewById(R.id.surname);
        name = findViewById(R.id.name);
        department = findViewById(R.id.department);
        jobtitle = findViewById(R.id.jobtitle);
        mobile = findViewById(R.id.mobile);

        surname.setText(surnameText);
        name.setText(nameText);
        department.setText(departmentText);
        jobtitle.setText(jobtitleText);
        mobile.setText(mobileText);

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + mobileText));
                startActivity(callIntent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.fromParts("sms", mobileText, null));
                startActivity(smsIntent);
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whatsappCheck("com.whatsapp")) {
                    Uri uri = Uri.parse("smsto:" + mobileText.replaceFirst("8", "+7"));
                    Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO, uri);
                    whatsappIntent.setPackage("com.whatsapp");
                    startActivity(whatsappIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "У Вас нет WhatsApp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean whatsappCheck (String uri) {
        PackageManager packageManager = getPackageManager();
        boolean appCheck = false;
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            appCheck = true;
        } catch (PackageManager.NameNotFoundException ex) {
            appCheck = false;
        }
        return appCheck;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent(surname.getText() + " " + name.getText() + "\n" + mobile.getText());
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent (String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }
}
