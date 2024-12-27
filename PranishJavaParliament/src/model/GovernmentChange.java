package src.model;

public class GovernmentChange {
    private int changeId;
    private int stateId;
    private String dateOfChange;
    private String description;

    public GovernmentChange(int changeId, int stateId, String dateOfChange, String description) {
        this.changeId = changeId;
        this.stateId = stateId;
        this.dateOfChange = dateOfChange;
        this.description = description;
    }

    // Getters and Setters
    public int getChangeId() { return changeId; }
    public void setChangeId(int changeId) { this.changeId = changeId; }

    public int getStateId() { return stateId; }
    public void setStateId(int stateId) { this.stateId = stateId; }

    public String getDateOfChange() { return dateOfChange; }
    public void setDateOfChange(String dateOfChange) { this.dateOfChange = dateOfChange; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
