package rnd.com.technodhaka.android.navigationdrawer;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

/**
 * free sample of the git hub project
 *
 * @see {@link : https://github.com/googlemaps/android-samples/blob/48b482aeabceaaddfb993290d50991619585597e/ApiDemos/app/src/main/java/com/example/mapdemo/StreetViewPanoramaEventsDemoActivity.java}
 */
public class StreetViewPanoramaEventsDemoActivity extends AppCompatActivity
        implements StreetViewPanorama.OnStreetViewPanoramaChangeListener, StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener,
        StreetViewPanorama.OnStreetViewPanoramaClickListener, StreetViewPanorama.OnStreetViewPanoramaLongClickListener {


    // George St, Sydney
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);

    private StreetViewPanorama mStreetViewPanorama;

    private TextView mPanoChangeTimesTextView;

    private TextView mPanoCameraChangeTextView;

    private TextView mPanoClickTextView;

    private TextView mPanoLongClickTextView;

    private int mPanoChangeTimes = 0;

    private int mPanoCameraChangeTimes = 0;

    private int mPanoClickTimes = 0;

    private int mPanoLongClickTimes = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view_panorama_events_demo);


        mPanoChangeTimesTextView = (TextView) findViewById(R.id.change_pano);
        mPanoCameraChangeTextView = (TextView) findViewById(R.id.change_camera);
        mPanoClickTextView = (TextView) findViewById(R.id.click_pano);
        mPanoLongClickTextView = (TextView) findViewById(R.id.long_click_pano);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        mStreetViewPanorama = panorama;
                        mStreetViewPanorama.setOnStreetViewPanoramaChangeListener(
                                StreetViewPanoramaEventsDemoActivity.this);
                        mStreetViewPanorama.setOnStreetViewPanoramaCameraChangeListener(
                                StreetViewPanoramaEventsDemoActivity.this);
                        mStreetViewPanorama.setOnStreetViewPanoramaClickListener(
                                StreetViewPanoramaEventsDemoActivity.this);
                        mStreetViewPanorama.setOnStreetViewPanoramaLongClickListener(
                                StreetViewPanoramaEventsDemoActivity.this);

                        // Only set the panorama to SYDNEY on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            mStreetViewPanorama.setPosition(SYDNEY);
                        }
                    }
                });
    }

    @Override
    public void onStreetViewPanoramaCameraChange(StreetViewPanoramaCamera streetViewPanoramaCamera) {
        mPanoCameraChangeTextView.setText("Times camera changed=" + ++mPanoCameraChangeTimes);
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation location) {
        if (location != null) {
            mPanoChangeTimesTextView.setText("Times panorama changed=" + ++mPanoChangeTimes);
        }
    }

    @Override
    public void onStreetViewPanoramaClick(StreetViewPanoramaOrientation orientation) {

        Point point = mStreetViewPanorama.orientationToPoint(orientation);
        if (point != null) {
            mPanoClickTimes++;
            mPanoClickTextView.setText(
                    "Times clicked=" + mPanoClickTimes + " : " + point.toString());
            mStreetViewPanorama.animateTo(
                    new StreetViewPanoramaCamera.Builder()
                            .orientation(orientation)
                            .zoom(mStreetViewPanorama.getPanoramaCamera().zoom)
                            .build(), 1000);
        }

    }

    @Override
    public void onStreetViewPanoramaLongClick(StreetViewPanoramaOrientation orientation) {
        Point point = mStreetViewPanorama.orientationToPoint(orientation);
        if (point != null) {
            mPanoLongClickTimes++;
            mPanoLongClickTextView.setText(
                    "Times long clicked=" + mPanoLongClickTimes + " : " + point.toString());
        }
    }

}
