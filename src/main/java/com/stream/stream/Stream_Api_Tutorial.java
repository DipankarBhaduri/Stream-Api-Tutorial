package com.stream.stream;

import com.stream.models.ApplicationUser;
import com.stream.repositories.ApplicationUserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest")
public class Stream_Api_Tutorial {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    /**
     * @Filter -->
     * Description: This operation filters the elements in the stream based on a specified condition.
     **/
    @PostMapping("/filter")
    public void filter() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        applicationUserList = applicationUserList.stream().filter(user -> user.getGender()
                .equals("Male") && user.getAge() <= 18)
                .collect(Collectors.toList());
        applicationUserList.stream().forEach(applicationUser -> {
            System.out.println(applicationUser.getName()+" - "+ applicationUser.getAge()+" - "+ applicationUser.getGender());
        });
    }

    /**
     * @Map -->
     * Description: This operation maps the elements in the stream to a new value or structure.
     */
    @PostMapping("/map")
    public void map() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<String> applicationUsers = applicationUserList.stream()
                .filter(applicationUser -> applicationUser != null && StringUtils.isNotBlank(applicationUser.getName()))
                .map(ApplicationUser::getName).toList();
        applicationUsers.stream().forEach(name -> System.out.print(name+", "));
    }

    /**
     * @Collect -->
     * Description: This operation collects the elements in the stream into a collection, such as a List or Set.
     */
    @PostMapping("/collect")
    public void collect() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<String> applicationUsersName= applicationUserList.stream()
                .map(user -> user.getName()+" - "+user.getAge())
                .toList();

        HashSet<Integer> ages = (HashSet<Integer>) applicationUserList.stream()
                .map(user -> user.getAge())
                .collect(Collectors.toSet());

        applicationUsersName.stream().forEach(System.out::println);
        ages.stream().forEach(System.out::println);
    }

    /**
     * @ForEach -->
     * Description: This operation iterates over the elements in the stream and performs an action for each element.
     */
    @PostMapping("/forEach")
    public void forEach() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        applicationUserList.stream()
                .forEach(applicationUser -> System.out.println(applicationUser.getGender()));
    }

    /**
     * @Reduce -->
     * Description: This operation combines the elements in the stream into a single result using a
     *              specified binary operation.
     */
    @PostMapping("/reduce")
    public void reduce() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAged = applicationUserList.stream()
                .map(applicationUser -> applicationUser.getAge())
                .collect(Collectors.toList());

        int age = allAged.stream().reduce(0, (num1, num2) -> num1+num2);
        System.out.println(age);
    }

    /**
     * @Sort -->
     * Description: This operation sorts the elements in the stream based on a specified comparator.
     */
    @PostMapping("/sort")
    public void sort() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAge = applicationUserList.stream()
                .map(ApplicationUser::getAge).sorted().toList();
        allAge.stream().forEach(System.out::println);
    }

    /**
     * @Distinct -->
     * Description: This operation removes duplicate elements from the stream.
     */
    @PostMapping("/distinct")
    public void distinct() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAge = applicationUserList.stream()
                .map(ApplicationUser::getAge).distinct().sorted().toList();
        allAge.stream().forEach(System.out::println);
    }

    /**
     * @Limit -->
     * Description: This operation limits the number of elements in the stream to a specified maximum.
     */
    @PostMapping("/limit")
    public void limit() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAge = applicationUserList.stream()
                .map(ApplicationUser::getAge).distinct().sorted().limit(10).toList();
        allAge.stream().forEach(System.out::println);
    }

    /**
     * @Skip -->
     * Description: This operation skips the first N elements in the stream and returns the rest.
     */
    @PostMapping("/skip")
    public void skip() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAge = applicationUserList.stream()
                .map(ApplicationUser::getAge).distinct().sorted().skip(25).toList();
        allAge.stream().forEach(System.out::println);
    }

    /**
     * @FlatMap -->
     * Description: This operation maps each element in the stream to multiple values and flattens them into a single stream.
     */
    @PostMapping("/flatMap")
    public void flatMap() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        applicationUserList.stream().forEach( applicationUser -> {
            if(applicationUser.getContactNumber() != null && applicationUser.getContactNumber().size() == 1) {
                int num = (int) (Math.random() * 10);
                while (num != 0) {
                    int index = 0;
                    String number = "";
                    while(index != 10) {
                        number += (int)(Math.random() * 10);
                        index++;
                    }
                    num--;
                    applicationUser.getContactNumber().add(number);
                }
            }
        });
        applicationUserList.stream().forEach(applicationUser -> {
            applicationUserRepository.save(applicationUser);
        });

        List<String> phoneNumberList = applicationUserList.stream()
                .flatMap(applicationUser -> applicationUser.getContactNumber().stream())
                .collect(Collectors.toList());

        phoneNumberList.stream().forEach(System.out::println);
    }

    /**
     * @AnyMatch -->
     * Description: This operation checks if any elements in the stream match a given condition and returns a boolean result.
     */
    @PostMapping("/anyMatch")
    public void anyMatch() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        boolean b = applicationUserList.stream()
                .anyMatch(applicationUser -> {
                    if(!applicationUser.getContactNumber().isEmpty()) {
                        return applicationUser.getContactNumber().stream().anyMatch(number -> number.contains("-"));
                    } else {
                        return false;
                    }
                });
        if(b) {
            System.out.println("Contains - In Phone Number");
        }
    }

    /**
     * @AllMatch -->
     * Description: This operation checks if all elements in the stream match a given condition and returns a boolean result.
     */
    @PostMapping("/allMatch")
    public void allMatch() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        boolean b = applicationUserList.stream()
                .allMatch(applicationUser -> {
                    if(!applicationUser.getContactNumber().isEmpty()) {
                        return applicationUser.getContactNumber().stream().anyMatch(number -> number.length() > 11);
                    } else {
                        return false;
                    }
                });
        if(b) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    /**
     * @NoneMatch -->
     * Description: This operation checks if none of the elements in the stream match a given condition and returns a boolean result.
     */
    @PostMapping("/noneMatch")
    public void noneMatch() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        boolean b = applicationUserList.stream()
                .allMatch(applicationUser -> {
                    if(!applicationUser.getContactNumber().isEmpty()) {
                        return applicationUser.getContactNumber().stream().anyMatch(number -> number.length() > 11);
                    } else {
                        return false;
                    }
                });
        if(b) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    /**
     * @PartitioningBy -->
     * Description: This operation partitions elements in the stream into two groups based on a provided predicate.
     */
    @PostMapping("/partitioningBy")
    public void partitioningBy() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> integerList = applicationUserList.stream().map(applicationUser -> applicationUser.getAge()).toList();
        Map<Boolean, List<Integer>> map = integerList.stream().collect(Collectors.partitioningBy(num -> num > 25));
        map.entrySet().forEach(map1-> System.out.println(map1.getKey()+" - "+map1.getValue().toString()));
    }

    /**
     * @Min -->
     * Description: This operation finds the minimum element in the stream based on a comparator.
     */
    @PostMapping("/min")
    public void min() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> integerList = applicationUserList
                .stream()
                .map(ApplicationUser::getAge)
                .collect(Collectors.toList());
        int minValue = integerList.stream().min(Integer::compare).get();
        System.out.println(minValue);
    }

    /**
     * @Max -->
     * Description: This operation finds the maximum element in the stream based on a comparator.
     */
    @PostMapping("/max")
    public void max() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> ageList = applicationUserList
                .stream()
                .map(applicationUser -> applicationUser.getAge())
                .collect(Collectors.toList());
        int maxValue = ageList.stream().max(Integer::compare).get();
        System.out.println(maxValue);
    }

    /**
     * @Average -->
     * Description: This operation calculates the average of numeric elements in the stream.
     */
    @PostMapping("/average")
    public void average() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> ageList = applicationUserList
                .stream()
                .map(ApplicationUser::getAge)
                .toList();
        LongSummaryStatistics longSummaryStatistics = ageList.stream().collect(Collectors.summarizingLong(Integer::intValue));
        System.out.println(longSummaryStatistics.getAverage());
    }

    /**
     * @Sum -->
     * Description: This operation calculates the sum of numeric elements in the stream.
     */
    @PostMapping("/sum")
    public void sum() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> ageList = applicationUserList.stream().map(ApplicationUser::getAge).toList();
        LongSummaryStatistics longSummaryStatistics = ageList.stream().collect(Collectors.summarizingLong(Integer::intValue));
        System.out.println(longSummaryStatistics.getSum());
    }

    /**
     * @FindFirst -->
     * Description: This operation returns the first element in the stream, or an empty Optional if the stream is empty.
     */
    @PostMapping("/findFirst")
    public void findFirst() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        ApplicationUser applicationUser = applicationUserList.stream()
                .filter(appUser -> appUser.getName().toLowerCase().startsWith("d"))
                .findFirst().get();
        System.out.println(applicationUser.getName());
    }

    /**
     * @FindAny -->
     * Description: This operation returns any element in the stream, or an empty Optional if the stream is empty.
     */
    @PostMapping("/findAny")
    public void findAny() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        ApplicationUser applicationUser = applicationUserList.stream()
                .filter(appUser -> appUser.getName().toLowerCase().startsWith("d"))
                .findAny().get();
        System.out.println(applicationUser.getName());
    }

    /**
     * @Concatenate -->
     * Description: This operation concatenates the elements in the stream into a single string or other data type.
     */
    @PostMapping("/concatenate")
    public void concatenate() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<String> appUsersName = applicationUserList.stream().
                filter(applicationUser -> applicationUser.getName().toLowerCase().startsWith("a"))
                .map(ApplicationUser::getName).toList();

        String concatination = appUsersName.stream().collect(Collectors.joining(", "));
        System.out.println("Concatenated Name : "+concatination);
    }

    /**
     * @Count -->
     * Description: This operation counts the number of elements in the stream and returns the count as a long value.
     */
    @PostMapping("/count")
    public void count() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        int count = (int) applicationUserList.stream().filter(applicationUser -> applicationUser.getAge() >= 60).count();
        System.out.println(count);
    }

    /**
     * @Reverse -->
     * Description: This operation reverses the order of elements in the stream.
     */
    @PostMapping("/reverse")
    public void reverse() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> ageList = applicationUserList.stream().map(ApplicationUser::getAge).sorted(Collections.reverseOrder()).collect(Collectors.toList());
        ageList.stream().forEach(System.out::println);
    }
}