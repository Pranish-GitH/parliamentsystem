package src.model;

public class Parliament {
    private String houseName;
    private String houseType;

    // Constructor
    public Parliament(String houseName, String houseType) {
        this.houseName = houseName;
        this.houseType = houseType;
    }

    // Getters and Setters
    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }
}
