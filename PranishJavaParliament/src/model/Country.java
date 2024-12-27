package src.model;

public class Country {
    private String countryId;
    private String name;
    private String headOfState;

    public Country(String countryId, String name, String headOfState) {
        this.countryId = countryId;
        this.name = name;
        this.headOfState = headOfState;
    }

    // Getters and Setters
    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }
}
