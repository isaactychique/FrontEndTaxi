package org.example.frontendtaxi;

import com.dlsc.gmapsfx.GoogleMapView;

/** Supplies the API key before GMapsFX starts loading its embedded web page. */
final class ConfiguredMapView {
    private ConfiguredMapView() {}

    static GoogleMapView create() {
        // GMapsFX resolves its HTML relative to the runtime class. Instantiate
        // the library class directly so that its bundled resources are found.
        return new GoogleMapView(null, null, apiKey(), false);
    }

    static String apiKey() {
        String value = System.getenv("GOOGLE_MAPS_API_KEY");
        return value == null || value.isBlank() ? null : value.trim();
    }
}
