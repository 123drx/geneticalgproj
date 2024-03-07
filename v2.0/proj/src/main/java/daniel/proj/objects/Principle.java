package daniel.proj.objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Principles")
public class Principle 
{
    @Id
    private String name;
    private School School;
    public Principle()
    {
        
    }

    public Principle(String Name)
    {
        this.name= Name;
        this.School = null;
    }

    public String getName() {
        return name;
    }
   
    public void setName(String Name) {
        this.name = Name;
    }
    
    public School getSchool() {
        return School;
    }
    
    public void setSchool(School scool) {
        this.School = scool;
    }
    
}
