package kr.ac.sejong.api.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
    @Bean(name = "processing")
    ManagedChannel setProcessingChannel() {
        return ManagedChannelBuilder.forAddress("127.20.10.3", 8090).usePlaintext().build();
    }
}
