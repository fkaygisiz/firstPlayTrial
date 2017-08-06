package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.SampleModel;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import services.ElasticsearchService;

import java.util.*;

public class SampleController extends Controller {

    public Result getById(Long id) {
        SampleModel sampleModel = SampleModel.find.byId(id);
        return ok(Json.toJson(sampleModel));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result post() {
        JsonNode jsonNode = request().body().asJson();
        SampleModel sampleModel = Json.fromJson(jsonNode, SampleModel.class);
        sampleModel.save();
        return ok(Json.toJson(sampleModel));
    }

    public Result index(Long id) {
        SampleModel sampleModel = SampleModel.find.byId(id);
        String json = Json.toJson(sampleModel).toString();

        IndexResponse indexResponse = ElasticsearchService.get()
                .prepareIndex("sample_index", "sample")
                .setId(Long.toString(id))
                .setSource(json, XContentType.JSON)
                .get();

        return ok(indexResponse.toString());
    }

    public Result search() {
        SearchResponse searchResponse = ElasticsearchService.get()
                .prepareSearch("sample_index")
                .get();

        SearchHits searchHits = searchResponse.getHits();
        List<Map<String, Object>> list = new LinkedList<>();

        for (SearchHit searchHit : searchHits) {
            list.add(searchHit.getSource());
        }

        return ok(Json.toJson(list));
    }
}
