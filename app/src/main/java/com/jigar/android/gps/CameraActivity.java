
package com.jigar.android.gps;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by COMP11 on 05-Jan-18.
 */

public class CameraActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 2500;
    ImageView imageview_photo;
    ImageView imageview_photo2,imageview_photo3,imageview_photo4,imageview_photo5,imageview_photo6,imageview_photo7,imageview_photo8,imageview_photo9,imageview_photo10,imageview_photo11,imageview_photo12;
    ImageView imageview_photo13,imageview_photo14,imageview_photo15,imageview_photo16,imageview_photo17,imageview_photo18;
    String latitude,logitude,vehicle_no,customer_nm,model_no;
    Button btn_sendMail;

   // RelativeLayout image_relative;
    LinearLayout linearLayout;
   // TextView tv_lat,tv_long,tv_dateTime;

    File file;
    Bitmap bitmap_edited;
    int n=1;
    ArrayList<Uri> arrayList=new ArrayList<Uri>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent get_intent = getIntent();
        latitude=get_intent.getStringExtra("key_latitude");//Get From MainActivity
        logitude=get_intent.getStringExtra("key_logitude");//Get From MainActivity

        customer_nm=get_intent.getStringExtra("key_cusomer_nm");//Get From MainActivity
        vehicle_no=get_intent.getStringExtra("key_vehical_no");//Get From MainActivity
        model_no=get_intent.getStringExtra("key_model");

        imageview_photo = (ImageView)findViewById(R.id.imageview_photo);//Used for Camera capture image display.
        imageview_photo2 = (ImageView)findViewById(R.id.imageview_photo2);//Used for Camera capture image display.
        imageview_photo3 = (ImageView)findViewById(R.id.imageview_photo3);//Used for Camera capture image display.
        imageview_photo4 = (ImageView)findViewById(R.id.imageview_photo4);//Used for Camera capture image display.
        imageview_photo5 = (ImageView)findViewById(R.id.imageview_photo5);//Used for Camera capture image display.
        imageview_photo6 = (ImageView)findViewById(R.id.imageview_photo6);//Used for Camera capture image display.
        imageview_photo7 = (ImageView)findViewById(R.id.imageview_photo7);//Used for Camera capture image display.
        imageview_photo8 = (ImageView)findViewById(R.id.imageview_photo8);//Used for Camera capture image display.
        imageview_photo9 = (ImageView)findViewById(R.id.imageview_photo9);//Used for Camera capture image display.
        imageview_photo10= (ImageView)findViewById(R.id.imageview_photo10);//Used for Camera capture image display.
        imageview_photo11= (ImageView)findViewById(R.id.imageview_photo11);//Used for Camera capture image display.
        imageview_photo12= (ImageView)findViewById(R.id.imageview_photo12);//Used for Camera capture image display.

        imageview_photo13 = (ImageView)findViewById(R.id.imageview_photo13);//Used for Camera capture image display.
        imageview_photo14 = (ImageView)findViewById(R.id.imageview_photo14);//Used for Camera capture image display.
        imageview_photo15 = (ImageView)findViewById(R.id.imageview_photo15);//Used for Camera capture image display.
        imageview_photo16= (ImageView)findViewById(R.id.imageview_photo16);//Used for Camera capture image display.
        imageview_photo17= (ImageView)findViewById(R.id.imageview_photo17);//Used for Camera capture image display.
        imageview_photo18= (ImageView)findViewById(R.id.imageview_photo18);//Used for Camera capture image display.



        btn_sendMail = (Button)findViewById(R.id.btn_sendMail);
        linearLayout= (LinearLayout) findViewById(R.id.linear_image);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//Used for Open a camera.
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
//            dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //    FileName="IMG_"+ timeStamp + ".jpg";
//            output=new File(dir,FileName );
   //         takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
            startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
        }
        btn_sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Uri rrr = Uri.parse("file:///storage/emulated/0/DCIM/Camera/Img_20171225_194223340.jpg");
                Intent i = new Intent(Intent.ACTION_SEND_MULTIPLE);
                i.setType("message/rfc822");
                i.setType("application/image");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"jigar_rughani612@yahoo.in"});//jigar.mca612@gmail.com
                i.putExtra(Intent.EXTRA_SUBJECT, "Vehicle Details");
                i.putExtra(Intent.EXTRA_TEXT   , "Customer Name: "+customer_nm+ "\n Vehicle No: "+vehicle_no+"\n Model No: "+model_no);
                i.setType("image/*");
                i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                i.putExtra(Intent.EXTRA_STREAM,arrayList);
                try
                {
                      startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(CameraActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && resultCode==RESULT_OK) {

            Bitmap image = (Bitmap) data.getExtras().get("data");

          //  ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
          //  image.compress(Bitmap.CompressFormat.PNG, 100, bytestream);

        //    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        //    image.compress(Bitmap.CompressFormat.JPEG,100,stream1);

          //  byte[] byteArray = stream1.toByteArray();
          //  Bitmap image = BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);

            //Using Paint Method save text on image.
            bitmap_edited = addText(image);
            if(n==1)
            {
                imageview_photo.setImageBitmap(bitmap_edited);
            }
            if(n==2)
            {
                imageview_photo2.setImageBitmap(bitmap_edited);
            }
            if(n==3)
            {
                imageview_photo3.setImageBitmap(bitmap_edited);
            }
            if(n==4)
            {
                imageview_photo4.setImageBitmap(bitmap_edited);
            }
            if(n==5)
            {
                imageview_photo5.setVisibility(View.VISIBLE);
                imageview_photo5.setImageBitmap(bitmap_edited);
            }
            if(n==6)
            {
                imageview_photo6.setVisibility(View.VISIBLE);
                imageview_photo6.setImageBitmap(bitmap_edited);
            }
            if(n==7)
            {
                imageview_photo7.setVisibility(View.VISIBLE);
                imageview_photo7.setImageBitmap(bitmap_edited);
            }
            if(n==8)
            {
                imageview_photo8.setVisibility(View.VISIBLE);
                imageview_photo8.setImageBitmap(bitmap_edited);
            }
            if(n==9)
            {
                imageview_photo9.setVisibility(View.VISIBLE);
                imageview_photo9.setImageBitmap(bitmap_edited);
            }
            if(n==10)
            {
                imageview_photo10.setVisibility(View.VISIBLE);
                imageview_photo10.setImageBitmap(bitmap_edited);
            }
            if(n==11)
            {
                imageview_photo11.setVisibility(View.VISIBLE);
                imageview_photo11.setImageBitmap(bitmap_edited);
            }
            if(n==12)
            {
                imageview_photo5.setVisibility(View.VISIBLE);
                imageview_photo12.setImageBitmap(bitmap_edited);
            }

            //New.
            if(n==13)
            {
                imageview_photo13.setVisibility(View.VISIBLE);
                imageview_photo13.setImageBitmap(bitmap_edited);
            }
            if(n==14)
            {
                imageview_photo14.setVisibility(View.VISIBLE);
                imageview_photo14.setImageBitmap(bitmap_edited);
            }
            if(n==15)
            {
                imageview_photo15.setVisibility(View.VISIBLE);
                imageview_photo15.setImageBitmap(bitmap_edited);
            }
            if(n==16)
            {
                imageview_photo16.setVisibility(View.VISIBLE);
                imageview_photo16.setImageBitmap(bitmap_edited);
            }
            if(n==17)
            {
                imageview_photo17.setVisibility(View.VISIBLE);
                imageview_photo17.setImageBitmap(bitmap_edited);
            }
            if(n==18)
            {
                imageview_photo18.setVisibility(View.VISIBLE);
                imageview_photo18.setImageBitmap(bitmap_edited);

            }


         //   imageview_photo.setImageBitmap(bitmap_edited);
            File cache = getApplicationContext().getExternalCacheDir();
            Random gen = new Random();
            int n = 10000;
            n = gen.nextInt(n);
            String fotoname = "Photo-"+ n +".jpg";
            file = new File(cache, fotoname);
            try{
                OutputStream stream = null;
                stream = new FileOutputStream(file);
                bitmap_edited.compress(Bitmap.CompressFormat.PNG,100,stream);
                stream.flush();
                stream.close();
            }catch (IOException e) // Catch the exception
            {
                e.printStackTrace();
            }
            Uri uri1 = Uri.parse("file://" +  file);
            arrayList.add(uri1);
        }
        //If user want more capturing Images then.
        android.app.AlertDialog.Builder alertbox = new android.app.AlertDialog.Builder(CameraActivity.this);
        alertbox.setMessage("Do you want more capture? ")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(DialogInterface dialog, int which)
                    {
                        n++;
                        ImageView imageView=new ImageView(CameraActivity.this);
                        linearLayout.addView(imageView);
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//Used for Open a camera.
                        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                        {
                            startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                android.app.AlertDialog dialog = alertbox.create();
                dialog.show();

    }
        private Bitmap addText(Bitmap toEdit){//Make a function for add text on bitmap image(capture image).

        Bitmap dest = toEdit.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(dest);


        Paint paint = new Paint(Paint.DEV_KERN_TEXT_FLAG);  //Also ANTI_ALIAS_FLAG IS PERFECT     Here changes -> Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //paint.setShadowLayer(1.0f, 1.0f, 1.0f, Color.BLACK);

//        int pictureHeight = dest.getHeight();               // 1.This is working good
//        paint.setTextSize(pictureHeight * .04629f);         // 2.

        Resources resources = getApplicationContext().getResources(); //Add new for the better resolution of font
        float scale = resources.getDisplayMetrics().density;
        paint.setTextSize((int) (3.8 * scale));
       // paint.setShadowLayer(0.3f, 0.3f, 0.3f, Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);

        int top=imageview_photo.getTop();
        int left=imageview_photo.getLeft();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        canvas.drawText("LAT: "+latitude ,left,top+15, paint);
        canvas.drawText("LONG: "+logitude ,top,left+30, paint);
        canvas.drawText("Date Time: "+formattedDate,left,top+45, paint);

        return dest;
    }
}
