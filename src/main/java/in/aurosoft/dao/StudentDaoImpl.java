package in.aurosoft.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import in.aurosoft.model.Student;

public class StudentDaoImpl implements StudentDao{

    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public StudentDaoImpl(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        super();
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }
    @Override
    public void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }

    }

    @Override
    public void disconnect() throws SQLException {
        if(jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    @Override
    public List<Student> ListAllStudents() throws SQLException {
        List<Student> listStudent= new ArrayList<>();
        String sql = "Select * from student";
        connect();
        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String phone = resultSet.getString("phone");

            Student student = new Student(id,name,email,password,phone);
            listStudent.add(student);
        }

        resultSet.close();
        statement.close();
        disconnect();
        return listStudent;
    }

    @Override
    public Student getStudentById(int id) throws SQLException {
        Student student = new Student();
        String sql = "Select * from student where id=?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()) {
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String phone = resultSet.getString("phone");

            student = new Student(id,name,email,password,phone);
        }
        resultSet.close();
        statement.close();
        disconnect();
        return student;
    }

    @Override
    public Boolean insertStudent(Student s1) throws SQLException {
        String sql = "Insert into student (name,email,password,phone) values (?,?,?,?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1,s1.getName());
        statement.setString(2, s1.getEmail());
        statement.setString(3, s1.getPassword());
        statement.setString(4, s1.getPhone());

        boolean b = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return b;
    }

    @Override
    public Boolean updateStudent(Student s1) throws SQLException {
        String sql = "Update student set name=?,email=?,password=?,phone=? where id=?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1,s1.getName());
        statement.setString(2, s1.getEmail());
        statement.setString(3, s1.getPassword());
        statement.setString(4, s1.getPhone());
        statement.setInt(5, s1.getId());

        boolean b = statement.executeUpdate() > 0;
        statement.close();

        disconnect();
        return b;
    }

    @Override
    public Boolean deleteStudent(int id) throws SQLException {
        String sql = "Delete from student where id=?";
        connect();
        PreparedStatement statement = jdbcConnection.prepareStatement(sql);

        statement.setInt(1, id);

        boolean b = statement.executeUpdate() > 0;
        statement.close();

        disconnect();
        return b;
    }
}
