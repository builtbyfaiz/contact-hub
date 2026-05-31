package model;

/** The {@code Contact} class represents the model of a contact used throughout the application */
public class Contact {

    private String  uuid;
    private String  name;
    private String  phone;
    private String  email;
    private String  notes;
    private String  ip;
    private String  avatarPath;
    private boolean isFav;

    // Constructor
    public Contact(String uuid, String name, String phone,
                   String email, String notes, String ip, 
                   String avatarPath, boolean isFav) {

        this.uuid       = uuid;
        this.name       = name;
        this.phone      = phone;
        this.email      = email;
        this.notes      = notes;
        this.ip         = ip;
        this.avatarPath = avatarPath;
        this.isFav      = isFav;
    }

    // Getters
    public String  getUUID()       { return uuid;       }
    public String  getName()       { return name;       }
    public String  getPhone()      { return phone;      }
    public String  getEmail()      { return email;      }
    public String  getNotes()      { return notes;      }
    public String  getIP()         { return ip;         }
    public String  getAvatarPath() { return avatarPath; }
    public boolean getFav()        { return isFav;      }

    // Setters
    public void setName(String name)             { this.name       = name;       }
    public void setPhone(String phone)           { this.phone      = phone;      }
    public void setEmail(String email)           { this.email      = email;      }
    public void setNotes(String notes)           { this.notes      = notes;      }
    public void setIP(String ip)                 { this.ip         = ip;         }
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }
    public void setFav(boolean isFav)            { this.isFav      = isFav;      }
}
