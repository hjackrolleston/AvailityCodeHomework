/**
 * Java program to read CSV files and search them for enrollees,
 *   sorting them by insurance company, dividing output into
 *   different files by respective company. Per file, sorts users
 *   ascending by first and last name, and eliminate duplicate copies
 *   of every person that has two IDs, favoring the ID with the
 *   higher number.
 * */

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

class User implements Comparable<User> {
    String userId;
    String name;
    int version;
    String insuranceName;
    public User(String name) {
        this.name = name;
        this.userId = "";
        this.version = 1;
        this.insuranceName = "";
    }

    public User(String userId, String name, int version, String insuranceName) {
        this.userId = userId;
        this.name = name;
        this.version = version;
        this.insuranceName = insuranceName;
    }

    @Override
    public int compareTo(User o) {
        // Sort based on last name, then firstName
        String lastName = o.name.split(" ")[1];
        String firstName = o.name.split(" ")[0];
        if(this.name.split(" ")[1].compareTo(lastName) < 0) {
            return -1;
        } else if(this.name.split(" ")[1].compareTo(lastName) == 0){
            if(this.name.split(" ")[0].compareTo(firstName) < 0) {
                return -1;
            } else if(this.name.split(" ")[0].compareTo(firstName) == 0){
                return 0;
            } else {return 1;}
        } else {return 1;}
    }
    @Override
    public String toString() {return userId + ","+name+","+version+","+insuranceName;}

}

public class SortedListUser{
    public static List<User> readFileAndExtractList(String folder, String fileName) {
        // FileRead and List returns:
        List<User> resultList = new ArrayList<User>();
        try {
            FileInputStream fs = new FileInputStream(folder+"/"+fileName);
            Scanner sc = new Scanner(fs);

            while(sc.hasNextLine()) {
                // Reading file and making entry to list
                String st = sc.nextLine();
                if(st.length() > 0) {
                    String s[] = st.split(",");
                    if(s.length == 4) {
                        User u = new User(s[0], s[1], Integer.parseInt(s[2]), s[3]);
                        resultList.add(u);
                    } else {System.out.println("Line Format Incorrect");}
                }
            } sc.close();
        } catch(Exception e) {
            System.out.println("Exception Occured while reading file");
        } return resultList;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please provide absolute folder path for input file");
        String folderPath = sc.nextLine();

        System.out.println("Please provide input file name");
        String fileName = sc.nextLine();

        List<User> l = readFileAndExtractList(folderPath, fileName);

        Map<String, List<User>> result = new HashMap<String, List<User>>();

        for(int i=0;i<l.size();i++) {
            User mainList = l.get(i);
            if(result.containsKey(mainList.insuranceName)) {
                List<User> insuranceList = result.get(mainList.insuranceName);
                // checking data present in the list

                for(int j=0;j<insuranceList.size();j++) {
                    User u = insuranceList.get(j);
                    if(u.userId.contentEquals(mainList.userId) && 
                    u.insuranceName.contentEquals(mainList.insuranceName) &&
                      u.version < mainList.version) {
                        // Removing old user with the lower version id
                        result.get(mainList.insuranceName).remove(u);
                        System.out.println("Removing user:"+ u.userId +" and version:"+u.version);
                        break;
                    }
                }
                result.get(mainList.insuranceName).add(mainList); 
            } else {
                // New company entry
                System.out.print("New Company in HashMap");
                List<User> ls = new ArrayList<>();
                ls.add(mainList);
                result.put(mainList.insuranceName, ls);
            }
        }

        for(Map.Entry<String, List<User>> m: result.entrySet()) {
            String key = m.getKey();
            List<User> u = m.getValue();
            System.out.println("Sorting according to company");
            Collections.sort(u);
            writeFile(key, u, folderPath);
        }

    }

    public static void writeFile(String key, List<User> values, String folder) {
        System.out.println("Starting file write for: " + key);
        try {
            Writer wr = new FileWriter(folder+ "/"+key+".txt");
            for(User u:values) {wr.write(u.toString());wr.write("\n");}
            wr.flush();wr.close();

            System.out.println("Completed file write for: " + key);

        } catch(Exception e) {System.out.println("Exception Occured in Writing File");}
    }
}

