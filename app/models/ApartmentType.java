package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by michael on 06.08.17.
 */
@Entity
public class ApartmentType extends Model {
    @Id
    public Long id;

    public String name;

    public static Find<Long, ApartmentType> find = new Find<Long, ApartmentType>(){};

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
