
package entities;


public class Administrator extends Employee {
    
    public Administrator(int id, String username, String password) {
        super(id, username, password, "Adiministrador");
    }

    @Override
    public boolean canManegeExpense() {
        return true; 
    }

    @Override
    public String toString() {
        return "Administrator{" + super.toString() + '}';
    }
    
    


}
