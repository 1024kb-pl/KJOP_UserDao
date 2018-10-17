import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDao {

    private Connection connection;
    private final String databaseName = "people";
    private final String tableName = "employees";
    private final String user = "root";
    private final String password = "admin";
    private UserRoleDao userRoleDao;

    public UserDao() {
        userRoleDao = new UserRoleDao();
        init();
    }

    private void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/"+databaseName+"?useSSL=false", user, password);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<User>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "select * from " + tableName;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                Integer age = resultSet.getInt("age");
                Integer userRoleId = resultSet.getInt("role_id");
                UserRole userRole = userRoleDao.getRoleById(userRoleId);


                User user = new User(id, name, lastname, age, userRole);
                users.add(user);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void createUser(User user) {
        PreparedStatement statement;
        try {
            Integer roleId = userRoleDao.getRoleIdByName(user.getUserRole().getRole().name());
            String query = "insert into " + tableName + " (name, lastname, age, role_id) values(?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, user.getName());
            statement.setString(2, user.getLastname());
            statement.setInt(3, user.getAge());
            statement.setInt(4, roleId);

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String lastname) {
        PreparedStatement statement;
        try {
            String query = "delete from " + tableName + " where lastname=?";
            statement = connection.prepareStatement(query);

            statement.setString(1, lastname);

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        PreparedStatement statement;
        try {
            String query = "update " + tableName + " set name = ?, lastname = ?, age = ?, role_id = ? where id=?";
            statement = connection.prepareStatement(query);

            statement.setString(1, user.getName());
            statement.setString(2, user.getLastname());
            statement.setInt(3, user.getAge());
            statement.setInt(4, user.getUserRole().getId());
            statement.setInt(5, user.getId());

            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
