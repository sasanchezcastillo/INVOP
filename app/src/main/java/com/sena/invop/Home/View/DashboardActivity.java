package com.sena.invop.Home.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sena.invop.Home.Adapter.ItemAdapter;
import com.sena.invop.Home.Interfaces.Dashboard;
import com.sena.invop.Home.Interfaces.PresenterDashboard;
import com.sena.invop.Home.Model.Item;
import com.sena.invop.Home.Model.Products;
import com.sena.invop.Home.presenter.PresenterDashboardImpl;
import com.sena.invop.Login.View.LoginActivity;
import com.sena.invop.ManagerSharePreferences.PreferenceData;
import com.sena.invop.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.awt.font.TextAttribute;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends AppCompatActivity implements SlidingUpPanelLayout.PanelSlideListener, TextWatcher , Dashboard {

    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    private static final String TAG = "DashboardActivity";

    @BindView(R.id.content_code_value)
    EditText value_code;

    @BindView(R.id.show_action_close)
    TextView show_action;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;
    @BindView(R.id.button_edit_item)
    Button button_action;
    @BindView(R.id.recycler_data_items)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.text_message)
            TextView text_message;

    PresenterDashboard presenterDashboard;
    private List<Products> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        presenterDashboard = new PresenterDashboardImpl(this);
        slidingUpPanelLayout.addPanelSlideListener(this);
        value_code.addTextChangedListener(this);
        presenterDashboard.getData();
        presenterDashboard.getProducts();
    }
    @OnClick(R.id.initial_scan_bar_code)
    public void openScan(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.MIXED_SCAN);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan Full");
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);
        if(result.getContents() == null) {
            Log.d("MainActivity", "Cancelled scan");
            value_code.setText("");
        } else {
            Log.d("MainActivity", "Scanned");
            //startNewView(result.getContents());
            value_code.setText(result.getContents());

        }
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {

    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
        if (newState.name().equalsIgnoreCase("Collapsed")){
            show_action.setVisibility(View.INVISIBLE);

        }else if (newState.name().equalsIgnoreCase("Expanded")){
            show_action.setVisibility(View.VISIBLE);

        }else if(newState.name().equalsIgnoreCase("ANCHORED")){
            show_action.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.button_edit_item)
    void openDetail(){
        if (button_action.getText().toString().equalsIgnoreCase("BUSCAR")){
            startNewView(value_code.getText().toString(),"edit",products,0);
        }else{
            if (products.size() < 0){
                notProducts();
            }else{
                showProducts().show();
            }
            //the  super code
        }
    }


    void startNewView(String id,String type,List<Products> products,int position){
        Intent intent = new Intent(getApplicationContext(),DetailItemActivity.class);
        Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("type",type);
            bundle.putInt("index",position);
            bundle.putSerializable("product", (Serializable) products);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }
    @Override
    public void afterTextChanged(Editable editable) {
        changeActionButton(validateEditext());
    }

    public boolean validateEditext(){
        if (value_code.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    void changeActionButton(boolean action){
        if (action){
            button_action.setText("BUSCAR");
        }else {
            button_action.setText("NUEVO PRODUCTO");
        }
    }

    @OnClick(R.id.action_logout)
    void logout(){
        PreferenceData.get(this).logout();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void setData(List<Item> data) {
        text_message.setVisibility(View.GONE);
        ItemAdapter adapter =  new ItemAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration =  new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.setData(data);
    }

    @Override
    public void setMessage(String message) {
        switch (message){
            case "0":
                text_message.setVisibility(View.VISIBLE);
                break;
        }
    }
    @Override
    public void showProgress(boolean status) {
        if (status){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setProducts(List<Products> products) {
        this.products = products;
        Log.e(TAG, "setProducts: " + products.size() );
    }

    @Override
    public void productsNotFount() {
        notProducts().show();

    }



    public AlertDialog notProducts(){
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
               builder.setTitle("Alerta");
                builder.setMessage("No se han creado prodcutos, Contacte al Administrador");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                return  builder.create();

    }
    public AlertDialog showProducts(){
        String[] singleChoiceItems = new String[products.size()];
        final int[] position_single = {0};
        for (int i = 0; i <products.size() ; i++) {
            singleChoiceItems[i] = products.get(i).getNombre_producto();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setTitle("Seleccione un Producto");
        builder.setSingleChoiceItems(singleChoiceItems, position_single[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                position_single[0] = i;
            }
        });
        builder.setPositiveButton("Crear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Log.e(TAG, "onClick: "+ position_single[0]);
                Log.e(TAG, "onClick: " +products.get(position_single[0]).getNombre_producto() );
                startNewView(null,"create",products,position_single[0]);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });



        return  builder.create();
    }
}
