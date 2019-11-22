package com.m_shport.corpphonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String BASE_URL = "http://example.com:port/";
    public static final String APP_PREFERENCES = "config";
    public static final String SAVED_LOGIN = "login";
    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        animation.setStartOffset(150);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);

        final EditText phone = findViewById(R.id.pnum);
        phone.setSelection(phone.getText().length());
        final TextView warning = findViewById(R.id.warning);

        sp = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (sp.contains(SAVED_LOGIN)) {
            phone.setText(sp.getString(SAVED_LOGIN, ""));
        }

        warning.clearAnimation();
        warning.setVisibility(View.INVISIBLE);
        phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                warning.clearAnimation();
                warning.setVisibility(View.INVISIBLE);
            }
        });

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sp = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
                String loginPhone = phone.getText().toString();
                SharedPreferences.Editor e = sp.edit();
                e.putString(SAVED_LOGIN, loginPhone);
                e.commit();

                final String enterNum = phone.getText().toString();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                DataInterface dataInterface = retrofit.create(DataInterface.class);

                Call<PojoJson> call = dataInterface.getData();
                call.enqueue(new Callback<PojoJson>() {
                    @Override
                    public void onResponse(Call<PojoJson> call, Response<PojoJson> response) {
                        if (response.isSuccessful()) {
                            List<ContactList> listValue = response.body().getList();
                            for (int i = 0; i < listValue.size(); i++) {
                                String num = String.valueOf(listValue.get(i).getMobile());
                                if (checkNumber(enterNum, num)) {
                                    Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                                    startActivity(intent);
                                    break;
                                } else {
                                    warning.setText("Вас нет в базе!");
                                    warning.setVisibility(View.VISIBLE);
                                    warning.startAnimation(animation);
                                }
                            }
                        } else {
                            warning.setText("Проверьте подключение к сети");
                            warning.setVisibility(View.VISIBLE);
                            warning.startAnimation(animation);
                        }
                    }

                    @Override
                    public void onFailure(Call<PojoJson> call, Throwable t) {
                        warning.setText("Проверьте подключение к сети");
                        warning.setVisibility(View.VISIBLE);
                        warning.startAnimation(animation);
                    }
                });
            }
        });

        phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                    final String enterNum = phone.getText().toString();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    DataInterface dataInterface = retrofit.create(DataInterface.class);

                    Call<PojoJson> call = dataInterface.getData();
                    call.enqueue(new Callback<PojoJson>() {
                        @Override
                        public void onResponse(Call<PojoJson> call, Response<PojoJson> response) {
                            if (response.isSuccessful()) {
                                List<ContactList> listValue = response.body().getList();
                                for (int i = 0; i < listValue.size(); i++) {
                                    String num = String.valueOf(listValue.get(i).getMobile());
                                    if (checkNumber(enterNum, num)) {
                                        Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                                        startActivity(intent);
                                        break;
                                    } else {
                                        warning.setText("Вас нет в базе!");
                                        warning.setVisibility(View.VISIBLE);
                                        warning.startAnimation(animation);
                                    }
                                }
                            } else {
                                warning.setText("Проверьте подключение к сети");
                                warning.setVisibility(View.VISIBLE);
                                warning.startAnimation(animation);
                            }
                        }

                        @Override
                        public void onFailure(Call<PojoJson> call, Throwable t) {
                            warning.setText("Проверьте подключение к сети");
                            warning.setVisibility(View.VISIBLE);
                            warning.startAnimation(animation);
                        }
                    });
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView warning = findViewById(R.id.warning);
        warning.clearAnimation();
        warning.setVisibility(View.INVISIBLE);
        final EditText phone = findViewById(R.id.pnum);
        if (sp.contains(SAVED_LOGIN)) {
            phone.setText(sp.getString(SAVED_LOGIN, ""));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        TextView warning = findViewById(R.id.warning);
        warning.clearAnimation();
        warning.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        TextView warning = findViewById(R.id.warning);
        warning.clearAnimation();
        warning.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        TextView warning = findViewById(R.id.warning);
        warning.clearAnimation();
        warning.setVisibility(View.INVISIBLE);
    }

    public boolean checkNumber(String enterNumber, String numberFromList) {

        boolean num = false;

        if (enterNumber.length() == 10) {
            enterNumber = "8" + enterNumber;
        } else if (enterNumber.length() == 12) {
            enterNumber.replace("+7", "8");
        }

        if (enterNumber.equals(numberFromList)) {
            num = true;
        }
        return num;
    }
}
