package alvin.relationship;

import alvin.basic.entities.Interest;
import alvin.basic.entities.User;
import alvin.basic.entities.UserInfo;
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
        user.addFriend(users.get(1));
        user.addFriend(users.get(2));
        user.addFriend(users.get(3));
        userService.save(user);

        user = users.get(1);
        user.addFriend(users.get(2));
        user.addFriend(users.get(3));
        userService.save(user);
        em.clear();

        user = userService.findById(users.get(0).getId()).get();
        assertThat(user.getFriends().size(), is(1));
        assertThat(user.getFriends().get(0).getName(), is("姓名1"));

        user = userService.findById(users.get(1).getId()).get();
        assertThat(user.getFriends().size(), is(2));
        assertThat(user.getFriends().get(0).getName(), is("姓名2"));
        assertThat(user.getFriends().get(1).getName(), is("姓名3"));
    }

    @Test
    public void test_user_with_interest() throws Exception {
        List<User> users = createUsers(2);
        List<Interest> interests = createIntereasts(5);
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

    private List<Interest> createIntereasts(int n) {
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

    @Override
    protected String[] getTruncateTables() {
        return new String[]{"user_", "user_info_", "group_", "group_user_",
                "interest_", "user_interest_"};
    }
}
