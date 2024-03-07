package daniel.proj.Repositorys;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import daniel.proj.objects.Principle;




@Repository
public interface PrincipleRepository extends MongoRepository<Principle,String> {
    public Principle findByName(String s);

    default Principle updatePrinciple(Principle updatedUser) {
        Principle existingUser = findByName(updatedUser.getName());
        if (existingUser != null) {
            // Update relevant fields
            existingUser.setName(updatedUser.getName());
            existingUser.setSchool(updatedUser.getSchool());

            // Save the updated object
            return save(existingUser);
        }
        return null; // Handle the case when the user doesn't exist
    }
    
} 