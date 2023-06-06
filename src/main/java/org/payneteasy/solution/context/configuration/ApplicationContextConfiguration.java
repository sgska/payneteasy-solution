package org.payneteasy.solution.context.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.payneteasy.solution.context.type.BeanLoadType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * General application configuration
 */
@Getter
public class ApplicationContextConfiguration {

    private final List<BeanConfig<?>> beanConfigs;
    private final boolean webApp;


    private ApplicationContextConfiguration(List<BeanConfig<?>> beanConfigs, boolean webApp) {
        this.beanConfigs = Collections.unmodifiableList(beanConfigs);
        this.webApp = webApp;
    }

    @NoArgsConstructor
    public static class Builder {

        private final List<BeanConfig<?>> beanConfigs = new ArrayList<>();
        private boolean webApp = false;

        public Builder addBean(BeanLoadType beanLoadType, Class<?> tClass) {
            this.beanConfigs.add(new BeanConfig<>(beanLoadType, tClass));
            return this;
        }

        public Builder addBean(Class<?> tClass) {
            this.beanConfigs.add(new BeanConfig<>(BeanLoadType.DEFAULT, tClass));
            return this;
        }

        public Builder webApp(boolean webApp) {
            this.webApp = webApp;
            return this;
        }

        public ApplicationContextConfiguration build() {
            return new ApplicationContextConfiguration(this.beanConfigs, this.webApp);
        }

    }

    @Getter
    @RequiredArgsConstructor
    public static class BeanConfig<T> {

        private final BeanLoadType beanLoadType;
        private final Class<T> tClass;

    }

}
