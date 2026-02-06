package com.primetrade.ai.authtask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.primetrade.ai.authtask.entity.Task;
import com.primetrade.ai.authtask.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findByUser(User currentUser);

}