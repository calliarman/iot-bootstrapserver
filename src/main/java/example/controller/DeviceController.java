package example.controller;


// tag::hateoas-imports[]
// end::hateoas-imports[]

import example.DeviceNotFoundException;
import example.entity.Device;
import example.repository.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class DeviceController {

    private final DeviceRepository repository;

    DeviceController(DeviceRepository repository) {
        this.repository = repository;
    }

    // Aggregate root

	/*// tag::get-aggregate-root[]
	@GetMapping("/devices")
	Resources<Resource<Device>> all() {

		List<Resource<Device>> devices = repository.findAll().stream()
			.map(employee -> new Resource<>(employee,
				linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
				linkTo(methodOn(EmployeeController.class).all()).withRel("devices")))
			.collect(Collectors.toList());

		return new Resources<>(devices,
			linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
	}
	// end::get-aggregate-root[]*/

    @GetMapping("/devices")
    public ResponseEntity<Iterable<Device>> all() {
        try {
            return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/devices")
    ResponseEntity<Device> create(@RequestBody Device newDevice) {
        return new ResponseEntity<>(repository.save(newDevice), HttpStatus.CREATED);
    }

    // Single item
	/*// tag::get-single-item[]
	@GetMapping("/devices/{id}")
	Resource<Device> one(@PathVariable Long id) {
		
		Device employee = repository.findById(id)
			.orElseThrow(() -> new DeviceNotFoundException(id));
		
		return new Resource<>(employee,
			linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
			linkTo(methodOn(EmployeeController.class).all()).withRel("devices"));
	}
	// end::get-single-item[]*/

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> one(@PathVariable String id) {
        Device device = repository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        try {
            return new ResponseEntity<>(device, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/devices/{id}")
    ResponseEntity<Device> replace(@RequestBody Device newDevice, @PathVariable String id) {

        return repository.findById(id)
                .map(device -> {
                    device.setId(newDevice.getId());
                    device.setAccessToken(newDevice.getAccessToken());
                    return new ResponseEntity<>(repository.save(device), HttpStatus.OK);
                })
                .orElseGet(() -> {
                    newDevice.setId(id);
                    return new ResponseEntity<>(repository.save(newDevice), HttpStatus.CREATED);

                });
    }

    @DeleteMapping("/devices/{id}")
    ResponseEntity<Device> delete(@PathVariable String id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
