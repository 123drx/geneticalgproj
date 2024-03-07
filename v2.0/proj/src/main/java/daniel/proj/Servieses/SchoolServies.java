package daniel.proj.Servieses;import java.util.List;

import org.springframework.stereotype.Service;

import daniel.proj.Repositorys.SchoolRepository;
import daniel.proj.objects.School;

@Service
public class SchoolServies {

    private SchoolRepository Srepo;

    public SchoolServies(SchoolRepository r)
    {
        this.Srepo = r;
    }
    
    public void insertSchool(School t)
    {
        Srepo.insert(t);
    }

    public List<School> GetAllTeachers()
    {
        return Srepo.findAll(); 
    }

    public School updateSchool(School s)
    {
        return Srepo.updateSchool(s);
    }

    public School findByName(String Name)
    {
        return Srepo.findByName(Name);
    }
}
