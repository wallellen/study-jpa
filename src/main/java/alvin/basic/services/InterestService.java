package alvin.basic.services;

import alvin.basic.entities.Interest;
import alvin.basic.repositories.InterestRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class InterestService {
    private InterestRepository interestRepository;

    @Inject
    public InterestService(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    public Optional<Interest> findById(Integer id) {
        return interestRepository.find(id);
    }
}
