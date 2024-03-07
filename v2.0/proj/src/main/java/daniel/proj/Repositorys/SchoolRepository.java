package daniel.proj.Repositorys;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import daniel.proj.objects.School;




@Repository
public interface SchoolRepository extends MongoRepository<School,ObjectId> {
        public School findByName(String Name);

        default School updateSchool(School updatedSchool) {
        School existingSchool = findByName(updatedSchool.getName());
        if (existingSchool != null) {
            // Update relevant fields
            existingSchool.setName(updatedSchool.getName());
            existingSchool.setTeachers(updatedSchool.getTeachers());
            existingSchool.setClasses(updatedSchool.getClasses());

            // Save the updated object
            return save(existingSchool);
        }
        return null; // Handle the case when the user doesn't exist
    }
} 