package vttp2023.batch4.paf.assessment.models;

public class Suburb {
    private String id;
    private String suburb;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSuburb() {
        return suburb;
    }
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
    public Suburb() {
    }
    public Suburb(String id, String suburb) {
        this.id = id;
        this.suburb = suburb;
    }

    
}
