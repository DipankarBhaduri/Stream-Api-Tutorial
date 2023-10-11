package com.stream.stream;

import com.stream.models.ApplicationUser;
import com.stream.repositories.ApplicationUserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/rest")
public class Stream_Api_Tutorial {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    /**
     * @Filter --> Line 161 - 169
     * Description: This operation filters the elements in the stream based on a specified condition.
     *
     * @Map --> Line 172 - 178
     * Description: This operation maps the elements in the stream to a new value or structure.
     *
     * @Collect --> Line 183 - 195
     * Description: This operation collects the elements in the stream into a collection, such as a List or Set.
     *
     * @ForEach --> Line 199 - 203
     * Description: This operation iterates over the elements in the stream and performs an action for each element.
     *
     * @Reduce --> Line 206 - 214
     * Description: This operation combines the elements in the stream into a single result using a
     *              specified binary operation.
     *
     * @Sort --> Line 217 - 222
     * Description: This operation sorts the elements in the stream based on a specified comparator.
     *
     * @Distinct --> Line 225 - 230
     * Description: This operation removes duplicate elements from the stream.
     *
     * @Limit --> Line 233 - 238
     * Description: This operation limits the number of elements in the stream to a specified maximum.
     *
     * @Skip --> Line 242 - 247
     * Description: This operation skips the first N elements in the stream and returns the rest.
     *
     * @FlatMap --> Line 250 - 276
     * Description: This operation maps each element in the stream to multiple values and flattens them into a single stream.
     *
     * @AnyMatch --> Line 91 -
     * Description: This operation checks if any elements in the stream match a given condition and returns a boolean result.
     *
     * @AllMatch --> Line 97 -
     * Description: This operation checks if all elements in the stream match a given condition and returns a boolean result.
     *
     * @NoneMatch --> Line 103 -
     * Description: This operation checks if none of the elements in the stream match a given condition and returns a boolean result.
     *
     * @GroupingBy --> Line 109 -
     * Description: This operation groups elements in the stream based on a provided classifier function.
     *
     * @PartitioningBy --> Line 115 -
     * Description: This operation partitions elements in the stream into two groups based on a provided predicate.
     *
     * @Min --> Line 121 -
     * Description: This operation finds the minimum element in the stream based on a comparator.
     *
     * @Max --> Line 127 -
     * Description: This operation finds the maximum element in the stream based on a comparator.
     *
     * @Average --> Line 133 -
     * Description: This operation calculates the average of numeric elements in the stream.
     *
     * @Sum --> Line 139 -
     * Description: This operation calculates the sum of numeric elements in the stream.
     *
     * @FindFirst --> Line 145 -
     * Description: This operation returns the first element in the stream, or an empty Optional if the stream is empty.
     *
     * @FindAny --> Line 151 -
     * Description: This operation returns any element in the stream, or an empty Optional if the stream is empty.
     *
     * @Concatenate --> Line 157 -
     * Description: This operation concatenates the elements in the stream into a single string or other data type.
     *
     * @Peek --> Line 163 -
     * Description: This operation performs a side effect for each element in the stream and returns the same stream.
     *
     * @Count --> Line 169 -
     * Description: This operation counts the number of elements in the stream and returns the count as a long value.
     *
     * @MapToInt --> Line 175 -
     * Description: This operation maps elements to primitive integers and returns an IntStream.
     *
     * @MapToDouble --> Line 181 -
     * Description: This operation maps elements to primitive doubles and returns a DoubleStream.
     *
     * @MapToLong --> Line 187 -
     * Description: This operation maps elements to primitive longs and returns a LongStream.
     *
     * @Peek --> Line 193 -
     * Description: This operation performs a side effect for each element in the stream and returns the same stream.
     *
     * @Limit --> Line 199 -
     * Description: This operation limits the number of elements in the stream to a specified maximum.
     *
     * @Skip --> Line 205 -
     * Description: This operation skips the first N elements in the stream and returns the rest.
     *
     * @ParallelStream --> Line 211 -
     * Description: This operation converts a sequential stream into a parallel stream for parallel processing.
     *
     * @Concat --> Line 217 -
     * Description: This operation concatenates two streams into one stream.
     *
     * @Zip --> Line 223 -
     * Description: This operation combines two streams element-wise using a specified function.
     *
     * @Unmodifiable --> Line 229 -
     * Description: This operation wraps a stream to make it unmodifiable, preventing further modification.
     *
     * @Reverse --> Line 235 -
     * Description: This operation reverses the order of elements in the stream.
     *
     * @TakeWhile --> Line 241 -
     * Description: This operation takes elements from the start of the stream while a condition is met.
     *
     * @DropWhile --> Line 247 -
     * Description: This operation skips elements from the start of the stream while a condition is met.
     *
     * @DistinctByKey --> Line 253 -
     * Description: This operation removes duplicate elements from the stream based on a key extractor function.
     *
     * @ToMap --> Line 259 -
     * Description: This operation converts the elements in the stream into a map using key and value mapping functions.
     *
     * @ToSet --> Line 265 -
     * Description: This operation converts the elements in the stream into a set.
     *
     * @ToCollection --> Line 271 -
     * Description: This operation converts the elements in the stream into a specified collection.
     *
     * @ToPrimitiveArray --> Line 277 -
     * Description: This operation converts the elements in the stream to a primitive array.
     *
     * @GroupingAndCounting --> Line 283 -
     * Description: This operation groups elements in the stream and counts occurrences of each group.
     *
     * @FilterNulls --> Line 289 -
     * Description: This operation filters out null elements from the stream.
     *
     * @DistinctByProperty --> Line 295 -
     * Description: This operation removes duplicate elements from the stream based on a specific property.
     *
     * @ToMultimap --> Line 301 -
     * Description: This operation converts the elements in the stream into a multimap.
     */

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

    @PostMapping("/map")
    public void map() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<String> applicationUsers = applicationUserList.stream()
                .filter(applicationUser -> applicationUser != null && StringUtils.isNotBlank(applicationUser.getName()))
                .map(ApplicationUser::getName).toList();
        applicationUsers.stream().forEach(name -> System.out.print(name+", "));
    }

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

    @PostMapping("/foreach")
    public void foreach() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        applicationUserList.stream()
                .forEach(applicationUser -> System.out.println(applicationUser.getGender()));
    }

    @PostMapping("/reduce")
    public void reduce() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAged = applicationUserList.stream()
                .map(applicationUser -> applicationUser.getAge())
                .collect(Collectors.toList());

        int age = allAged.stream().reduce(0, (num1, num2) -> num1+num2);
        System.out.println(age);
    }

    @PostMapping("/sort")
    public void sort() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAge = applicationUserList.stream()
                .map(ApplicationUser::getAge).sorted().toList();
        allAge.stream().forEach(System.out::println);
    }

    @PostMapping("/distinct")
    public void distinct() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAge = applicationUserList.stream()
                .map(ApplicationUser::getAge).distinct().sorted().toList();
        allAge.stream().forEach(System.out::println);
    }

    @PostMapping("/limit")
    public void limit() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAge = applicationUserList.stream()
                .map(ApplicationUser::getAge).distinct().sorted().limit(10).toList();
        allAge.stream().forEach(System.out::println);
    }


    @PostMapping("/skip")
    public void skip() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        List<Integer> allAge = applicationUserList.stream()
                .map(ApplicationUser::getAge).distinct().sorted().skip(25).toList();
        allAge.stream().forEach(System.out::println);
    }

    @PostMapping("/flatmap")
    public void flatmap() {
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

    @PostMapping("/anymatch")
    public void anymatch() {
        List<ApplicationUser> applicationUserList = applicationUserRepository.findAll();
        applicationUserList.stream()
                .filter(applicationUser -> applicationUser.getContactNumber() != null && !applicationUser.getContactNumber().isEmpty())
                .forEach(applicationUser -> {
                    applicationUser.getContactNumber().stream().forEach(mobileNumber -> {
                        int count = Stream.of(mobileNumber.toCharArray())
                                .filter(character -> ((int)(character - '0')) % 2 == 0)
                                .count() > 0;
                        if(count > 0) System.out.println(mobileNumber);
                    });
                });
    }
}

/*
 * @AnyMatch --> Line 91 -
 * Description: This operation checks if any elements in the stream match a given condition and returns a boolean result.
 *
 * @AllMatch --> Line 97 -
 * Description: This operation checks if all elements in the stream match a given condition and returns a boolean result.
 *
 */