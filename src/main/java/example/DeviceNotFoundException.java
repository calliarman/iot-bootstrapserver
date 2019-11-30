package example;

public class DeviceNotFoundException extends RuntimeException {

	public DeviceNotFoundException(String id) {
		super("Could not find device " + id);
	}
}
