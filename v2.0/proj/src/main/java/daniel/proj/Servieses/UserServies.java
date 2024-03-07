package daniel.proj.Servieses;

import java.util.List;

import org.springframework.stereotype.Service;

import daniel.proj.Repositorys.UserRepository;
import daniel.proj.objects.User;

@Service
public class UserServies 
{
    private UserRepository Urepo;

    public UserServies(UserRepository r)
    {
        this.Urepo = r;
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
