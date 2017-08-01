import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.business.RentalCarBusiness;
import com.service.RentalCarService;
import com.ui.RentalCarServiceController;

@SpringBootApplication
@ComponentScan(basePackageClasses = { RentalCarServiceController.class, RentalCarService.class,
		RentalCarBusiness.class })
public class RentalCarApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RentalCarApplication.class, args);

		try {
			context.getBean(RentalCarBusiness.class).obtainJsonFile();
		} catch (BeansException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
