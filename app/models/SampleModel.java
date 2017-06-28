package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SampleModel extends Model {

    @Id
    public Long id;

    public String name;

    public static Find<Long, SampleModel> find = new Find<Long, SampleModel>(){};

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
