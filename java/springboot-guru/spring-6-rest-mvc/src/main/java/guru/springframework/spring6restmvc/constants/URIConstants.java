package guru.springframework.spring6restmvc.constants;

public class URIConstants {
    // Beer constants
    // Versions
    public static final String BEER_PATH_V1 = "/api/v1/beer";

    // Beer Id
    public static final String BEER_ID_PATH = "/{beerId}";

   // Endpoints
    public static final String BEER_BY_ID_PATH = BEER_PATH_V1 + BEER_ID_PATH;
    public static final String BEER_LIST_PATH = BEER_PATH_V1;
    public static final String BEER_CREATE_PATH = BEER_PATH_V1;
    public static final String BEER_UPDATE_PATH = BEER_PATH_V1 + BEER_ID_PATH;
    public static final String BEER_DELETE_PATH = BEER_PATH_V1 + BEER_ID_PATH;
    public static final String BEER_PATCH_PATH = BEER_PATH_V1 + BEER_ID_PATH;

    private URIConstants() {
    }
}
