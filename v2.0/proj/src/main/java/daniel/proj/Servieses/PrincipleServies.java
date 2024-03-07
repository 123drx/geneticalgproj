package daniel.proj.Servieses;

import java.util.List;

import org.springframework.stereotype.Service;

import daniel.proj.Repositorys.PrincipleRepository;
import daniel.proj.objects.Principle;


@Service
public class PrincipleServies 
{
    private PrincipleRepository Prepo;
    
    public PrincipleServies(PrincipleRepository Screpo)
    {
        this.Prepo = Screpo;
    }

    public void InsertPrincple(Principle s )
    {
        Prepo.insert(s);
    }

    public Principle findbyname(String s)
    {
        return Prepo.findByName(s);
    }

    public Principle UpdatePrinciple(Principle s)
    {
        return Prepo.updatePrinciple(s);
    }

    
   public List<Principle> getAllScheduals()
   {
      return Prepo.findAll();
   }

    


}
