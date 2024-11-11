package edu.ada.grupo5.movies_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {
}
