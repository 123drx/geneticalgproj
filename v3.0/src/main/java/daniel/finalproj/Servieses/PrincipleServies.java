package daniel.finalproj.Servieses;

import java.util.List;

import org.springframework.stereotype.Service;

import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Objects.User;
import daniel.finalproj.Repositorys.PrincipleRepository;


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

    public void UpdatePrinciple(Principle s)
    {
        try
        {
            Prepo.save(s);
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.out.println("Error In Updating User");
        }
    }

    
   public List<Principle> getAllScheduals()
   {
      return Prepo.findAll();
   }

    


}
