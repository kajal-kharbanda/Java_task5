import java.util.*;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private int enrolledStudents;

    public Course(String code, String title, String description, int capacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public String getCode() {
        return code;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents;
    }

    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Course Code: " + code + ", Title: " + title + ", Available Slots: " + getAvailableSlots();
    }
}

class Student {
    private String id;
    private String name;
    private List<Course> registeredCourses;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public boolean registerCourse(Course course) {
        if (registeredCourses.contains(course)) {
            System.out.println("Already registered for course: " + course.getCode());
            return false;
        }
        if (course.enrollStudent()) {
            registeredCourses.add(course);
            return true;
        }
        System.out.println("No available slots for course: " + course.getCode());
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.dropStudent();
            return true;
        }
        System.out.println("Not registered for course: " + course.getCode());
        return false;
    }
}

public class CourseStudentSystem {
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        CourseStudentSystem system = new CourseStudentSystem();
        Scanner scanner = new Scanner(System.in);

        system.courses.add(new Course("CS101", "Introduction to Programming", "Learn basic programming concepts.", 3, "Mon-Wed 10:00-11:00"));
        system.courses.add(new Course("CS102", "Data Structures", "Study common data structures.", 2, "Tue-Thu 11:00-12:00"));

        while (true) {
            System.out.println("\n--- Course & Student Management System ---");
            System.out.println("1. List Available Courses");
            System.out.println("2. Register Student for a Course");
            System.out.println("3. Drop Student from a Course");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    system.listCourses();
                    break;
                case 2:
                    system.registerStudent(scanner);
                    break;
                case 3:
                    system.dropCourse(scanner);
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void listCourses() {
        System.out.println("\n--- Available Courses ---");
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    private void registerStudent(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        System.out.print("Enter Student Name: ");
        String studentName = scanner.nextLine();

        Student student = findOrCreateStudent(studentId, studentName);

        System.out.print("Enter Course Code to Register: ");
        String courseCode = scanner.nextLine();
        Course course = findCourse(courseCode);

        if (course != null) {
            if (student.registerCourse(course)) {
                System.out.println("Successfully registered for the course.");
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    private void dropCourse(Scanner scanner) {
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();
        Student student = findStudent(studentId);

        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("Enter Course Code to Drop: ");
        String courseCode = scanner.nextLine();
        Course course = findCourse(courseCode);

        if (course != null) {
            if (student.dropCourse(course)) {
                System.out.println("Successfully dropped the course.");
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    private Student findOrCreateStudent(String id, String name) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        Student newStudent = new Student(id, name);
        students.add(newStudent);
        return newStudent;
    }

    private Student findStudent(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    private Course findCourse(String code) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }
}
