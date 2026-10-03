
package entities;


public class Employee {
    private int id;
    private String username;
    private String password;
    private String role;
    
    public Employee(int id, String username, String password, String role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Employee{" + " id: " + id + ", username: " + username + ", role: " + role + '}';
    }
    
    public boolean authentication(String passwordInformed){
        return this.password.equals(passwordInformed);
    }
    
    public boolean canManegeExpense(){
        return false;
    }
}
