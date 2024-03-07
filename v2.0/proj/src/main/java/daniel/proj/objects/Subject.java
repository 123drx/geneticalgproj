package daniel.proj.objects;

import java.util.HashSet;
import java.util.Set;

public class Subject {
    private String SubjectName;
    private String TeacherName;
    private Set<String> classes = new HashSet<>();
    private int WeaklyHours;
    private int Priority;
    


    public void addClass(String ClassName)
    {
        this.classes.add(ClassName);
    }

    public Subject(String SubjectName , String TeachersName , int WeeklyHours)
    {
        this.SubjectName = SubjectName;
        this.TeacherName = TeachersName; 
        this.WeaklyHours = WeeklyHours;
        this.Priority = 3;
    }

    public Subject(String SubjectName , String TeachersName , int WeeklyHours , int priority)
    {
        this.SubjectName = SubjectName;
        this.TeacherName = TeachersName; 
        this.WeaklyHours = WeeklyHours;
        this.Priority = priority;
    }

    public Subject()
    {

    }

    public Subject(String SubjectName,String TeacherName ,Set<String> Classes)
    {
        this.setSubjectName(SubjectName);
        this.setClasses(Classes);
        this.setTeacherName(TeacherName); 
    }

    public Subject(String SubjectName,Set<String> Classes)
    {
        this.setSubjectName(SubjectName);
        this.setClasses(Classes);
        this.Priority = 3;
    }
    
    public Subject(Subject otherSubject) {
        this.SubjectName = otherSubject.getSubjectName();
        this.TeacherName = otherSubject.getTeacherName();
        this.WeaklyHours = otherSubject.getWeaklyHours();
        this.classes = otherSubject.getClasses();
        this.Priority = otherSubject.getPriority();

    }


    public Subject(String Name)
    {
        this.SubjectName = Name;
        this.Priority = 3;
    }
    
    public String getSubjectName() {
        return SubjectName;
    }
    
    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }
    
    public String getTeacherName() {
        return TeacherName;
    }
    
    public void setTeacherName(String teacherName) {
        TeacherName = teacherName;
    }
    
    public int getWeaklyHours() {
        return WeaklyHours;
    }
    
    public void setWeaklyHours(int weaklyHours) {
        WeaklyHours = weaklyHours;
    }

    public Set<String> getClasses() {
        return classes;
    }

    public void setClasses(Set<String> classes) {
        this.classes = classes;
    }

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }
    



}
