package alvin.basic.services;

import alvin.basic.entities.Group;
import alvin.basic.entities.User;
import alvin.basic.entities.UserInfo;
import alvin.basic.repositories.GroupRepository;
import alvin.basic.repositories.UserRepository;
import com.google.inject.persist.Transactional;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class GroupService {
    private GroupRepository groupRepository;

    @Inject
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public void save(Group group) {
        groupRepository.save(group);
    }

    public Optional<Group> findById(Integer id) {
        return groupRepository.find(id);
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}
