import java.sql.*;
import java.util.*;

public class StudentDAO {
    private final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private final String USER = "root";
    private final String PASSWORD = "your_password";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addStudent(Student s) throws SQLException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO student VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, s.getId());
            ps.setString(2, s.getName());
            ps.setInt(3, s.getAge());
            ps.setDouble(4, s.getMarks());
            ps.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM student")) {
            while (rs.next()) {
                list.add(new Student(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)));
            }
        }
        return list;
    }
}
