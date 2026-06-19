# Hotel Management System

A Java-based hotel management system demonstrating OOP concepts (inheritance, polymorphism, encapsulation).

## Structure

- **Hotel** - Main entity containing employees and rooms
- **Employee** (abstract) - Base class for all hotel staff
  - Manager, Receptionist, Guard, Housekeeper, Cook, Server
- **Room** (base) - Standard room with WiFi
  - MidRoom - Adds AC
  - LuxuryRoom - Adds AC + sea view
- **Job** - Enum of employee roles
- **Guest** - Hotel guest
- **Booking** - Room reservation
