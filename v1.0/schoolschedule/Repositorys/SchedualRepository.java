package danielgras.schoolschedule.Repositorys;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import danielgras.schoolschedule.Objects.Schedual;



@Repository
public interface SchedualRepository extends MongoRepository<Schedual,ObjectId> {
    public Schedual[] findbyclassname(String s);
    
} 