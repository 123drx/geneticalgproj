package daniel.proj.Servieses;import java.util.List;

import org.springframework.stereotype.Service;

import daniel.proj.Repositorys.TeacherRepository;
import daniel.proj.objects.Teacher;

@Service
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
