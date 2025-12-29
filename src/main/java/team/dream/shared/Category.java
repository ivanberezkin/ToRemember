package team.dream.shared;
import java.io.Serializable;

public enum Category implements Serializable {
    HOBBY("Hobby"),
    SPORT("Sport"),
    FOOD("Food"),
    WORK("Work"),
    FAMILY( "Family");

    private String description;

    Category(String description){
        this.description = description;
    }

    @Override
    public String toString(){
        return description;
    }

}


