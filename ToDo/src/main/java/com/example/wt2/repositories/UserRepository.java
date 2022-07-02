/* Repository um die gespeicherten User zu verwalten*/
package com.example.wt2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.wt2.models.Role;
import com.example.wt2.models.User;

//CrudRepository ermöglicht grundlegende CRUD Methoden
public interface UserRepository extends CrudRepository<User, String>{
	//Extra Methode um User DB nach Rolle zu durchsuchen
	Optional<User> findByRole(Role role);

}
