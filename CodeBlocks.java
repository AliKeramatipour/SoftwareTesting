



public void visitOwnerPets(Owner owner) {
    //--- Block one ---
    Collection<Pet> pets = petRepository.findByOwner(owner);
    Collection<Vet> vets = vetRepository.findAll();
    List<Pet> notVisited = new ArrayList<>();
    DateTime today = new DateTime();

    for (Pet pet : pets) {
        //-- Block four --
        Optional<Visit> last = pet.getLastVisit();
        if (pet != null)
        if (last.isPresent()) {
            //-- Block five --
            int age = Years.yearsBetween(new DateTime(pet.getBirthDate()), today).getYears();
            int daysFromLastVisit = Days.daysBetween(new DateTime(last.get().getDate()), today).getDays();
            if ((age > 3 && daysFromLastVisit > 364) || (age <= 3 && daysFromLastVisit > 182)) {
                //-- Block six --
                visitPetIfPossible(vets, notVisited, pet);
            }
        } else {
            //-- Block seven --
            visitPetIfPossible(vets, notVisited, pet);
        }
    }
    if (notVisited.size() > 0) {
        //-- Block ten --
        throw new VisitException(notVisited);
    }

    //-- first Node
    //-- Methods Arguments Definitions will be in this block too
    //-- Third Node
    //-- 4th Node
    Visit currentVisit = this.clinicService.findVisitById(visitId);


    //-- 5th Node
    return new ResponseEntity<Visit>(HttpStatus.NOT_FOUND);


//-- 6th Node
currentVisit.setDate(visit.getDate());
currentVisit.setDescription(visit.getDescription());
currentVisit.setPet(visit.getPet());
this.clinicService.saveVisit(currentVisit);
return new ResponseEntity<Visit>(currentVisit, HttpStatus.NO_CONTENT);
}



public ResponseEntity<Visit> updateVisit(@PathVariable("visitId") int visitId, @RequestBody @Valid Visit visit, BindingResult bindingResult){
    BindingErrorsResponse errors = new BindingErrorsResponse();
    HttpHeaders headers = new HttpHeaders();
    if(bindingResult.hasErrors() || (visit == null) || (visit.getPet() == null)){
        errors.addAllErrors(bindingResult);
        headers.add("errors", errors.toJSON());
        return new ResponseEntity<Visit>(headers, HttpStatus.BAD_REQUEST);
    }
    Visit currentVisit = this.clinicService.findVisitById(visitId);
    if(currentVisit == null){
        return new ResponseEntity<Visit>(HttpStatus.NOT_FOUND);
    }
    currentVisit.setDate(visit.getDate());
    currentVisit.setDescription(visit.getDescription());
    currentVisit.setPet(visit.getPet());
    this.clinicService.saveVisit(currentVisit);
    return new ResponseEntity<Visit>(currentVisit, HttpStatus.NO_CONTENT);
}


public ResponseEntity<Visit> updateVisit(@PathVariable("visitId") int visitId, @RequestBody @Valid Visit visit, BindingResult bindingResult){
    BindingErrorsResponse errors = new BindingErrorsResponse();
    HttpHeaders headers = new HttpHeaders();
    if(bindingResult.hasErrors() || (visit == null) || (visit.getPet() == null)){
        errors.addAllErrors(bindingResult);
        headers.add("errors", errors.toJSON());
        return new ResponseEntity<Visit>(headers, HttpStatus.BAD_REQUEST);
    }
    Visit currentVisit = this.clinicService.findVisitById(visitId);
    if(currentVisit == null){
        return new ResponseEntity<Visit>(HttpStatus.NOT_FOUND);
    }
    currentVisit.setDate(visit.getDate());
    currentVisit.setDescription(visit.getDescription());
    currentVisit.setPet(visit.getPet());
    this.clinicService.saveVisit(currentVisit);
    return new ResponseEntity<Visit>(currentVisit, HttpStatus.NO_CONTENT);
}

public ResponseEntity<Owner> updateOwner(@PathVariable("ownerId") int ownerId, @RequestBody @Valid Owner owner,
		BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || (owner == null)) {
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Owner>(headers, HttpStatus.BAD_REQUEST);
		}
		Owner currentOwner = this.clinicService.findOwnerById(ownerId);
		if (currentOwner == null) {
			return new ResponseEntity<Owner>(HttpStatus.NOT_FOUND);
		}
		currentOwner.setAddress(owner.getAddress());
		currentOwner.setCity(owner.getCity());
		currentOwner.setFirstName(owner.getFirstName());
		currentOwner.setLastName(owner.getLastName());
		currentOwner.setTelephone(owner.getTelephone());
		this.clinicService.saveOwner(currentOwner);
		return new ResponseEntity<Owner>(currentOwner, HttpStatus.NO_CONTENT);
    }
    


public void saveUser(User user) throws Exception {

    if(user.getRoles() == null || user.getRoles().isEmpty()) {
        throw new Exception("User must have at least a role set!");
    }

    for (Role role : user.getRoles()) {
        if(!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }

        if(role.getUser() == null) {
            role.setUser(user);
        }
    }

    userRepository.save(user);
}