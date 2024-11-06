package edu.ada.grupo5.movies_api.doc;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Movies API",
        description = """
                An API that retrieves information from TMDb's API and returns it with additional processing.
                \nThis API was developed by the G5, components:
                                 \n* Fernando -> https://github.com/farchettiensis
                                 \n* Nathan -> https://github.com/nathanllss
                                 \n* Lothar -> https://github.com/LotharNunnenkamp
                                 \n* Victor -> https://github.com/victot-exe
                                 \n* Erick -> https://github.com/Erick-sr""",
        version = "1.0"
    )
)
public class SwaggerConfig {
}
