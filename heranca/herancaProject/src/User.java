import java.time.*;
import java.util.UUID;


public class User extends BaseEntity {
    private String name;
    private String email;
    private String cellphone;
    private String prompt;
    
    public User(UUID id, Instant createdAt, Instant updatedAt, Boolean isActive, String name, String email, String cellphone, String prompt) {
        super(id, createdAt, updatedAt, isActive);
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.prompt = prompt;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCellphone() {
        return cellphone;
    }
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
    public String getPrompt() {
        return prompt;
    }
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
    
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", createdAt=" + createdAt + ", email=" + email + ", updatedAt="
                + updatedAt + ", cellphone=" + cellphone + ", isActive=" + isActive + ", prompt=" + prompt + "]";
    }
  
    

    
   
}



