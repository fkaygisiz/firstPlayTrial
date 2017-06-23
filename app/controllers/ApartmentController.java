package controllers;

import dto.Apartment;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.ElasticsearchService;

public class ApartmentController extends Controller {

    public Result getApartment() {
        Apartment apartment = new Apartment();
        apartment.setId("12345");
        apartment.setName("Beautiful Apartment");

        ClusterHealthResponse response = ElasticsearchService.get().admin().cluster().prepareHealth().get();
        apartment.setStatus(response.getStatus().name());

        return ok(Json.toJson(apartment));
    }
}
