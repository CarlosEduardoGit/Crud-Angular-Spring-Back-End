package com.carlos.service;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import com.carlos.model.Course;
import com.carlos.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {

	private final CourseRepository courseRepository;
	
	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}
	
	public List<Course> list() {
		return courseRepository.findAll();
	}
	
	public Optional<Course> findById(@PathVariable @NotNull @Positive Long id) {
		return courseRepository.findById(id);
	}
	
	public Course create(@Valid Course course) {
		return courseRepository.save(course);
	}
	
	public Optional<Course> update(@NotNull @Positive Long id, @RequestBody @Valid Course course) {
		return courseRepository.findById(id)
				.map(recordFound -> {
					recordFound.setName(course.getName());
					recordFound.setName(course.getCategory());
					return courseRepository.save(recordFound);
					
				});	
	}
	
	public boolean delete(@PathVariable Long id) {
		return courseRepository.findById(id)
				.map(recordFound -> {
					courseRepository.deleteById(id);
					return true;
				})
				.orElse(false);
		
	}
}
