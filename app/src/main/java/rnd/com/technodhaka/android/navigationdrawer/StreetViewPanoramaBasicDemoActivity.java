package rnd.com.technodhaka.android.navigationdrawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;

public class StreetViewPanoramaBasicDemoActivity extends AppCompatActivity {

    // George St, Sydney
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view_panorama_basic_demo);
        Intent intent = getIntent();
        final double lat = intent.getDoubleExtra(KeyClass.LAT_KEY, 0.0);
        final double log = intent.getDoubleExtra(KeyClass.LONG_KEY, 0.0);

        final LatLng var = new LatLng(lat, log);
        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        // Only set the panorama to SYDNEY on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            if (lat != 0 || log != 0)
                                panorama.setPosition(var);
                            else
                                panorama.setPosition(SYDNEY);
                        }
                    }
                });
    }

}
