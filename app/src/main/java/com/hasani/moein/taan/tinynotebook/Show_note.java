package com.hasani.moein.taan.tinynotebook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import Data.DataBaseHandler;
import Data.DbBitmapUtility;
import Model.note;

public class Show_note extends AppCompatActivity {

    private static final String TAG = "a";
    //private MapView mapView;
    private AlertDialog.Builder alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note);

        //FragmentManager fmanager = getSupportFragmentManager();
      //  Fragment fragment = fmanager.findFragmentById(R.id.map);
//     /   SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
//        supportmapfragment.getMap();
        //mapView=(MapView)findViewById(R.id.Map);
        Button save_changes=(Button)findViewById(R.id.save_changes) ;
        ImageView imageView = (ImageView) findViewById(R.id.image);
        TextView time = (TextView) findViewById(R.id.time);
        TextView title = (TextView) findViewById(R.id.title);
        final EditText content = (EditText) findViewById(R.id.wish);
        TextView date = (TextView) findViewById(R.id.date);
        Button delete = (Button) findViewById(R.id.delete);

        final note note1 = (note) getIntent().getSerializableExtra("My object");

        title.setText(note1.getTitle());
        content.setText(note1.getContent());
        date.setText(note1.getDate());
        time.setText(note1.getTime());

        Log.v(TAG, "*****Bitmap = " + note1.getBitmap());


        if (note1.getBitmap() != null) {

            imageView.setImageBitmap(
                    getRoundedCornerBitmap(DbBitmapUtility.getImage(note1.getBitmap())));
           ////////////////////////////////////////////////////
        }


        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                note1.setContent(content.getText().toString());

                DataBaseHandler dataBaseHandler=new DataBaseHandler(getApplicationContext());

                dataBaseHandler.update_note(note1);

            }
        });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alert = new AlertDialog.Builder(Show_note.this);
                    alert.setTitle("Delete");
                    alert.setMessage("Do you want to delete this note ?");
                    alert.setCancelable(false);
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataBaseHandler dbh = new DataBaseHandler(getApplicationContext());
                            dbh.removenote(note1.getId());
                            finish();

                        }
                    });

                    AlertDialog alert_example = alert.create();
                    alert_example.show();


                }
            });



    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {//gerd kardan dore aks
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 20;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
//    public class Find_me_on_map extends FragmentActivity implements OnMapReadyCallback {
//
//        private GoogleMap mMap;
//        private Marker mMarker;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.show_note);
//
//            SupportMapFragment mapFragment = (SupportMapFragment)
//                    getSupportFragmentManager().findFragmentById();
//            mapFragment.getMapAsync(this);
//
//        }
//
//        @Override
//        public void onMapReady(GoogleMap googleMap){
//
//            mMap = googleMap;
//
//            try{
//                mMap.setMyLocationEnabled(true);
//
//                setUpMapIfNeeded();
//
//
//            } catch(SecurityException e){
//
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);
//                Toast.makeText(getApplicationContext(),"App doesn't have the permission to gps... ",Toast.LENGTH_SHORT).show();
//            }
//        }
//
//
//        public Marker setUpMapIfNeeded() {
//
//            try{
//                final LocationManager lm = (LocationManager) this.getSystemService(
//                        Context.LOCATION_SERVICE);
//                final Location myLoc = lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//                if (myLoc != null) {
//                    mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(myLoc.getLatitude(),
//                            myLoc.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("bluemarker", 60, 100))));
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLoc.getLatitude(),
//                            myLoc.getLongitude()), 16));
//                }
//            } catch(SecurityException e){
//
//                Toast.makeText(getApplicationContext(),"App doesn't have the permission to gps... ",Toast.LENGTH_SHORT).show();
//            }
//            return mMarker;
//        }
//        public Bitmap resizeMapIcons(String iconName, int width, int height){
//            Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
//            Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
//            return resizedBitmap;
//        }
//
//    }


}
