import java.util.*;

public class StudentController {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();
        StudentView view = new StudentView();

        int choice;
        do {
            choice = view.showMenu();
            switch (choice) {
                case 1 -> {
                    Student s = view.getStudentDetails();
                    try {
                        dao.addStudent(s);
                        System.out.println("✅ Student added successfully!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    try {
                        List<Student> students = dao.getAllStudents();
                        view.displayStudents(students);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }
}
