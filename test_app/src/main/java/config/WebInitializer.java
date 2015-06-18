package config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { DataSourceConfig.class, PersistenceConfig.class, CoreConfig.class};
    }

    @Override
    public Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    public String[] getServletMappings() {
        return new String[] { "/" };
    }


}