package alvin.builders;

import alvin.basic.entities.Worker;
import alvin.basic.services.WorkerService;

import javax.inject.Inject;

public class WorkerBuilder implements Builder<Worker> {
    private WorkerService workerService;

    private String name = "Alvin";
    private String gender = "M";

    @Inject
    public WorkerBuilder(WorkerService workerService) {
        this.workerService = workerService;
    }

    public WorkerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public WorkerBuilder gender(String gender) {
        this.gender = gender;
        return this;
    }

    @Override
    public Worker build() {
        Worker worker = new Worker();
        worker.setName(name);
        worker.setGender(gender);
        return worker;
    }

    @Override
    public Worker create() {
        Worker worker = build();
        workerService.save(worker);
        return worker;
    }
}
