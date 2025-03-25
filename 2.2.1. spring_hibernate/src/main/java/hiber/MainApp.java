package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.CarService;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);
      CarService carService = context.getBean(CarService.class);

      User user1 = new User("User1", "Lastname1", "user1@mail.ru");
      User user2 = new User("User2", "Lastname2", "user2@mail.ru");
      User user3 = new User("User3", "Lastname3", "user3@mail.ru");

      Car car1 = new Car("BMW", 5);
      Car car2 = new Car("Audi", 7);
      Car car3 = new Car("BMW", 1);
      Car car4 = new Car("Skoda", 4);

      user1.setCar(car1);
      user2.setCar(car2);
      user3.setCar(car3);
      user3.setCar(car4);

      userService.add(user1);
      userService.add(user2);
      userService.add(user3);

//      carService.add(new Car("BMW", 5));
//      carService.add(new Car("Audi", 7));
//      carService.add(new Car("BMW", 1));
//      carService.add(new Car("Skoda", 7));
//
//      userService.add(new User("User1", "Lastname1", "user1@mail.ru"));
//      userService.add(new User("User2", "Lastname2", "user2@mail.ru"));
//      userService.add(new User("User3", "Lastname3", "user3@mail.ru"));
//      userService.add(new User("User4", "Lastname4", "user4@mail.ru"));
//
//      User user1 = userService.getUserByCarModelAndSeries("Audi", 7);
//      System.out.println(user1);
//
//      List<User> users = userService.listUsers();
//      for (User user : users) {
//         System.out.println("Id = "+user.getId());
//         System.out.println("First Name = "+user.getFirstName());
//         System.out.println("Last Name = "+user.getLastName());
//         System.out.println("Email = "+user.getEmail());
//         System.out.println();
//      }

      User userCar = userService.getUserByCarModelAndSeries("Skoda", 4);
      System.out.println(userCar);
      context.close();
   }
}
