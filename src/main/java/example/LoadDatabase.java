package example;

import example.entity.Device;
import example.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(DeviceRepository deviceRepository) {
		return args -> {
			log.info("Preloading " + deviceRepository.save(new Device("173ffe20-8ce5-11e9-a94e-eb8771cc07b9", "xYCk2546Dyhl6xbXQcFH", "http://iotmanager4.westeurope.cloudapp.azure.com:9090/")));
		};
	}
}
