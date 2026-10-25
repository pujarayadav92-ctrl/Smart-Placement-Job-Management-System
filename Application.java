public class Application {

    private int applicationId;
    private int studentId;
    private int jobId;
    private String status;

    public Application(int applicationId, int studentId, int jobId, String status) {
        this.applicationId = applicationId;
        this.studentId = studentId;
        this.jobId = jobId;
        this.status = status;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getJobId() {
        return jobId;
    }

    public String getStatus() {
        return status;
    }
}
