package daniel.finalproj.Servieses;

import java.util.List;

import org.springframework.stereotype.Service;

import daniel.finalproj.Objects.User;
import daniel.finalproj.Repositorys.UserRepository;

@Service
public class UserServies 
{
    private UserRepository Urepo;

    public UserServies(UserRepository r)
    {
        this.Urepo = r;
    }
    
    public void UpdateUser(User user)
    {
        try
        {
            Urepo.save(user);
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public void insertUser(User t)
    {
        Urepo.insert(t);
    }

    public List<User> GetAllUsers()
    {
        return Urepo.findAll(); 
    }

    public User findByUsername(String UserName)
    {
        return Urepo.findByUsername(UserName);
    }
}
