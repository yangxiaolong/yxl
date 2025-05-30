package com.geekhalo.like.domain.user;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NullActionUserLoaderConfiguration {

    @ConditionalOnMissingBean(ActionUserLoader.class)
    @Bean(LoadActionUserByUserId.BEAN_NAME)
    public ActionUserLoader actionUserLoader(){
        return new NullActionUserLoader();
    }
    private static class NullActionUserLoader implements ActionUserLoader{
        @Override
        public ActionUser loadByUserId(Long userId) {
            return ActionUser.apply(userId);
        }
    }
}

