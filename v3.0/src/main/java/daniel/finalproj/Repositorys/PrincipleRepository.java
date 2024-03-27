package daniel.finalproj.Repositorys;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import daniel.finalproj.Objects.Principle;





@Repository
public interface PrincipleRepository extends MongoRepository<Principle,String> {
    public Principle findByName(String s);

    
    
} 