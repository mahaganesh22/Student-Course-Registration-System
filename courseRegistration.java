import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolled;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolled = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getEnrolled() {
        return enrolled;
    }

    public boolean enrollStudent() {
        if (enrolled < capacity) {
            enrolled++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolled > 0) {
            enrolled--;
            return true;
        }
        return false;
    }
}

class Student {
    private String studentId;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerCourse(Course course) {
        registeredCourses.add(course);
    }

    public void dropCourse(Course course) {
        registeredCourses.remove(course);
    }
}

class CourseDatabase {
    private Map<String, Course> courses;

    public CourseDatabase() {
        courses = new HashMap<>();
    }

    public void addCourse(Course course) {
        courses.put(course.getCourseCode(), course);
    }

    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }

    public void displayCourses() {
        for (Course course : courses.values()) {
            System.out.println("Course Code: " + course.getCourseCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Capacity: " + course.getCapacity());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println("Enrolled: " + course.getEnrolled());
            System.out.println("Available Slots: " + (course.getCapacity() - course.getEnrolled()));
            System.out.println();
        }
    }
}

class StudentDatabase {
    private Map<String, Student> students;

    public StudentDatabase() {
        students = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.put(student.getStudentId(), student);
    }

    public Student getStudent(String studentId) {
        return students.get(studentId);
    }
}

public class courseRegistration {
    public static void main(String[] args) {
        CourseDatabase courseDatabase = new CourseDatabase();
        StudentDatabase studentDatabase = new StudentDatabase();

        // Sample courses
        courseDatabase.addCourse(new Course("CS101", "Introduction to Computer Science", "Basics of CS", 30, "Mon-Wed 9:00-10:30"));
        courseDatabase.addCourse(new Course("MATH101", "Calculus I", "Introduction to Calculus", 40, "Tue-Thu 11:00-12:30"));

        // Sample students
        studentDatabase.addStudent(new Student("S001", "Alice"));
        studentDatabase.addStudent(new Student("S002", "Bob"));

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Menu:");
            System.out.println("1. Display Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    courseDatabase.displayCourses();
                    break;
                case 2:
                    System.out.print("Enter your student ID: ");
                    String studentId = scanner.nextLine();
                    Student student = studentDatabase.getStudent(studentId);

                    if (student == null) {
                        System.out.println("Student not found.");
                        break;
                    }

                    System.out.print("Enter the course code to register: ");
                    String courseCode = scanner.nextLine();
                    Course course = courseDatabase.getCourse(courseCode);

                    if (course == null) {
                        System.out.println("Course not found.");
                    } else if (course.enrollStudent()) {
                        student.registerCourse(course);
                        System.out.println("Successfully registered for the course.");
                    } else {
                        System.out.println("Course is full.");
                    }
                    break;
                case 3:
                    System.out.print("Enter your student ID: ");
                    studentId = scanner.nextLine();
                    student = studentDatabase.getStudent(studentId);

                    if (student == null) {
                        System.out.println("Student not found.");
                        break;
                    }

                    System.out.print("Enter the course code to drop: ");
                    courseCode = scanner.nextLine();
                    course = courseDatabase.getCourse(courseCode);

                    if (course == null) {
                        System.out.println("Course not found.");
                    } else if (student.getRegisteredCourses().contains(course)) {
                        course.dropStudent();
                        student.dropCourse(course);
                        System.out.println("Successfully dropped the course.");
                    } else {
                        System.out.println("You are not registered for this course.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }
}
