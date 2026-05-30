package model;

public class Contact {

    private String  uuid;
    private String  name;
    private String  avatarPath;
    private String  ip;
    private String  phone;
    private String  email;
    private String  notes;
    private boolean isFav;

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

    public String  getUUID()       { return uuid;       }
    public String  getName()       { return name;       }
    public String  getAvatarPath() { return avatarPath; }
    public String  getIP()         { return ip;         }
    public String  getPhone()      { return phone;      }
    public String  getEmail()      { return email;      }
    public String  getNotes()      { return notes;      }
    public boolean isFav()         { return isFav;      }

    public void setName(String name)             { this.name       = name;       }
    public void setAvatarPath(String avatarPath) { this.avatarPath = avatarPath; }
    public void setIP(String ip)                 { this.ip         = ip;         }
    public void setPhone(String phone)           { this.phone      = phone;      }
    public void setEmail(String email)           { this.email      = email;      }
    public void setNotes(String notes)           { this.notes      = notes;      }
    // public void toggleFav(boolean isFav)         { this.isFav      = isFav;      }
    public void toggleFav()                      { this.isFav      = !isFav;      }

}
