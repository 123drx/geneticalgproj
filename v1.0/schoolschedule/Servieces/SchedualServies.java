package danielgras.schoolschedule.Servieces;

import java.util.List;

import org.springframework.stereotype.Service;

import danielgras.schoolschedule.Objects.Schedual;
import danielgras.schoolschedule.Repositorys.SchedualRepository;


@Service
public class SchedualServies {
    private SchedualRepository Srepo;
    
    public SchedualServies(SchedualRepository Screpo)
    {
        this.Srepo = Screpo;
    }

    public void InsertSchedual(Schedual s )
    {
        Srepo.insert(s);
    }

    public Schedual[] findbyclassname(String s)
    {
        return Srepo.findbyclassname(s);
    }

    
   public List<Schedual> getAllScheduals()
   {
      return Srepo.findAll();
   }

    


}
