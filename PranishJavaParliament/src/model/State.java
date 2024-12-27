package src.model;

public class State {
    private int stateId;       // Unique ID for the state
    private String name;       // Name of the state
    private String capital;    // Capital of the state
    private String headOfState; // Head of State for the state
    private long population;   // Population of the state
    private double area;       // Area of the state in square kilometers
    private String languages;  // Languages spoken in the state

    // Constructor to initialize all fields
    public State(int stateId, String name, String capital, String headOfState, long population, double area, String languages) {
        this.stateId = stateId;
        this.name = name;
        this.capital = capital;
        this.headOfState = headOfState;
        this.population = population;
        this.area = area;
        this.languages = languages;
    }

    // Getters and Setters for each field
    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getHeadOfState() {
        return headOfState;
    }

    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    // Override toString method to display State in a readable format
    @Override
    public String toString() {
        return "State ID: " + stateId + "\n" +
               "Name: " + name + "\n" +
               "Capital: " + capital + "\n" +
               "Head of State: " + headOfState + "\n" +
               "Population: " + population + "\n" +
               "Area: " + area + " sq km\n" +
               "Languages: " + languages;
    }
}
