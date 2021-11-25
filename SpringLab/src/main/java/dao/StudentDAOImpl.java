package dao;

import model.Student;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentDAOImpl implements StudentDAO {
    private JdbcTemplate jdbcTemplate;

    public StudentDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student getStudentById(int id) {
        Student student;
        try {
            student = jdbcTemplate.queryForObject("SELECT * FROM Students WHERE studentID=?", new BeanPropertyRowMapper<>(Student.class), new Object[]{id});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        List allStudents = jdbcTemplate.query("SELECT * FROM Students",
                new BeanPropertyRowMapper<>(Student.class));
        return allStudents;
    }
}
