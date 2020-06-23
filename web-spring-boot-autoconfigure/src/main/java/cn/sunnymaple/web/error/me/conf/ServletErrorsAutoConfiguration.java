package cn.sunnymaple.web.error.me.conf;

import cn.sunnymaple.web.error.me.WebErrorHandlers;
import cn.sunnymaple.web.error.me.adapter.HttpErrorAttributesAdapter;
import cn.sunnymaple.web.error.me.adapter.attributes.ServletErrorAttributes;
import cn.sunnymaple.web.error.me.mvc.ErrorsControllerAdvice;
import cn.sunnymaple.web.error.me.HttpError;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;

import static org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type.SERVLET;

/**
 * Encapsulates servlet-specific parts of errors auto-configuration.
 *
 * @author Ali Dehghani
 */
@ConditionalOnWebApplication(type = SERVLET)
@AutoConfigureAfter(ErrorsAutoConfiguration.class)
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ServletErrorsAutoConfiguration {

    /**
     * Registers a {@link org.springframework.web.bind.annotation.RestControllerAdvice} to catch all
     * exceptions thrown by the web layer. If there was no {@link WebErrorHandlers} in the application
     * context, then the advice would not be registered.
     *
     * @param webErrorHandlers           The exception handler.
     * @param httpErrorAttributesAdapter To adapt our and Spring Boot's error representations.
     * @return The registered controller advice.
     */
    @Bean
    @ConditionalOnBean(WebErrorHandlers.class)
    public ErrorsControllerAdvice errorsControllerAdvice(WebErrorHandlers webErrorHandlers,
                                                         HttpErrorAttributesAdapter httpErrorAttributesAdapter) {
        return new ErrorsControllerAdvice(webErrorHandlers, httpErrorAttributesAdapter) {
        };
    }

    /**
     * Registers a {@link ErrorAttributes} implementation which would replace the default one provided
     * by the Spring Boot. This {@link ErrorAttributes} would be used to adapt Spring Boot's error model
     * to our customized model.
     *
     * @param webErrorHandlers           To handle exceptions.
     * @param httpErrorAttributesAdapter Adapter between {@link HttpError} and
     *                                   {@link ErrorAttributes}.
     * @return The to-be-registered {@link ServletErrorAttributes}.
     */
    @Bean
    @ConditionalOnBean(WebErrorHandlers.class)
    public ErrorAttributes errorAttributes(WebErrorHandlers webErrorHandlers,
                                           HttpErrorAttributesAdapter httpErrorAttributesAdapter) {
        return new ServletErrorAttributes(webErrorHandlers, httpErrorAttributesAdapter);
    }
}
