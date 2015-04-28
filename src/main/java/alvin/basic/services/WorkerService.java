package alvin.basic.services;

import alvin.basic.entities.Worker;
import alvin.basic.repositories.WorkerRepository;
import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WorkerService {
    private WorkerRepository workerRepository;

    @Inject
    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Transactional
    public void save(Worker worker) {
        workerRepository.save(worker);
    }
}
