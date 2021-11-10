package ir.jiring.accounting;

import ir.jiring.accounting.model.Role;
import ir.jiring.accounting.model.User;
import ir.jiring.accounting.service.interfaces.IUserService;
import net.sf.ehcache.CacheManager;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;

@SpringBootTest
class AccountingApplicationTests {

	@Autowired
	private IUserService iUserService;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Test
	void contextLoads() {
		User user = new User();
		user.setFirstName("Saeed");
		user.setLastName("Safari");
		user.setUsername("saeed");
		user.setPassword(new BCryptPasswordEncoder().encode("1234"));
		user.setAmount(new BigDecimal(0));
		user.setEnabled(true);

		Role role = new Role();
		role.setTitle("CUSTOMER");
		user.addRole(role);
		iUserService.add(user);
		//iUserService.loadById(user.getId());
		int size = CacheManager.ALL_CACHE_MANAGERS.get(0)
				.getCache("ir.jiring.accounting.model.User").getSize();
		System.out.println(">>>>>>>>>>Size:" + size);
	}

	@Test
	void testOptimisticConcurrency() {
		Session session1 = entityManagerFactory.createEntityManager().unwrap(Session.class);
		Session session2 = entityManagerFactory.createEntityManager().unwrap(Session.class);

		session1.getTransaction().begin();
		session2.getTransaction().begin();

		User user1 = session1.find(User.class, 19L);
		User user2 = session2.find(User.class, 19L);

		user1.setAmount(user1.getAmount().add(new BigDecimal(200)));
		session1.flush();
		session1.getTransaction().commit();

		user2.setAmount(user2.getAmount().add(new BigDecimal(200)));
		session2.flush();
		session2.getTransaction().commit();
	}
}
