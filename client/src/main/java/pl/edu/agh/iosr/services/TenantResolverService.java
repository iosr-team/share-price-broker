package pl.edu.agh.iosr.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;

public interface TenantResolverService {
    Tenant resolveTenant();
    UserEntity resolveUser();

    Boolean canModify(Tenant tenant);
    Boolean canModify(UserEntity userEntity);
}
