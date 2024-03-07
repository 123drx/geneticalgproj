package danielgras.schoolschedule.Objects;
import java.util.ArrayList;
import java.util.List;

public class Class {
    private String className;
    private List<String> subjects = new ArrayList<>();
    

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public List<String> getSubjects() {
        return subjects;
    }
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
    public void addSubject(String Subject)
    {
        this.subjects.add(Subject);
    }
    public void RemoveSubject(int index)
    {
        this.subjects.remove(index);
    }
    public String tostring()
    {
        String s;
        s= "class" +className+"\n";
        s += "Subjects : ";
        for(String sbj : subjects)
        {
            s += sbj;
        }
        return s; 
    }
    
}
