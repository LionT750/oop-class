# Dynamic CLI Plugin Framework

Version: 0.1

Language: Java

Dependencies: None

Storage: In Memory

Interface: CLI / Console

Pattern Focus:

* OOP
* Repository Pattern
* Command Pattern
* Plugin Architecture
* Dynamic Menu Composition

---

# 1. Purpose

Build a small Java application that demonstrates a dynamic CLI framework.

The application must support:

* Dynamic menu generation
* Runtime functionality registration
* Plugin-based feature modules
* In-memory persistence
* Repository abstraction
* Zero external libraries

The business domain used for demonstration is a simple sales system.

The framework itself must remain domain-independent.

---

# 2. Design Goals

## Primary Goals

* No switch/case menu implementation
* No hardcoded menu numbering
* Runtime addition of commands
* Runtime addition of plugins
* Simple architecture
* Educational codebase
* Easy future expansion

## Secondary Goals

* Simulate real plugin systems
* Keep modules isolated
* Support future persistence layers
* Support future permissions
* Support future events

---

# 3. High Level Architecture

Main
↓
Menu Engine
↓
Plugins
↓
Functionalities (Commands)
↓
Repository
↓
InMemoryDatabase

---

# 4. Package Structure

src/

├── app
│   └── Main.java
│
├── models
│   ├── Product.java
│   ├── Offer.java
│   ├── SalesPhysical.java
│   └── SalesDigital.java
│
├── data
│   └── InMemoryDatabase.java
│
├── repository
│   └── Repository.java
│
├── menu
│   ├── Menu.java
│   ├── MenuFunctionality.java
│   ├── Plugin.java
│   ├── FunctionalityContext.java
│   └── PluginRegistry.java
│
├── plugins
│   ├── ProductPlugin.java
│   ├── SalesPlugin.java
│   ├── DebugPlugin.java
│   ├── AdminPlugin.java
│   └── RuntimePluginLoader.java
│
├── utils
│   ├── ConsoleReader.java
│   ├── IdGenerator.java
│   └── Printer.java
│
└── events
├── EventBus.java
├── Event.java
└── listeners

---

# 5. Domain Model

## Product

Represents a product available for sale.

Fields:

Long id
String name
String description
double price
boolean active

---

## Offer

Base class.

Fields:

Long id
Product product
int quantity
LocalDateTime createdAt

---

## SalesPhysical

Extends Offer.

Fields:

String customerName
String shippingAddress
String postalCode

---

## SalesDigital

Extends Offer.

Fields:

String customerName
String email
String downloadKey

---

# 6. InMemoryDatabase

Purpose:

Store all application data.

Rules:

* Data only
* No business logic
* No validation
* No searching
* No filtering

Structure:

class InMemoryDatabase {

```
ArrayList<Product> products;

ArrayList<Offer> offers;

ArrayList<SalesPhysical> physicalSales;

ArrayList<SalesDigital> digitalSales;
```

}

---

# 7. Repository Layer

Purpose:

Single access point to persistence.

Menu and plugins never touch database collections directly.

Responsibilities:

saveProduct()

findProductById()

findAllProducts()

deleteProduct()

updateProduct()

savePhysicalSale()

saveDigitalSale()

findAllPhysicalSales()

findAllDigitalSales()

countProducts()

countSales()

clearProducts()

clearSales()

resetDatabase()

Future Implementations:

MemoryRepository
FileRepository
SqlRepository
ApiRepository

---

# 8. Functionality Context

Shared dependency container.

class FunctionalityContext {

```
Repository repository;

Menu menu;

Scanner scanner;

PluginRegistry pluginRegistry;

EventBus eventBus;
```

}

Purpose:

Provide dependencies to functionalities without globals.

---

# 9. MenuFunctionality Interface

Every executable menu action implements this interface.

public interface MenuFunctionality {

```
String getId();

String getLabel();

String getDescription();

void execute();
```

}

Examples:

create-product
list-products
delete-product
create-sale
exit

---

# 10. Command Pattern

Every menu item is represented by an object.

Menu never contains business logic.

Menu only executes:

selected.execute();

Benefits:

* No switch
* No if chains
* Open for extension
* Easy plugin integration

---

# 11. Plugin Interface

public interface Plugin {

```
String getId();

String getName();

String getDescription();

List<MenuFunctionality> getFunctionalities();
```

}

Purpose:

Provide groups of functionalities.

---

# 12. Plugin Registry

Responsible for managing plugins.

Responsibilities:

registerPlugin()

unregisterPlugin()

getPlugin()

getAllPlugins()

loadPluginFunctionalities()

Prevent duplicate plugin IDs.

---

# 13. Plugin Structure

Recommended structure:

public class ProductPlugin implements Plugin {

```
class CreateProduct
        implements MenuFunctionality {}

class ListProducts
        implements MenuFunctionality {}

class DeleteProduct
        implements MenuFunctionality {}
```

}

Reason:

Plugin becomes self-contained.

Commands live with their module.

Closer to real plugin architecture.

---

# 14. Menu Engine

Fields:

List<MenuFunctionality> functionalities;

boolean running;

Responsibilities:

loadFunctionalities()

loadPlugin()

addFunctionality()

removeFunctionality()

clearFunctionalities()

stop()

run()

---

# 15. Automatic Menu Generation

Menu options generated dynamically.

Example:

1 - Create Product
2 - List Products
3 - Create Physical Sale
4 - Load Debug Plugin
5 - Exit

Generated directly from functionality list.

No hardcoded numbering.

---

# 16. Runtime Functionality Registration

Any command may register another command.

Example:

LoadDebugCommand

Executes:

menu.addFunctionality(
new DebugStatistics()
);

New option appears immediately.

No restart required.

---

# 17. Runtime Plugin Loading

Any command may load a plugin.

Example:

LoadAdminPluginCommand

Executes:

menu.loadPlugin(
new AdminPlugin(context)
);

Menu updates automatically.

---

# 18. Product Plugin

Responsibilities:

Create Product

List Products

Find Product

Update Product

Delete Product

Deactivate Product

Count Products

---

# 19. Sales Plugin

Responsibilities:

Create Physical Sale

Create Digital Sale

List Physical Sales

List Digital Sales

List All Sales

Find Sale

Count Sales

---

# 20. Debug Plugin

Responsibilities:

Show Product Count

Show Sales Count

Show Registered Plugins

Show Loaded Commands

Show Database State

Dump Memory State

Show Event Statistics

---

# 21. Admin Plugin

Responsibilities:

Clear Products

Clear Sales

Reset Database

Unload Plugin

Disable Command

Enable Command

---

# 22. Exit Functionality

Responsibilities:

Stop application loop.

Implementation:

menu.stop();

---

# 23. Plugin Loader Plugin

Special plugin responsible for loading other plugins.

Commands:

Load Debug Plugin

Load Admin Plugin

Unload Plugin

List Plugins

This demonstrates runtime extensibility.

---

# 24. Event System (Optional Phase 2)

Introduce lightweight event bus.

Event Types:

ProductCreatedEvent

ProductDeletedEvent

SaleCreatedEvent

PluginLoadedEvent

PluginUnloadedEvent

CommandExecutedEvent

Benefits:

Loose coupling.

---

# 25. Event Bus

Responsibilities:

publish()

subscribe()

unsubscribe()

Plugins may react to events.

Example:

StatisticsPlugin listens to SaleCreatedEvent.

---

# 26. Permission System (Optional Phase 2)

Roles:

ADMIN

USER

DEBUG

GUEST

Functionality extension:

requiredRole()

Menu hides unauthorized commands.

---

# 27. Future File-Based Plugins

Current:

menu.loadPlugin(
new SalesPlugin(context)
);

Future:

plugins/

sales.plugin

debug.plugin

admin.plugin

Framework scans folder.

Discovers plugins.

Registers automatically.

Simulates real-world plugin platforms.

---

# 28. Utility Classes

ConsoleReader

Purpose:
Input validation

Methods:

readInt()

readString()

readDouble()

---

Printer

Purpose:
Consistent console output

Methods:

header()

separator()

error()

success()

---

IdGenerator

Purpose:
Generate IDs

Methods:

nextProductId()

nextOfferId()

---

# 29. Startup Flow

1. Create Database

2. Create Repository

3. Create EventBus

4. Create PluginRegistry

5. Create Menu

6. Create Context

7. Create Plugins

8. Register Plugins

9. Load Functionalities

10. Start Menu

Pseudo Flow:

Main
↓
Database
↓
Repository
↓
Context
↓
Plugins
↓
Menu.loadPlugin(...)
↓
Menu.run()

---

# 30. Success Criteria

The project is successful when it demonstrates:

✓ Java OOP

✓ Inheritance

✓ Encapsulation

✓ Repository Pattern

✓ Command Pattern

✓ Plugin Pattern

✓ Dynamic Menu Generation

✓ Runtime Command Registration

✓ Runtime Plugin Registration

✓ In-Memory Persistence

✓ Separation of Concerns

✓ No External Libraries

✓ No Switch-Based Menu

✓ Extensible Architecture

---

# 31. Stretch Goals

Phase 2

* Event Bus
* Permissions
* Command History
* Undo Commands
* Plugin Discovery Folder
* Save To File
* Import / Export Data

Phase 3

* Reflection-based plugin loading
* Annotation-driven command registration
* Nested menus
* Scheduled commands
* Lightweight dependency injection container

The final architecture should feel less like a sales application and more like a miniature plugin-capable application platform that happens to ship with a sales module.
