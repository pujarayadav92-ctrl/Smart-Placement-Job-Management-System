public class Interview {

    private int interviewId;
    private int applicationId;
    private String interviewDate;
    private String interviewMode;
    private String status;

    public Interview(int interviewId, int applicationId, String interviewDate,
                     String interviewMode, String status) {

        this.interviewId = interviewId;
        this.applicationId = applicationId;
        this.interviewDate = interviewDate;
        this.interviewMode = interviewMode;
        this.status = status;
    }

    public int getInterviewId() {
        return interviewId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public String getInterviewMode() {
        return interviewMode;
    }

    public String getStatus() {
        return status;
    }
}