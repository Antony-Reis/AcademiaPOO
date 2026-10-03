
package entities;


public class GymClass {
    private int id;
    private String room;
    private String instructor;
    private String type;
    private int capacity;
    
    public GymClass(int id, String room, String instructor,String type, int capacity){
        this.id = id;
        this.room = room;
        this.instructor = instructor;
        this.type = type;
        this.capacity = capacity;
        
    }

    public int getId() {
        return id;
    }

    public String getRoom() {
        return room;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0){
            System.out.print("Não pode existir capacidade 0");
        }        
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "GymClass{" + "id: " + id + 
                ", room: " + room + 
                ", instructor: " + instructor + 
                ", type: " + type + 
                ", capacity: " + capacity + '}';
    }
    
    
}
