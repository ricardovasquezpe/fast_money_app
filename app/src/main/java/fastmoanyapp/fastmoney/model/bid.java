package fastmoanyapp.fastmoney.model;

/**
 * Created by Usuario on 29/05/2017.
 */

public class bid {
    private String id;
    private String IdJob;
    private String IdUser;
    private String Comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdJob() {
        return IdJob;
    }

    public void setIdJob(String idJob) {
        IdJob = idJob;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
