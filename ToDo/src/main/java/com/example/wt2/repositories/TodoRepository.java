package com.example.wt2.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.wt2.models.TodoItems;
import com.example.wt2.models.User;


//P&SRepos stellt als Erweiterung CRUD Funktionalität zur Verfügung & darüberhinaus noch Methoden zum pagen
@Repository
public interface TodoRepository extends PagingAndSortingRepository<TodoItems, Long>{
	Page<TodoItems> findAllByOwner (Pageable pageable, User owner);
}
