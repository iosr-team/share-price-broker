package pl.edu.agh.iosr.services;

import pl.edu.agh.iosr.model.entity.StockQuote;
import pl.edu.agh.iosr.model.entity.Tenant;
import pl.edu.agh.iosr.model.entity.UserEntity;

public interface TenantResolverService {
    Tenant resolveTenant();
    UserEntity resolveUser();

    Boolean canModify(Tenant tenant);
    Boolean canModify(UserEntity userEntity);
    Boolean canModify(StockQuote stockQuote);
}
