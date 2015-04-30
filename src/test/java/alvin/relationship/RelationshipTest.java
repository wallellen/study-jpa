package alvin.relationship;

import alvin.basic.entities.Group;
import alvin.basic.entities.Interest;
import alvin.basic.entities.User;
import alvin.basic.entities.UserInfo;
import alvin.basic.services.GroupService;
import alvin.basic.services.InterestService;
import alvin.basic.services.UserService;
import alvin.configs.TestSupport;
import com.google.inject.Inject;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class RelationshipTest extends TestSupport {

    @Inject
    private UserService userService;

    @Inject
    private InterestService interestService;

    @Inject
    private GroupService groupService;

    @Test
    public void test_create_user() throws Exception {
        User user = createUser();
        userService.save(user);
        assertThat(user.getId(), is(notNullValue()));
    }

    @Test
    public void test_create_user_with_info() throws Exception {
        User user = createUser();
        UserInfo userInfo = createUserInfo(user);

        user.setUserInfo(userInfo);
        userService.save(user);
        assertThat(user.getId(), is(notNullValue()));
        assertThat(userInfo.getId(), is(notNullValue()));
    }

    @Test
    public void test_create_user_info_with_user() throws Exception {
        User user = createUser();
        UserInfo userInfo = createUserInfo(user);

        userService.save(userInfo);
        assertThat(user.getId(), is(notNullValue()));
        assertThat(userInfo.getId(), is(notNullValue()));
    }

    @Test
    public void test_delete_user() throws Exception {
        User user = createUser();
        UserInfo userInfo = createUserInfo(user);

        user.setUserInfo(userInfo);
        userService.save(user);
        em.clear();

        user = userService.findById(user.getId()).get();
        userService.delete(user);
        assertThat(user.getId(), is(notNullValue()));
    }

    @Test
    public void test_user_friend() throws Exception {
        List<User> users = createUsers(4);

        User user = users.get(0);
        user.addEmployee(users.get(1));
        user.addEmployee(users.get(2));
        user.addEmployee(users.get(3));
        userService.save(user);

        user = users.get(1);
        user.addEmployee(users.get(2));
        user.addEmployee(users.get(3));
        userService.save(user);
        em.clear();

        user = userService.findById(users.get(0).getId()).get();
        assertThat(user.getEmployees().size(), is(1));
        assertThat(user.getEmployees().get(0).getName(), is("姓名1"));

        user = userService.findById(users.get(1).getId()).get();
        assertThat(user.getEmployees().size(), is(2));
        assertThat(user.getEmployees().get(0).getName(), is("姓名2"));
        assertThat(user.getEmployees().get(1).getName(), is("姓名3"));
    }

    @Test
    public void test_user_with_interest() throws Exception {
        List<User> users = createUsers(2);
        List<Interest> interests = createInterests(5);
        em.clear();

        User user = users.get(0);
        user.addInterest(interests.get(0));
        user.addInterest(interests.get(2));

        user = users.get(1);
        user.addInterest(interests.get(0));
        user.addInterest(interests.get(1));
        user.addInterest(interests.get(3));

        userService.saveAll(users);

        em.clear();

        user = userService.findById(users.get(0).getId()).get();
        assertThat(user.getInterests().size(), is(2));
        assertThat(user.getInterests().get(0).getInterest(), is("兴趣0"));
        assertThat(user.getInterests().get(1).getInterest(), is("兴趣2"));

        user = userService.findById(users.get(1).getId()).get();
        assertThat(user.getInterests().size(), is(3));
        assertThat(user.getInterests().get(0).getInterest(), is("兴趣0"));
        assertThat(user.getInterests().get(1).getInterest(), is("兴趣1"));
        assertThat(user.getInterests().get(2).getInterest(), is("兴趣3"));

        Interest interest = interestService.findById(interests.get(0).getId()).get();
        assertThat(interest.getUsers().size(), is(2));

        interest = interestService.findById(interests.get(1).getId()).get();
        assertThat(interest.getUsers().size(), is(1));
    }

    @Test
    public void test_group_user() throws Exception {
        List<User> users = createUsers(5);
        Group group = createGroup();
        group.addUser(users.get(0));
        group.addUser(users.get(2));
        group.addUser(users.get(4));
        groupService.save(group);

        em.clear();

        users = userService.findAll();
        assertThat(users.size(), is(3));
        assertThat(users.get(0).getName(), is("姓名0"));
        assertThat(users.get(1).getName(), is("姓名2"));
        assertThat(users.get(2).getName(), is("姓名4"));
    }

    @Test
    public void test_cascade_delete() throws Exception {
        List<User> users = createUsers(5);
        Group group = createGroup();
        group.addUser(users.get(0));
        group.addUser(users.get(2));
        group.addUser(users.get(4));
        groupService.save(group);

        em.clear();

        em.getTransaction().begin();
        try {
            group = groupService.findById(group.getId()).get();
            assertThat(group.getUsers().size(), is(3));

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

    @Test
    public void test_one_plus_n() throws Exception {
        List<User> users = createUsers(5);
        List<Group> groups = createGroups(2);

        Group group = groups.get(0);
        group.addUser(users.get(0));
        group.addUser(users.get(1));
        group.addUser(users.get(2));
        groupService.save(group);

        group = groups.get(1);
        group.addUser(users.get(3));
        group.addUser(users.get(4));
        groupService.save(group);

        em.clear();

        groups = groupService.findAll();
//        assertThat(groups.get(0).getUsers().size(), is(3));
//        assertThat(groups.get(1).getUsers().size(), is(2));
    }

    private User createUser() {
        User user = new User();
        user.setName("Alvin");
        user.setPassword("1234");
        user.setLastLoginTime(LocalDateTime.now().atOffset(ZoneOffset.UTC).toLocalDateTime());
        return user;
    }

    private UserInfo createUserInfo(User user) {
        UserInfo info = new UserInfo();
        info.setFullName("Alvin.Qu");
        info.setGender("M");
        info.setBirthday(LocalDateTime.of(1981, 3, 17, 0, 0).atOffset(ZoneOffset.UTC).toLocalDateTime());
        info.setEmail("alvin@fake.com");
        info.setTelephone("13000000000");
        info.setRemark("Test");
        info.setUser(user);
        return info;
    }

    private List<Interest> createInterests(int n) {
        List<Interest> interests = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Interest interest = new Interest();
            interest.setInterest("兴趣" + i);
            interests.add(interest);
        }
        return interests;
    }

    private List<User> createUsers(int n) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            User user = new User();
            user.setName("姓名" + i);
            user.setPassword("123");
            user.setLastLoginTime(LocalDateTime.now().atOffset(ZoneOffset.UTC).toLocalDateTime());
            users.add(user);
        }
        return users;
    }

    private Group createGroup() {
        Group group = new Group();
        group.setName("分组");
        return group;
    }

    private List<Group> createGroups(int n) {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Group group = new Group();
            group.setName("分组" + i);
            groups.add(group);
        }
        return groups;
    }

    @Override
    protected String[] getTruncateTables() {
        return new String[]{"user_", "user_info_", "group_",
                "interest_", "user_interest_"};
    }
}
