package danielgras.schoolschedule.Servieces;

import java.util.List;

import danielgras.schoolschedule.Objects.User;
import danielgras.schoolschedule.Repositorys.UserRepository;



public class UserServies {
      private UserRepository Trepo;

    public UserServies(UserRepository r)
    {
        this.Trepo = r;
    }
    
    public void insertUser(User t)
    {
        Trepo.insert(t);
    }

    public List<User> GetAllUsers()
    {
        return Trepo.findAll(); 
    }
}
