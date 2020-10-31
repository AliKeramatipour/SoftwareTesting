package org.springframework.samples.petclinic.rest;

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

	ArrayList<Pet> pets = new ArrayList<Pet>(2);
	ArrayList<Vet> vets = new ArrayList<Vet>(2);

	@InjectMocks
	ClinicServiceImpl clinicService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void visitOwnerPetsTestValidVisitAllVisited() {
		//	this test will go inside both ifs so the visitPetIfPossible function in the first if will be running
		Date date = new Date();
		date.setTime(700000000L);
		pets.add(pet);
		vets.add(vet);

		Mockito.when(pet.getBirthDate()).thenReturn(date);
		Mockito.when(visit.getDate()).thenReturn(date);
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

			return;
		}
		// ------------------------	5 - 7 - 8 - 9 - 11	//	visitPetIfPossible function was not running - didn't enter second if
		fail("visitOwnerPetsTestValidVisitAllVisited added test but it should't have");
	}

	@Test
	public void visitOwnerPetsTestBadDates() {
		//	this test won't go in any ifs and the visitPetIfPossible will not be running at all
		Date date = new Date(1000, 1, 1);
		pets.add(pet);
		vets.add(vet);

		Mockito.when(pet.getBirthDate()).thenReturn(date);
		Mockito.when(visit.getDate()).thenReturn(date);
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
			return;
		}
		// ------------------------	5 - 7 - 8 - 9 - 11	//	visitPetIfPossible function was not running - didn't enter *first* if
		fail("visitOwnerPetsTestNullVisitAllVisited added test but it should't");
	}
}
