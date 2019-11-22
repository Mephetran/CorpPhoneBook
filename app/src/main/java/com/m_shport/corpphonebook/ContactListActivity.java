package com.m_shport.corpphonebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactListActivity extends AppCompatActivity {

    private RetrofitAdapter adapter;
    private RecyclerView recyclerView;
    public EditText search;
    private TextView error;

    public final static String BASE_URL = "http://example.com:port/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = findViewById(R.id.toolbar_list);
        toolbar.setTitle("Контакты");
        setSupportActionBar(toolbar);

        error = findViewById(R.id.error_msg);
        search = findViewById(R.id.filter);

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
                    createRView(response.body().getList());
                } else {
                    error.setText("Ошибка подключения\nПопробуйте позже");
                }
            }

            @Override
            public void onFailure(Call<PojoJson> call, Throwable t) {
                error.setText("Ошибка подключения\nПопробуйте позже");
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void createRView (List<ContactList> list) {

        recyclerView = findViewById(R.id.rv);
        adapter = new RetrofitAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ContactListActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
