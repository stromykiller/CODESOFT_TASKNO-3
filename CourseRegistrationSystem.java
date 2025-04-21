import java.util.*;

class Course {
    String courseCode;
    String title;
    String description;
    int capacity;
    int enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.enrolledStudents = 0;
    }

    
    public boolean hasAvailableSlots() {
        return enrolledStudents < capacity;
    }


    public boolean enrollStudent() {
        if (hasAvailableSlots()) {
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
        return courseCode + ": " + title + " - " + description + " | Capacity: " + capacity + " | Enrolled: " + enrolledStudents;
    }
}

class Student {
    String studentID;
    String name;
    Set<String> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new HashSet<>();
    }

  
    public boolean registerCourse(Course course) {
        if (course.enrollStudent()) {
            registeredCourses.add(course.courseCode);
            return true;
        }
        return false;
    }

    
    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course.courseCode) && course.dropStudent()) {
            registeredCourses.remove(course.courseCode);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return studentID + ": " + name + " | Registered Courses: " + registeredCourses;
    }
}

public class CourseRegistrationSystem {
    private static Map<String, Course> courseDatabase = new HashMap<>();
    private static Map<String, Student> studentDatabase = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        initializeCourses();

        Student student = new Student("S101", "John Doe");

       
        studentDatabase.put(student.studentID, student);

        while (true) {
            System.out.println("\nCourse Registration System");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Drop a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); 

            switch (option) {
                case 1:
                    viewAvailableCourses();
                    break;
                case 2:
                    registerForCourse(scanner, student);
                    break;
                case 3:
                    dropCourse(scanner, student);
                    break;
                case 4:
                    viewRegisteredCourses(student);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void initializeCourses() {
        courseDatabase.put("CSE101", new Course("CSE101", "Introduction to Programming", "Learn the basics of programming in Java.", 30));
        courseDatabase.put("CSE102", new Course("CSE102", "Data Structures", "Learn about different data structures.", 25));
        courseDatabase.put("CSE103", new Course("CSE103", "Database Management", "Learn the fundamentals of databases.", 20));
    }

    private static void viewAvailableCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courseDatabase.values()) {
            System.out.println(course);
        }
    }

    private static void registerForCourse(Scanner scanner, Student student) {
        System.out.print("Enter the course code to register: ");
        String courseCode = scanner.nextLine().toUpperCase();

        Course course = courseDatabase.get(courseCode);
        if (course != null) {
            if (student.registerCourse(course)) {
                System.out.println("Successfully registered for " + course.title);
            } else {
                System.out.println("Course registration failed. Either the course is full or there's an error.");
            }
        } else {
            System.out.println("Invalid course code.");
        }
    }

    private static void dropCourse(Scanner scanner, Student student) {
        System.out.print("Enter the course code to drop: ");
        String courseCode = scanner.nextLine().toUpperCase();

        Course course = courseDatabase.get(courseCode);
        if (course != null) {
            if (student.dropCourse(course)) {
                System.out.println("Successfully dropped the course " + course.title);
            } else {
                System.out.println("You are not registered for this course.");
            }
        } else {
            System.out.println("Invalid course code.");
        }
    }

    private static void viewRegisteredCourses(Student student) {
        System.out.println("\nRegistered Courses for " + student.name + ":");
        for (String courseCode : student.registeredCourses) {
            Course course = courseDatabase.get(courseCode);
            if (course != null) {
                System.out.println(course);
            }
        }
    }
}
