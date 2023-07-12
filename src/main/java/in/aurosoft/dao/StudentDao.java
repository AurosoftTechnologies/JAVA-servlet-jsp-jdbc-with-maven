package in.aurosoft.dao;

import java.sql.SQLException;
import java.util.List;

import in.aurosoft.model.Student;

public interface StudentDao {

    public void connect() throws SQLException;
    public void disconnect() throws SQLException;

    public List<Student> ListAllStudents() throws SQLException;
    public Student getStudentById(int id) throws SQLException;

    public Boolean insertStudent(Student s1) throws SQLException;
    public Boolean updateStudent(Student s1) throws SQLException;
    public Boolean deleteStudent(int id) throws SQLException;
}
