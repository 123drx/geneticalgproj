package daniel.proj.objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class User {

    @Id
    private String username;
    private String Password;
    private String Proffesion;
    private String RealName;

    public User()
    {

    }

    public User(String UserName,String password , String Proffesion , String RealName)
    {
        this.username = UserName;
        this.Password = password;
        this.Proffesion = Proffesion;
        this.RealName = RealName;
    }

    public String getUsername() {
        return username;
    }
   
    public void setUsername(String Username) {
        this.username = Username;
    }
   
    public String getPassword() {
        return Password;
    }
   
    public void setPassword(String password) {
        this.Password = password;
    }
   
    public String getProffesion() {
        return Proffesion;
    }
   
    public void setProffesion(String proffesion) {
        this.Proffesion = proffesion;
    }
   
    public String getRealName() {
        return RealName;
    }
   
    public void setRealName(String realName) {
        RealName = realName;
    }

    
    
}
