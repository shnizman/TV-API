package project.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CastMember {

    private Person person;

    public CastMember() {

    }

    public CastMember(Person person) {
        this.person = person;
    }

}
