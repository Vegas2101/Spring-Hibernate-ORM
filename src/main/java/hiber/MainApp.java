package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car();
      car1.setModel("Mazda");
      car1.setSerial(2);

      Car car2 = new Car();
      car2.setModel("Toyota");
      car2.setSerial(3);

      User user1 = new User();
      user1.setFirstName("John");
      user1.setLastName("Doe");
      user1.setEmail("john@mail.ru");
      user1.setCar(car1);

      User user2 = new User();
      user2.setFirstName("Jane");
      user2.setLastName("Smith");
      user2.setEmail("jane@mail.ru");
      user2.setCar(car2);

      userService.add(user1);
      userService.add(user2);

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println("Car Model = "+user.getCar().getModel());
         System.out.println("Car Serial = "+user.getCar().getSerial());
         System.out.println();
      }
      User foundUser = userService.findUserByCar("Mazda", 2);
      if (foundUser != null) {
         System.out.println("Найден Пользователь: Имя - " + foundUser.getFirstName() + ", Фамилия - " + foundUser.getLastName()
         + ", с электронной почтой: " + foundUser.getEmail() + ", с маркой машины: " + foundUser.getCar().getModel() +
                 ", с серийным номером машины: " + foundUser.getCar().getSerial() + ".");
      } else {
         System.out.println("Пользователь не найден.");
      }

      context.close();
   }
}
