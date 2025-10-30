import java.util.*;

public class StudentView {
    Scanner sc = new Scanner(System.in);

    public int showMenu() {
        System.out.println("\n=== Student Management System ===");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
        return sc.nextInt();
    }

    public Student getStudentDetails() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        return new Student(id, name, age, marks);
    }

    public void displayStudents(List<Student> students) {
        System.out.println("\n--- Student Records ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
