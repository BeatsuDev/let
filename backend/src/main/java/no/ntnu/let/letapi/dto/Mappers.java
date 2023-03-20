package no.ntnu.let.letapi.dto;

import no.ntnu.let.letapi.dto.listing.ListingMapper;
import no.ntnu.let.letapi.dto.listing.ListingMapperImpl;
import no.ntnu.let.letapi.dto.user.UserMapper;
import no.ntnu.let.letapi.dto.user.UserMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Mappers {
    @Bean
    UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    ListingMapper listingMapper(@Autowired UserMapper userMapper) {
        return new ListingMapperImpl(userMapper);
    }
}
