package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by michael on 06.08.17.
 */
@Entity
public class Facility extends Model{
    @Id
    private Long id;

    private String name;

    public static Find<Long, Facility> find = new Find<Long, Facility>(){};


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
