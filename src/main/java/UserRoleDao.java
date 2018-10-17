import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserRoleDao {

    private Connection connection;
    private final String databaseName = "people";
    private final String tableName = "user_roles";
    private final String user = "root";
    private final String password = "admin";

    public UserRoleDao() {
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

    public UserRole getRoleById(Integer id) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "select * from " + tableName + "where id="+id;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String role = resultSet.getString("role");
                Role userRole = Role.valueOf(role);
                return new UserRole(id, userRole);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Integer getRoleIdByName(String roleName) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "select * from " + tableName + "where name="+roleName;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                return id;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void createUserRole(UserRole role) {
        PreparedStatement statement;
        try {
            String query = "insert into " + tableName + " (name) values(?)";
            statement = connection.prepareStatement(query);

            statement.setString(1, role.getRole().name());
            statement.execute();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<UserRole> getAllUserRoles() {
        List<UserRole> userRoles = new LinkedList<UserRole>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String query = "select * from " + tableName;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");


                userRoles.add(new UserRole(id, Role.valueOf(name)));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userRoles;
    }
}
