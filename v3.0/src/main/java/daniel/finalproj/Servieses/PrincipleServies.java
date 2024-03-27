package daniel.finalproj.Servieses;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import daniel.finalproj.Objects.Principle;
import daniel.finalproj.Objects.User;
import daniel.finalproj.Repositorys.PrincipleRepository;


@Service
public class PrincipleServies 
{
   
    private PrincipleRepository Prepo;

    private ArrayList<PrincipleChangeListener> listeners;

    public interface PrincipleChangeListener {
        public void onChange();
    }
    
    public PrincipleServies(PrincipleRepository Screpo)
    {
        this.Prepo = Screpo;
        listeners = new ArrayList<PrincipleChangeListener>();
    }



    public void InsertPrincple(Principle s )
    {
        synchronized(listeners){
            for(PrincipleChangeListener listener : listeners)
            {
                listener.onChange();
            }
        }
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

   public void AddPrincipleChangeListener(PrincipleChangeListener listener)
   {
    synchronized(listeners){
        listeners.add(listener);
    }
   }

    


}
