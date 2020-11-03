package org.springframework.samples.petclinic.service;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.ClinicServiceImpl;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class ClinicServiceImplTest {
	@Mock Pet pet;
	@Mock Vet vet;
	@Mock Owner owner;
	@Mock Visit visit;
	@Mock PetRepository petRepository;
	@Mock VetRepository vetRepository;

	ArrayList<Pet> pets = new ArrayList<Pet>(1);
	ArrayList<Vet> vets = new ArrayList<Vet>(1);

	@InjectMocks
	ClinicServiceImpl clinicService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void visitOwnerPetsTestValidVisitAllVisitedFirst() {
		//	this test will go inside both ifs so the visitPetIfPossible function in the first if will be running
		Date visitDate = new Date(100, 01, 01);	//2000-02-01
		Date birthDate = new Date(100, 01, 01);	//2000-02-01

		pets.add(pet);
		vets.add(vet);

		Mockito.when(pet.getBirthDate()).thenReturn(birthDate);
		Mockito.when(visit.getDate()).thenReturn(visitDate);
		Mockito.when(pets.get(0).getLastVisit()).thenReturn(Optional.of(visit));
		Mockito.when(petRepository.findByOwner(owner)).thenReturn(pets);
		Mockito.when(vetRepository.findAll()).thenReturn(vets);

		try {
			clinicService.visitOwnerPets(owner);
		}catch (ClinicServiceImpl.VisitException e)  {
			// ------------------------	1	//	visitOwnerPets was running
			Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
			Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
			// ------------------------	2 - 3	//inside for loop
			Mockito.verify(pet, Mockito.times(1)).getLastVisit();
			// ------------------------	4 - 5	//	inside first if
			Mockito.verify(pet, Mockito.times(1)).getBirthDate();
			Mockito.verify(visit, Mockito.times(1)).getDate();
			// if visitPetIfPossible was running, pet.getType() would've been invoked
			Mockito.verify(pet, Mockito.times(1)).getType();

			return;
		}
		// ------------------------	5 - 7 - 8 - 9 - 11	//	visitPetIfPossible function was not running - didn't enter second if
		fail("visitOwnerPetsTestValidVisitAllVisited added test but it should't have");
	}

	@Test
	public void visitOwnerPetsTestValidVisitAllVisitedSecond() {
		//	this test will go inside both ifs so the visitPetIfPossible function in the first if will be running
		Date visitDate = new Date(120, 3, 30);	//2020-10-30
		Date birthDate = new Date(120, 9, 30);	//2020-10-30
//		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//		String date = DATE_FORMAT.format(visitDate);
//		System.out.println(date);

		pets.add(pet);
		vets.add(vet);

		Mockito.when(pet.getBirthDate()).thenReturn(birthDate);
		Mockito.when(visit.getDate()).thenReturn(visitDate);
		Mockito.when(pets.get(0).getLastVisit()).thenReturn(Optional.of(visit));
		Mockito.when(petRepository.findByOwner(owner)).thenReturn(pets);
		Mockito.when(vetRepository.findAll()).thenReturn(vets);

		try {
			clinicService.visitOwnerPets(owner);
		}catch (ClinicServiceImpl.VisitException e)  {
			// ------------------------	1	//	visitOwnerPets was running
			Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
			Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
			// ------------------------	2 - 3	//inside for loop
			Mockito.verify(pet, Mockito.times(1)).getLastVisit();
			// ------------------------	4 - 5	//	inside first if
			Mockito.verify(pet, Mockito.times(1)).getBirthDate();
			Mockito.verify(visit, Mockito.times(1)).getDate();
			// if visitPetIfPossible was running, pet.getType() would've been invoked
			Mockito.verify(pet, Mockito.times(1)).getType();

			return;
		}
		// ------------------------	5 - 7 - 8 - 9 - 11	//	visitPetIfPossible function was not running - didn't enter second if
		fail("visitOwnerPetsTestValidVisitAllVisited added test but it should't have");
	}

	@Test
	public void visitOwnerPetsTestFirstBadDates() {
		//	this test won't go in second if and the visitPetIfPossible will not be running at all
		Date visitDate = new Date(120, 9, 30);	//2020-10-30
		Date birthDate = new Date(100, 01, 01);	//2000-02-01
		pets.add(pet);
		vets.add(vet);

		Mockito.when(pet.getBirthDate()).thenReturn(birthDate);
		Mockito.when(visit.getDate()).thenReturn(visitDate);
		Mockito.when(pets.get(0).getLastVisit()).thenReturn(Optional.of(visit));
		Mockito.when(petRepository.findByOwner(owner)).thenReturn(pets);
		Mockito.when(vetRepository.findAll()).thenReturn(vets);

		try {
			clinicService.visitOwnerPets(owner);
		}catch (ClinicServiceImpl.VisitException e)  {
			// ------------------------	5- 6 - 8 - 9 - 10 - 11	//	visitPetIfPossible function was running - entered *second* if
			fail("visitOwnerPetsTestBadDates added test but it should't have");
			return;
		}
		// ------------------------	1	//visitOwnerPets was running
		Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
		Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
		// ------------------------	2 - 3	//	inside for loop
		Mockito.verify(pet, Mockito.times(1)).getLastVisit();
		// ------------------------	4 - 5	//inside *first* if
		Mockito.verify(pet, Mockito.times(1)).getBirthDate();
		// ------------------------
		// if visitPetIfPossible was running, pet.getType() would've been invoked
		Mockito.verify(pet, Mockito.times(0)).getType();
	}

	@Test
	public void visitOwnerPetsTestSecondBadDates() {
		//	this test won't go in second if and the visitPetIfPossible will not be running at all
		Date visitDate = new Date(120, 9, 30);	//2020-10-30
		Date birthDate = new Date(120, 8, 30);	//2020-09-30
//		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
//		String date = DATE_FORMAT.format(visitDate);
//		System.out.println(date);

		pets.add(pet);
		vets.add(vet);

		Mockito.when(pet.getBirthDate()).thenReturn(birthDate);
		Mockito.when(visit.getDate()).thenReturn(visitDate);
		Mockito.when(pets.get(0).getLastVisit()).thenReturn(Optional.of(visit));
		Mockito.when(petRepository.findByOwner(owner)).thenReturn(pets);
		Mockito.when(vetRepository.findAll()).thenReturn(vets);

		try {
			clinicService.visitOwnerPets(owner);
		}catch (ClinicServiceImpl.VisitException e)  {
			// ------------------------	5- 6 - 8 - 9 - 10 - 11	//	visitPetIfPossible function was running - entered *second* if
			fail("visitOwnerPetsTestBadDates added test but it should't have");
			return;
		}
		// ------------------------	1	//visitOwnerPets was running
		Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
		Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
		// ------------------------	2 - 3	//	inside for loop
		Mockito.verify(pet, Mockito.times(1)).getLastVisit();
		// ------------------------	4 - 5	//inside *first* if
		Mockito.verify(pet, Mockito.times(1)).getBirthDate();
		// ------------------------
		// if visitPetIfPossible was running, pet.getType() would've been invoked
		Mockito.verify(pet, Mockito.times(0)).getType();
	}

	@Test
	public void visitOwnerPetsTestNullVisitAllVisited() {
		//	this test won't go in any ifs so the visitPetIfPossible function in 'else' will be running
		pets.add(pet);
		vets.add(vet);

		Mockito.when(pets.get(0).getLastVisit()).thenReturn(Optional.empty());
		Mockito.when(petRepository.findByOwner(owner)).thenReturn(pets);
		Mockito.when(vetRepository.findAll()).thenReturn(vets);

		try {
			clinicService.visitOwnerPets(owner);
		}catch (ClinicServiceImpl.VisitException e)  {
			// ------------------------	1	//visitOwnerPets was running
			Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
			Mockito.verify(petRepository, Mockito.times(1)).findByOwner(any(Owner.class));
			// ------------------------	2 - 3	//	inside for loop
			Mockito.verify(pet, Mockito.times(1)).getLastVisit();
			// ------------------------	4	//*didn't enter first if*
			Mockito.verify(pet, Mockito.times(0)).getBirthDate();
			// ------------------------
			// if visitPetIfPossible was running, pet.getType() would've been invoked
			Mockito.verify(pet, Mockito.times(1)).getType();
			return;
		}
		// ------------------------	5 - 7 - 8 - 9 - 11	//	visitPetIfPossible function was not running - didn't enter *first* if
		fail("visitOwnerPetsTestNullVisitAllVisited added test but it should't");
	}
}

