package cz.muni.fi.pa165.travelagency.web.config;


import cz.muni.fi.pa165.travelagency.sampledata.TravelAgencyWithSampleDataConfiguration;
import cz.muni.fi.pa165.travelagency.web.converter.IdToTripConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created on 06.12.2016.
 *
 * @author Martin Salata
 */
@Configuration

@Import(TravelAgencyWithSampleDataConfiguration.class)
@ComponentScan(basePackages = "cz.muni.fi.pa165.travelagency")
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * Maps the main login page to a login view.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("travel");
    }

    /**
     * Provides mapping from view names to JSP pages in WEB-INF/pages directory.
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    protected IdToTripConverter idToTripConverter() {
        return new IdToTripConverter();
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(idToTripConverter());
        super.addFormatters(registry);
    }

}
