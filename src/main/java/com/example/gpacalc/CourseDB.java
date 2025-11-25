package com.example.gpacalc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CourseDB {

        private Connection connection;
        private final Logger logger = Logger.getLogger(this.getClass().getName());

        public CourseDB() {
            openConnection();
            createTable();
        }

        private void openConnection() {
            try {
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection("jdbc:sqlite:courses.db");
                    logger.info("Connected To Database");
                }
            } catch (SQLException e) {
                logger.warning(e.toString());
            }
        }

        private void createTable() {
            String query = """
            CREATE TABLE IF NOT EXISTS course (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                credit REAL NOT NULL,
                grade TEXT NOT NULL,
                code TEXT,
                teacher1 TEXT,
                teacher2 TEXT
            )
        """;

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.execute();
                logger.info("Course Table Created");
            } catch (SQLException e) {
                logger.warning(e.toString());
            }
        }

        public void insertCourse(CourseDetailsWithID c) {
            String query = "INSERT INTO course(name, credit, grade, code, teacher1, teacher2) VALUES(?,?,?,?,?,?)";

            try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, c.getName());
                ps.setDouble(2, c.getCredit());
                ps.setString(3, c.getGrade());
                ps.setString(4, c.getCode());
                ps.setString(5, c.getTeacher1());
                ps.setString(6, c.getTeacher2());

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    c.setId(rs.getInt(1));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void updateCourse(int id, CourseDetails c) {
            String query = "UPDATE course SET name=?, credit=?, grade=?, code=?, teacher1=?, teacher2=? WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, c.getName());
                ps.setDouble(2, c.getCredit());
                ps.setString(3, c.getGrade());
                ps.setString(4, c.getCode());
                ps.setString(5, c.getTeacher1());
                ps.setString(6, c.getTeacher2());
                ps.setInt(7, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                logger.warning(e.toString());
            }
        }

        public void deleteCourse(int id) {
            String query = "DELETE FROM course WHERE id=?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                logger.warning(e.toString());
            }
        }

        public List<CourseDetailsWithID> getAllCourses() {
            List<CourseDetailsWithID> list = new ArrayList<>();
            String query = "SELECT * FROM course";

            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(new CourseDetailsWithID(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("credit"),
                            rs.getString("grade"),
                            rs.getString("code"),
                            rs.getString("teacher1"),
                            rs.getString("teacher2")
                    ));
                }

            } catch (SQLException e) {
                logger.warning(e.toString());
            }
            return list;
        }

    }