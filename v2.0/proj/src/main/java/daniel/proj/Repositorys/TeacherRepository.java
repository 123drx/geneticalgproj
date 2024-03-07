package daniel.proj.Repositorys;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import daniel.proj.objects.Teacher;



@Repository
public interface TeacherRepository extends MongoRepository<Teacher,ObjectId>{
    
    public Teacher findByName(String Name);
}

    

