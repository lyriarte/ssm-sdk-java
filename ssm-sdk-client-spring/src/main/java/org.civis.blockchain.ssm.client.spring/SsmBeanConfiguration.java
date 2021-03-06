package org.civis.blockchain.ssm.client.spring;

import org.civis.blockchain.ssm.client.SsmClient;
import org.civis.blockchain.ssm.client.SsmClientConfig;
import org.civis.blockchain.ssm.client.domain.SignerAdmin;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@EnableConfigurationProperties(SsmConfiguration.class)
@ConditionalOnProperty(name = "ssm.coop.url")
public class SsmBeanConfiguration {

    private final SsmConfiguration ssmConfiguration;

    public SsmBeanConfiguration(SsmConfiguration ssmConfiguration) {
        this.ssmConfiguration = ssmConfiguration;
    }

    @Bean
    protected SsmClientConfig ssmClientConfig() {
        return new SsmClientConfig(ssmConfiguration.getCoop().getUrl());
    }

    @Bean
    protected SsmClient ssmClient(SsmClientConfig ssmClientConfig) throws IOException {
        return SsmClient.fromConfig(ssmClientConfig);
    }

    @Bean
    @ConditionalOnProperty(prefix = "ssm.signer.admin", name = {"name", "key"})
    protected SignerAdmin adminSigner() throws Exception {
        return ssmConfiguration.adminSigner();
    }
}
