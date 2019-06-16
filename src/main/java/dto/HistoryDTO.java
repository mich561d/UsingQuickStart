package dto;

public class HistoryDTO {

    private long id;
    private String arguments;

    public HistoryDTO(long id, String arguments) {
        this.id = id;
        this.arguments = arguments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

}
