package kodz.org.muhit.Kits;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.huawei.hms.site.api.SearchResultListener;
import com.huawei.hms.site.api.SearchService;
import com.huawei.hms.site.api.SearchServiceFactory;
import com.huawei.hms.site.api.model.Coordinate;
import com.huawei.hms.site.api.model.LocationType;
import com.huawei.hms.site.api.model.NearbySearchRequest;
import com.huawei.hms.site.api.model.NearbySearchResponse;
import com.huawei.hms.site.api.model.SearchStatus;
import com.huawei.hms.site.api.model.Site;

import java.util.List;
import java.util.Locale;

import kodz.org.muhit.Helpers.Utils;
import kodz.org.muhit.R;

public class SiteKit {

    SearchService searchService;
    NearbySearchRequest nearbySearchRequest;
    Coordinate coordinate;
    SearchResultListener<NearbySearchResponse> nearbySearchResponseSearchResultListener;

    MapKit map;
    Context context;

    public void init(Context mcontext, MapKit mmap) {
        context = mcontext;
        map = mmap;
    }


    public void getPoiList(final LocationType locationType, String query, final int icon, Coordinate coordinate, final Integer color) {

        if (map.huaweiMap != null) {
            searchService = SearchServiceFactory.create(context, Utils.getApiKey(context));
            nearbySearchRequest = new NearbySearchRequest();
            nearbySearchRequest.setLocation(coordinate);
            nearbySearchRequest.setRadius(20000);
            nearbySearchRequest.setPoiType(locationType);
            //nearbySearchRequest.setLanguage(Locale.getDefault().getLanguage());
            //nearbySearchRequest.setQuery(query);
            //nearbySearchRequest.setPageIndex(1);
            //request.setPageSize(5);

            nearbySearchResponseSearchResultListener = new SearchResultListener<NearbySearchResponse>() {
                @Override
                public void onSearchResult(NearbySearchResponse results) {
                    List<Site> sites = results.getSites();
                    if (results == null || results.getTotalCount() <= 0 || sites == null || sites.size() <= 0) {
                        Toast.makeText(context, context.getString(R.string.poi_not_found), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (Site site : sites) {
                        map.addMarker(site, icon, locationType, color);
                    }
                }


                @Override
                public void onSearchError(SearchStatus status) {
                    Log.i(Utils.TAG, "Error : " + status.getErrorCode() + " " + status.getErrorMessage());
                }
            };
            searchService.nearbySearch(nearbySearchRequest, nearbySearchResponseSearchResultListener);
        }

    }

}
