package controllers;

import models.Apartment;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.jetbrains.annotations.NotNull;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ElasticsearchService;

import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

public class ApartmentController extends Controller {

    private static final String APARTMENT_INDEX = "apartment1_index";

    /** This method should be called at the start up.
     * http://localhost:9000/apartment/indexAll
     * Fetches all apartments from database and creates elastic search indexes.
     * @return Result
     */
    public Result indexAll() {
        List<Apartment> apartments = Apartment.find.all();
        for (Apartment apartment : apartments) {
            indexApartment(apartment);
        }
        return ok();
    }

    private void indexApartment(Apartment apartment) {
        String json = Json.toJson(apartment).toString();
        System.out.println("indexing : " + apartment.getId() + " json: " + json);
        ElasticsearchService.get()
                .prepareIndex(APARTMENT_INDEX, "apartment")
                .setId(Long.toString(apartment.getId()))
                .setSource(json, XContentType.JSON)
                .get();
    }

    /**
     * HTTP Parameter names:
     *   name : name of the apartment
     *   facilities : name of the facilities
     *   apartmenttype : type of the apartment
     * Parameters are not mandatory.
     * @return Apartment List in JSON
     * Example requests:
     * http://localhost:9000/apartment/search?name=Finca&facilities=GARDEN&facilities=TV&apartmentType=Finca returns all
     *  apartments of which name containing finca AND facilities containing both garden AND TV, apartment type contains
     *  finca
     *
     * http://localhost:9000/apartment/search fetches all apartments
     *
     *http://localhost:9000/apartment/search?name=Finca fethces all apartments of which name containing finca
     */
    public Result search() {
        BoolQueryBuilder queryBuilder = prepareElasticSearchQueryFromRequest();
        SearchResponse searchResponse = ElasticsearchService.get()
                .prepareSearch(APARTMENT_INDEX)
                .setQuery(queryBuilder)
                .get();
        return ok(Json.toJson(extractSearchResponseToList(searchResponse)));
    }

    @NotNull
    private List<Map<String, Object>> extractSearchResponseToList(SearchResponse searchResponse) {
        SearchHits searchHits = searchResponse.getHits();
        List<Map<String, Object>> list = new LinkedList<>();

        for (SearchHit searchHit : searchHits) {
            list.add(searchHit.getSource());
        }
        return list;
    }

    private BoolQueryBuilder prepareElasticSearchQueryFromRequest() {
        final Set<Map.Entry<String, String[]>> entries = request().queryString().entrySet();
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        for (Map.Entry<String, String[]> entry : entries) {
            boolQueryBuilder.must(getBoolQueryBuilder(entry));
        }
        return boolQueryBuilder;
    }

    @NotNull
    private BoolQueryBuilder getBoolQueryBuilder(Map.Entry<String, String[]> entry) {
        final String key = entry.getKey();
        final String[] entryValue = entry.getValue();
        BoolQueryBuilder innerBoolQueryBuilder = boolQuery();
        if ("name".equalsIgnoreCase(key)) {
            addValuesToMustQueryBuilder(entryValue, innerBoolQueryBuilder, "name");
        } else if ("facilities".equalsIgnoreCase(key)) {
            addValuesToMustQueryBuilder(entryValue, innerBoolQueryBuilder, "facilities.name");
        } else if ("apartmentType".equalsIgnoreCase(key)) {
            addValuesToMustQueryBuilder(entryValue, innerBoolQueryBuilder, "apartmentType.name");
        }
        return innerBoolQueryBuilder;
    }

    private void addValuesToMustQueryBuilder(String[] entryValue, BoolQueryBuilder innerBoolQueryBuilder, String termName) {
        for (String valueString : entryValue) {
            if(StringUtils.isNotBlank(valueString)){
                innerBoolQueryBuilder.must(termQuery(termName, valueString.toLowerCase()));
            }
        }
    }
}