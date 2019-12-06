package com.crud.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.demo.entity.User;
import com.crud.demo.exception.RecordNotFoundException;
import com.crud.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	public List<User> getAllUsers() {
		List<User> userList = userRepo.findAll();

		if (userList.size() > 0) {
			return userList;
		} else {
			return new ArrayList<User>();
		}
	}

	public User getuserById(Long id) throws RecordNotFoundException {
		Optional<User> user = userRepo.findById(id);

		if (user.isPresent()) {
			return user.get();
		} else {
			throw new RecordNotFoundException("No user record exist for given id");
		}
	}

	public User createOrUpdateUser(User entity) throws RecordNotFoundException {
		Optional<User> user = userRepo.findById(entity.getId());

		if (user.isPresent()) {
			User newEntity = user.get();
			newEntity.setEmail(entity.getEmail());
			newEntity.setFirstName(entity.getFirstName());
			newEntity.setLastName(entity.getLastName());

			newEntity = userRepo.save(newEntity);

			return newEntity;
		} else {

			entity = userRepo.save(entity);

			return entity;
		}
	}

	public void deleteUserById(Long id) throws RecordNotFoundException {
		Optional<User> user = userRepo.findById(id);

		if (user.isPresent()) {
			userRepo.deleteById(id);
		} else {
			throw new RecordNotFoundException("No user record exist for given id");
		}
	}

}
