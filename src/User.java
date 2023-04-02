public class User {
    protected int idNum;
    protected String name;
    protected String email;
    protected String phone;
    protected String address;
    protected String password;

    protected String userType;

    static int currentID = 0;



    public void User (String name, String email, String phone, String address, String userType, String password, int id){
        currentID++;


        if (id == -1 ){
            currentID = id;

        } else {
            idNum= id;
        }
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.userType = userType;

    }
    //method to print info
    public void printInfo(){
        System.out.println("---------------------------------------------------------");
        System.out.println("The details are:");
        System.out.println("ID" + idNum);
        System.out.println("Name" + name);
        System.out.println("Email" + email);
        System.out.println("Address" + address);
        System.out.println("userType" + userType);


    }

    //get book information

    //setters and getters
    public void setName (String n) {
        this.name = n;


    }
    public String getName() {
        return name;
    }

    public void setEmail (String e) {
        this.email = e;
    }

    public String  getEmail() {
        return email;
    }

    public  void setPhone (String p) {
        this.phone = p;

    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String a) {
        this.address = a;
    }

    public String getAddress() {
        return address;
    }

    public void setUserType(String u) {
        this.userType = u; }

    public String getUserType() {
        return userType;
    }


}