public class Job {

    private int jobId;
    private String jobTitle;
    private String salary;
    private String location;
    private int companyId;

    public Job(int jobId, String jobTitle, String salary, String location, int companyId) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.location = location;
        this.companyId = companyId;
    }

    public int getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getSalary() {
        return salary;
    }

    public String getLocation() {
        return location;
    }

    public int getCompanyId() {
        return companyId;
    }
}