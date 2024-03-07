package daniel.proj.Repositorys;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import daniel.proj.objects.User;



@Repository
public interface UserRepository extends MongoRepository<User, String>
{
    public User findByUsername(String Username);
}