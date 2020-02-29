package com.sena.invop.Home.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sena.invop.BuildConfig;
import com.sena.invop.Home.Interfaces.ItemView;
import com.sena.invop.Home.Interfaces.PresenterItem;
import com.sena.invop.Home.Model.Item;
import com.sena.invop.Home.Model.Products;
import com.sena.invop.Home.Util.BitmapProcessor;
import com.sena.invop.Home.presenter.PresenterItemImpl;
import com.sena.invop.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailItemActivity extends AppCompatActivity implements ItemView {

    private static final int GALLERY_REQUEST_CODE = 0001;
    private static final int CAMERA_REQUEST_CODE = 0002;
    private static final int MY_PERMISSIONS_REQUEST_READ_CAMERA = 0003;
    @BindView(R.id.content_info_item)
    LinearLayout layout;
    @BindView(R.id.progrres_load)
    ProgressBar progressBar;
    @BindView(R.id.image_product)
    ImageView img_product;
    String urlImg = "";
    @BindView(R.id.save_item)
    Button save;
    Item item;

    private PresenterItem presenter;
    private  String[] requiredPermissions;
    private String cameraFilePath;
    private String id;
    private List<Products> products = new ArrayList<>();

    private static final String TAG = "DetailItemActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        ButterKnife.bind(this);
        presenter = new PresenterItemImpl(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            id = bundle.getString("id");
            String type = bundle.getString("type");
            if (id != null ){
                presenter.getItem(id);
            }
        }


        String id = getIntent().getStringExtra("id");
        presenter.getItem(id);
    }

    @OnClick(R.id.image_product)
    void updateImg(){
        List<String> permission = new ArrayList<>(Arrays.asList(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE));
        requiredPermissions = permission.toArray(new String[permission.size()]);
        if (!hasAllPermissions()){
            ActivityCompat.requestPermissions(this,
                    requiredPermissions,
                    MY_PERMISSIONS_REQUEST_READ_CAMERA);
        }else {
            chooseOption();
        }

    }

    public void  chooseOption(){
        BottomSheetDialog dialog =  new BottomSheetDialog(this);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.choose_camera_gallery);
        LinearLayout camera = (LinearLayout) dialog.findViewById(R.id.content_camera);
        LinearLayout gallery = (LinearLayout) dialog.findViewById(R.id.content_gallery);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel_action);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
                dialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                dialog.dismiss();
            }
        });
        dialog.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            Uri uri;
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    uri  = data.getData();
                    img_product.setImageURI(uri);
                    uploadImg();
                    break;
                case CAMERA_REQUEST_CODE:
                    uri = Uri.parse(cameraFilePath);
                    img_product.setImageURI(uri);
                    uploadImg();

                    break;
            }

        }
    }
    private boolean hasAllPermissions() {
        // Check if we have all required permissions.
        for (String perm : requiredPermissions) {
            if (ActivityCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    void openCamera(){

        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }
    void openGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }
    @Override
    public void showLoad() {
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void hideLoad() {
        progressBar.setVisibility(View.GONE);
    }
    @Override
    public void error(String error) {
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void setItem(Item item) {
        for (Map.Entry k: item.getItemDetails().entrySet()) {
            View inflater = getLayoutInflater().inflate(R.layout.layout_info_item,null,false);
            TextView key = (TextView) inflater.findViewById(R.id.text_description_item);
            key.setText(k.getKey().toString());
            EditText value = (EditText) inflater.findViewById(R.id.value_item);
            value.setText(k.getValue().toString());
            layout.addView(inflater);
        }

    }
    @Override
    public void itemNotFound() {
        showProducts();
    }



    void uploadImg(){
        Bitmap img = ((BitmapDrawable) img_product.getDrawable()).getBitmap();
        BitmapProcessor bitmapProcessor = new BitmapProcessor(img,200,200);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapProcessor.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef = storageRef.child("Images").child("").child("Profile").child("profileUser.png");
        UploadTask task = mountainsRef.putBytes(data);

        Task<Uri> urlTask = task.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return mountainsRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    urlImg = downloadUri.toString();
                    save.setEnabled(true);
                    Glide.with(getApplicationContext()).load(urlImg).preload();
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }


    public AlertDialog showProducts(){
        String[] singleChoiceItems = new String[products.size()];
        final int[] position_single = {0};
        for (int i = 0; i <products.size() ; i++) {
            singleChoiceItems[i] = products.get(i).getNombre_producto();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailItemActivity.this);
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
