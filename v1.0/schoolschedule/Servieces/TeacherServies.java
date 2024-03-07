package danielgras.schoolschedule.Servieces;
import java.util.List;

import danielgras.schoolschedule.Objects.Teacher;
import danielgras.schoolschedule.Repositorys.TeacherRepository;


public class TeacherServies {

    private TeacherRepository Trepo;

    public TeacherServies(TeacherRepository r)
    {
        this.Trepo = r;
    }
    
    public void insertTeacher(Teacher t)
    {
        Trepo.insert(t);
    }

    public List<Teacher> GetAllTeachers()
    {
        return Trepo.findAll(); 
    }

    public Teacher findByName(String Name)
    {
        return Trepo.findByName(Name);
    }
}
