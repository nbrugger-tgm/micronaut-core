package org.particleframework.context;

import org.particleframework.context.env.Environment;
import org.particleframework.config.PropertyResolver;
import org.particleframework.core.convert.ConversionService;

import java.util.function.Consumer;

/**
 * An application context extends a {@link BeanContext} and adds the concepts of configuration, environments and runtimes.
 *
 * @author Graeme Rocher
 * @since 1.0
 */
public interface ApplicationContext extends BeanContext, PropertyResolver {

    /**
     * @return The default conversion service
     */
    ConversionService getConversionService();

    /**
     * @return The application environment
     */
    Environment getEnvironment();

    /**
     * Starts the application context
     *
     * @return The application context
     */
    @Override
    ApplicationContext start();
    /**
     * Stops the application context
     *
     * @return The application context
     */
    @Override
    ApplicationContext stop();

    /**
     * Allow configuration the {@link Environment}
     * @param consumer The consumer
     * @return This context
     */
    default ApplicationContext environment(Consumer<Environment> consumer) {
        consumer.accept(getEnvironment());
        return this;
    }

    @Override
    default ApplicationContext registerSingleton(Object singleton) {
        Class type = singleton.getClass();
        return registerSingleton(type, singleton);
    }

    @Override
    <T> ApplicationContext registerSingleton(Class<T> type, T singleton);

    /**
     * Run the {@link ApplicationContext}. This method will instantiate a new {@link ApplicationContext} and call {@link #start()}
     *
     * @param environment The environment to use
     * @return The running {@link BeanContext}
     */
    static ApplicationContext run(String environment) {
        return build(environment).start();
    }

    /**
     * Build a {@link ApplicationContext}
     *
     * @param environment The environment to use
     * @return The built, but not yet running {@link ApplicationContext}
     */
    static ApplicationContext build(String environment) {
        return new DefaultApplicationContext(environment);
    }

    /**
     * Run the {@link BeanContext}. This method will instantiate a new {@link BeanContext} and call {@link #start()}
     *
     * @param environment The environment to use
     * @param classLoader The classloader to use
     * @return The running {@link ApplicationContext}
     */
    static ApplicationContext run(String environment, ClassLoader classLoader) {
        return build(environment, classLoader).start();
    }

    /**
     * Build a {@link ApplicationContext}
     *
     * @param environment The environment to use
     * @param classLoader The classloader to use
     * @return The built, but not yet running {@link ApplicationContext}
     */
    static ApplicationContext build(String environment, ClassLoader classLoader) {
        return new DefaultApplicationContext(environment, classLoader);
    }
}
