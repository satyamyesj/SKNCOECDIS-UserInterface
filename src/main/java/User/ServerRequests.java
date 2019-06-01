package User;

import DatabaseAccessObjects.QueryObjects.*;
import DatabaseAccessObjects.ResultObjects.BookSearchQueryResult;
import DatabaseAccessObjects.ResultObjects.DomainQueryResult;
import DatabaseAccessObjects.ResultObjects.LibraryContentSearchQueryResult;
import DatabaseAccessObjects.ResultObjects.LibraryContentTypesQueryResult;
import DatabaseAccessObjects.ResultObjects.LibrayContentFileQueryResult;
import DatabaseAccessObjects.ResultObjects.PublisherQueryResult;
import DatabaseAccessObjects.ResultObjects.ReportSearchQueryResult;
import DatabaseAccessObjects.ResultObjects.Statistics;
import DatabaseAccessObjects.ResultObjects.SubjectQueryResult;
import DatabaseAccessObjects.ResultObjects.UserProfileQueryResult;
import RequestAttributes.RequestAttributes;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public class ServerRequests {

    static public boolean login(String username, String password) throws IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "VERIFY_CREDENTIALS";
        UserInterface.objOut.writeObject(requestAttributes);

        User user = new User();
        user.username = username;
        user.password = password;
        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }
        UserInterface.objOut.writeObject(user);
        boolean successfulSignIn = UserInterface.dataIn.readBoolean();
        if (successfulSignIn == true) {
            user = (User) UserInterface.objIn.readObject();
            UserInterface.user = user;
            libraryInchargeInterface.closeConnection();
            return true;
        } else {
            libraryInchargeInterface.closeConnection();
            return false;
        }
    }

    static public List<BookSearchQueryResult> getBookSearchResult(BookSearchAttributes book_query) throws IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();
        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "SEARCH_BOOK";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.objOut.writeObject(book_query);
        List<BookSearchQueryResult> book_query_result_set = (List<BookSearchQueryResult>) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return book_query_result_set;
    }

    public static List<ReportSearchQueryResult> getReportSearchResult(ReportSearchAttributes report_query) throws IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();
        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "SEARCH_REPORT";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.objOut.writeObject(report_query);
        List<ReportSearchQueryResult> report_query_result_set = (List<ReportSearchQueryResult>) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return report_query_result_set;
    }

    public static List<LibraryContentSearchQueryResult> getLibraryContentSearchResult(LibraryContentSearchAttributes library_content_search_attributes) throws UnknownHostException, IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();
        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "SEARCH_LIBRARY_CONTENT";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.objOut.writeObject(library_content_search_attributes);
        List<LibraryContentSearchQueryResult> libray_content_query_result_set = (List<LibraryContentSearchQueryResult>) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return libray_content_query_result_set;
    }
    
    public static LibrayContentFileQueryResult getLibraryContentFile(int content_id) throws UnknownHostException, IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();
        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "GET_LIBRARY_CONTENT_FILE";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.dataOut.writeInt(content_id);
        LibrayContentFileQueryResult library_content_file= (LibrayContentFileQueryResult) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return library_content_file;
    }


    static public UserProfileQueryResult getUserProfile(UserProfileRequestAttribute user_profile_query) throws IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "GET_USER_PROFILE";
        UserInterface.objOut.writeObject(requestAttributes);
        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }
        UserInterface.objOut.writeObject(user_profile_query);
        UserProfileQueryResult user_profile_query_result = (UserProfileQueryResult) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();

        return user_profile_query_result;
    }

    static public List<PublisherQueryResult> getPublisherList() throws IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = null;
        try {
            libraryInchargeInterface = new UserInterface();
        } catch (UnknownHostException e) {
            //e.printStackTrace();
        }
        libraryInchargeInterface.connectToServer();
        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "GET_PUBLISHERS_LIST";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        List<PublisherQueryResult> publishers_list;
        publishers_list = (List<PublisherQueryResult>) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return publishers_list;
    }

    static public List<DomainQueryResult> getDomainList() throws IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = null;
        try {
            libraryInchargeInterface = new UserInterface();
        } catch (UnknownHostException e) {
            //e.printStackTrace();
        }
        libraryInchargeInterface.connectToServer();
        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "GET_DOMAINS_LIST";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        List<DomainQueryResult> domains_list;
        domains_list = (List<DomainQueryResult>) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return domains_list;
    }

    static public List<SubjectQueryResult> getSubjectList() throws IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = null;
        try {
            libraryInchargeInterface = new UserInterface();
        } catch (UnknownHostException e) {
            //e.printStackTrace();
        }
        libraryInchargeInterface.connectToServer();
        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "GET_SUBJECTS_LIST";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        List<SubjectQueryResult> subjects_list;
        subjects_list = (List<SubjectQueryResult>) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return subjects_list;
    }

    public static boolean addSubject(AddSubjectAttributes add_subject_query) throws IOException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "ADD_SUBJECT";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.objOut.writeObject(add_subject_query);
        boolean added_successfully = UserInterface.dataIn.readBoolean();
        libraryInchargeInterface.closeConnection();
        return added_successfully;
    }

    public static boolean addLibrayContent(AddLibraryContentAttributes library_content_attributes) throws UnknownHostException, IOException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "ADD_LIBRARY_CONTENT";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.objOut.writeObject(library_content_attributes);
        boolean added_successfully = UserInterface.dataIn.readBoolean();
        libraryInchargeInterface.closeConnection();
        return added_successfully;
    }


    public static List<LibraryContentTypesQueryResult> getLibrayContentTypes() throws UnknownHostException, IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "GET_LIBRARY_CONTENT_TYPES";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }
        List<LibraryContentTypesQueryResult> library_content_types_list = (List<LibraryContentTypesQueryResult>) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return library_content_types_list;
    }

    public static List<String> getExamPatterns() throws UnknownHostException, IOException, ClassNotFoundException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "GET_EXAM_PATTERNS";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }
        List<String> exam_patterns_list = (List<String>) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return exam_patterns_list;
    }

    public static boolean checkWeatherUsernameIsAvailable(String input_username) throws UnknownHostException, IOException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "CHECK_USERNAME_IS_AVAILABLE";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.dataOut.writeUTF(input_username);
        boolean is_available = UserInterface.dataIn.readBoolean();
        libraryInchargeInterface.closeConnection();
        return is_available;
    }

    public static boolean registerStudentProfile(Student student) throws IOException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "REGISTER_STUDENT_PROFILE";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.objOut.writeObject(student);
        boolean registered_successfully = UserInterface.dataIn.readBoolean();
        libraryInchargeInterface.closeConnection();
        return registered_successfully;
    }

    public static boolean registerEmployeeProfile(Employee employee) throws UnknownHostException, IOException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "REGISTER_EMPLOYEE_PROFILE";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.objOut.writeObject(employee);
        boolean registered_successfully = UserInterface.dataIn.readBoolean();
        libraryInchargeInterface.closeConnection();
        return registered_successfully;
    }

    public static boolean updateStudentProfile(Student student) throws UnknownHostException, IOException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "UPDATE_STUDENT_PROFILE";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.objOut.writeObject(student);
        boolean updated_successfully = UserInterface.dataIn.readBoolean();
        libraryInchargeInterface.closeConnection();
        return updated_successfully;
    }

    public static boolean updateEmployeeProfile(Employee employee) throws UnknownHostException, IOException {
        UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "UPDATE_EMPLOYEE_PROFILE";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        UserInterface.objOut.writeObject(employee);
        boolean updated_successfully = UserInterface.dataIn.readBoolean();
        libraryInchargeInterface.closeConnection();
        return updated_successfully;
    }

    public static Statistics getStatistics() throws UnknownHostException, IOException, ClassNotFoundException {
       UserInterface libraryInchargeInterface = new UserInterface();
        libraryInchargeInterface.connectToServer();

        RequestAttributes requestAttributes = new RequestAttributes();
        requestAttributes.interfaceName = UserInterface.interfaceName;
        requestAttributes.requestCode = "GET_STATISTICS";
        UserInterface.objOut.writeObject(requestAttributes);

        while (true) {
            String serverStatus = UserInterface.bufferedReader.readLine();
            if (serverStatus.equals("SERVER_READY")) {
                break;
            }
        }

        Statistics statistics = (Statistics) UserInterface.objIn.readObject();
        libraryInchargeInterface.closeConnection();
        return statistics;
    }
}
