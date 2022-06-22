package de.unistuttgart.t2.common.scaling;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class AutoscalingMiddleware implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RequestDenier()).excludePathPatterns("/autoscaling/**");
		registry.addInterceptor(new MemoryLeaker()).excludePathPatterns("/autoscaling/**");
	}
}
