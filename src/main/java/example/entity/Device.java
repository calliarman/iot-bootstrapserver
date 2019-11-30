package example.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public
class Device {

	private @Id @NotNull String id;
	private @NotNull String accessToken;
	private @NotNull String endPoint;

	Device() {}

	public Device(String id, String accessToken, String endPoint) {
		this.id = id;
		this.accessToken = accessToken;
		this.endPoint = endPoint;
	}
}
