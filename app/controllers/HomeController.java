package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;
import java.util.Map;

public class HomeController extends Controller {

    public Result index() {
        Map<String, String> welcome = new HashMap<>();

        welcome.put("title", "Holidu Search Challenge");
        welcome.put("version", "1");

        return ok(Json.toJson(welcome));
    }
}
