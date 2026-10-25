public class Student {

    private int studentId;
    private String name;
    private String email;
    private String phone;
    private String course;

    public Student(int studentId, String name, String email, String phone, String course) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.course = course;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCourse() {
        return course;
    }
}
