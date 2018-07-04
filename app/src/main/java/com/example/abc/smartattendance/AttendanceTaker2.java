package com.example.abc.smartattendance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class AttendanceTaker2 extends AppCompatActivity {
    private Button mLoadBtn2;
    private ImageView mImageView2;
    private static int CAMERA_REQUEST_CODE = 1;
    private StorageReference mStorage;
    public int score;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_taker2);
        mStorage = FirebaseStorage.getInstance().getReference();
        // btnprocess=(Button)findViewById(R.id.btnprocess);
        databaseReference= FirebaseDatabase.getInstance().getReference("data2");

        mLoadBtn2 = (Button) findViewById(R.id.btnuploadfromcamera2);
        mImageView2 = (ImageView) findViewById(R.id.imgviewretrievefromfirebase2);

        mLoadBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode== RESULT_OK){
            //get the camera image
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

//set the image into imageview
            final Bitmap myBitmap=bitmap;
            mImageView2.setImageBitmap(bitmap);
            final Paint rectPaint=new Paint();
            rectPaint.setStrokeWidth(5);
            rectPaint.setColor(Color.WHITE);
            rectPaint.setStyle(Paint.Style.STROKE);
            final Bitmap tempBitmap=Bitmap.createBitmap(myBitmap.getWidth(),myBitmap.getHeight(),Bitmap.Config.RGB_565);
            final Canvas canvas=new Canvas(tempBitmap);
            canvas.drawBitmap(myBitmap,0,0,null);
            /*************** UPLOADS THE PIC TO FIREBASE***************/
            //Firebase storage folder where you want to put the images


          /*  StorageReference storageRef = FirebaseStorage.getInstance().getReference();

//name of the image file (add time to have different files to avoid rewrite on the same file)

            StorageReference imagesRef = storageRef.child("filename" + new Date().getTime());

//upload image

            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(),"Sending failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Toast.makeText(getApplicationContext(),"Sending done", Toast.LENGTH_LONG).show();
*/


                  /*  Uri downloadUri = taskSnapshot.getDownloadUrl();

                    Picasso.with(MainActivity.this).load(downloadUri).fit().centerCrop().into(mImageView);*/

/*********APPLYING FACE DETECTION USING GOOGLE VISION*************************/
            FaceDetector faceDetector=new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
                    .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                    .setMode(FaceDetector.FAST_MODE)
                    .build();
            if(!faceDetector.isOperational()){
                Toast.makeText(AttendanceTaker2.this,"face detector can not be set up",Toast.LENGTH_LONG).show();
                return;

            }
            Frame frame=new Frame.Builder().setBitmap(myBitmap).build();
            SparseArray<Face> sparseArray=faceDetector.detect(frame);
            // System.out.println(sparseArray.size()+"####################################");
            for(int i=0;i<sparseArray.size();i++){

                Face face=sparseArray.valueAt(i);
                float x1=face.getPosition().x;
                float y1=face.getPosition().y;
                float x2=x1+face.getWidth();
                float y2=y1+face.getHeight();
                RectF rectF=new RectF(x1,y1,x2,y2);
                canvas.drawRoundRect(rectF,2,2,rectPaint);





            }
            mImageView2.setImageDrawable(new BitmapDrawable(getResources(),tempBitmap));
            //   System.out.println(sparseArray.size()+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");



            if(sparseArray.size()!=0){
                      /* Intent i1=new Intent(AttendanceTaker.this,PresentActivity.class);
                        startActivity(i1);
                        finish();*/
                       /* System.out.println(databaseReference.getDatabase().getReference()+"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");    ;
                        System.out.println(databaseReference.getDatabase().toString()+"ooooooooooooooooooooooooooooooo");    ;
                        score++;
                        System.out.println(score+"++++++++++++++++++++++++++++++++++++++++++++++++");




*/
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Integer adc = dataSnapshot.getValue(Integer.class);
                        score=adc;
                        // tvscore.setText(""+  adc);
                        System.out.println(adc+"=========================================");


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }


                });

                score++;

                Intent i1=new Intent(AttendanceTaker2.this,PresentActivity2.class);
                startActivity(i1);

            }
            else if(sparseArray.size()==0){
                Intent i1=new Intent(AttendanceTaker2.this,AbsentActivity2.class);
                startActivity(i1);

                // System.out.println(score+"-----------------------------------------------");


            }

            else{

                Toast.makeText(AttendanceTaker2.this,"Kindly Try Again",Toast.LENGTH_LONG).show();
                return;
            }
            databaseReference.setValue(score);






        }

    }

}
