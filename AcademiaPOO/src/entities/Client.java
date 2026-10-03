
package entities;


public class Client {
    private Integer id;
    private String name;
    private String address;
    private String fone;
    private String email;
    private String cpf;
    private boolean situation;
    

    public Client(Integer id, String name, String address, String fone,
            String email, String cpf, boolean situation) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fone = fone;
        this.email = email;
        this.cpf = cpf;
        this.situation = situation;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getFone() {
        return fone;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean getSituation() {
        return situation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSituation(boolean situation) {
        this.situation = situation;
    }

    @Override
    public String toString() {
        return "Client{" + "id = " + id + 
                ", name = " + name + 
                ", address = " + address + 
                ", fone = " + fone + 
                ", email = " + email + 
                ", cpf = " + cpf + 
                ", situation = " + situation + '}';
    }


    
    
    
    
    
}
