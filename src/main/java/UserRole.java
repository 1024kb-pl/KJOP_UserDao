public class UserRole {
    private Integer id;
    private Role role;

    public UserRole(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    public UserRole(Role role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}
