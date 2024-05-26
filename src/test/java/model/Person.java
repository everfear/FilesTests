package model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Person {
    private String lastName;
    private String firstName;
    private List<String> personOid;
    private Organization organization;

    public String getlastName() {
        return lastName;
    }

    public String getfirstName() {
        return firstName;
    }

    public List<String> getpersonOid() {
        return personOid;
    }

    public Organization getOrganization() {
        return organization;
    }

    public static class Organization {
        @JsonProperty("Legal")
        private Boolean Legal;
        @JsonProperty("Business")
        private Boolean Business;
        @JsonProperty("Branch")
        private Boolean Branch;

        public Boolean getLegal() {
            return Legal;
        }

        public Boolean getBusiness() {
            return Business;
        }

        public Boolean getBranch() {
            return Branch;
        }
    }
}