package br.edu.infnet.domain.repository;

import br.edu.infnet.domain.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
}
